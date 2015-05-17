package haxm.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;
import haxm.policies.VirtUserPolicy;

/**
 * this class models a user in the simulation.
 *
 */
public class VirtUser extends VirtEntity{
	/**
	 * list of Vm's needed by user.
	 */
	private List<VM> vmList;
	/**
	 * list of vm's created.
	 */
	private List<VM> createdVMList;
	/**
	 * list of failed vm's.
	 */
	private List<VM> failedVMList;
	/**
	 * list fo tasks.
	 */
	private List<Task> taskList;
	/**
	 *  list of available datacenter id's.
	 */
	private List<Integer> availableDatacenterIdList;
	/**
	 *  id's of datacenters selected using policy.
	 */
	private List<Integer> selectedDatacenterIdList;
	/**
	 *  list of datacenter configurations.
	 */
	private List<DatacenterConfiguration> availableConfigurationsList;
	/**
	 * policy for selecting datacenter
	 */
	private VirtUserPolicy userPolicy;
	/**
	 *  map vm id's to number of tasks.
	 */
	private HashMap<Integer, Integer> vmToNumTasks;
	
	/**
	 * @param name
	 * @param policy
	 * constructor.
	 */
	public VirtUser(String name, VirtUserPolicy policy){
		super(name);
		this.vmList = new ArrayList<VM>();
		this.createdVMList = new ArrayList<VM>();
		this.setFailedVMList(new ArrayList<VM>());
		this.setTaskList(new ArrayList<Task>());
		this.vmToNumTasks = new HashMap<Integer, Integer>();
		this.availableDatacenterIdList = new ArrayList<Integer>();
		this.selectedDatacenterIdList = new ArrayList<Integer>();
		this.availableConfigurationsList = new ArrayList<DatacenterConfiguration>();
		this.userPolicy = policy;
	}

	@Override
	public boolean startEntity() {
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		scheduleNow(CloudVirt.cloudRegistry.getId(), TagEnum.DATACENTERS_INFO_REQUEST);
		return false;
	}

	@Override
	public boolean shutdownEntity() {
		this.currentState.setState(VirtStateEnum.FINISHED);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " finished at " + CloudVirt.getCurrentTime());
		return false;
	}

	@Override
	public boolean processEvent(VirtEvent event) {
/*		
		String message = "[VU PE] EventID:"+event.getId()
				+"	Source:"+CloudVirt.entityHolder.getEntityNameByID(event.getSourceId())
				+",ID-"+event.getSourceId()
				+"	Destination:"+CloudVirt.entityHolder.getEntityNameByID(event.getDestinationId())
				+",ID-"+event.getDestinationId()
				+ "	Type:"+event.getType()
				+"	Tag:"+event.getTag()
				+"	Time:"+event.getTime();
		CloudVirt.writeLog(CloudVirt.eventsLog, message);
		
*/
		switch(event.getTag()){
			case DATACENTERS_INFO_RESPONSE:
				handle_DATACENTERS_INFO_RESPONSE(event);
				break;
			case DATACENTER_CONFIGURATION_RESPONSE:
				handle_DATACENTER_CONFIGURATION_RESPONSE(event);
				break;
			case ACK_CREATE_VM:
				handle_ACK_CREATE_VM(event);
				break;
			case TASK_FINISHED:
				handle_TASK_FINISHED(event);
				break;
				
		}
		return false;
	}

	/**
	 * @param event contains task data
	 * handle task finished event.
	 */
	private void handle_TASK_FINISHED(VirtEvent event) {
		// TODO Auto-generated method stub
		Task task = (Task) event.getData();
		VM vm = task.getVm();
		int vmId = vm.getId();
		CloudVirt.tasksLog.append("[VU HTF] Task finished id:"+task.getId() + "of user id:"+getId()+"inside VM id:"+vmId+" start time:"
				+task.getStartTime()+" finished time:"+task.getFinishTime());
		int numTasks = vmToNumTasks.get(vmId);
		if(numTasks == 1){
			vmToNumTasks.remove(vmId);
			scheduleNow(task.getDatacenterId(), TagEnum.VM_DESTROY, vm);
		}else{
			vmToNumTasks.put(vm.getId(), numTasks-1);
		}
		
	}

	/**
	 * @param event 
	 * handles ack for create_vm.
	 */
	private void handle_ACK_CREATE_VM(VirtEvent event) {
		boolean result = (boolean) event.getData();
		VM vm = getVmList().get(0);
		getVmList().remove(0);
		if(result){
			getCreatedVMList().add(vm);
			vm.setDatacenterId(event.getSourceId());
			String message = "VM Created with VMId:"+vm.getId()+" in Datacenter:" + CloudVirt.entityHolder.getEntityNameByID(event.getSourceId())+",id:"+event.getSourceId();
			CloudVirt.writeLog(null, message);
			for(Task task:taskList){
				if(task.getVm().getId() == vm.getId()){
					scheduleNow(event.getSourceId(), TagEnum.SUBMIT_TASK, task);
				}	
			}
		}else{
			getFailedVMList().add(vm);
			String message = "VM Creation failed in Datacenter:" + CloudVirt.entityHolder.getEntityNameByID(event.getSourceId())+",id:"+event.getSourceId();
			CloudVirt.writeLog(null, message);
			
		}
		createNextVm();		
	}

	/**
	 * @return createdvm list.
	 */
	public List<VM> getCreatedVMList() {
		return createdVMList;
	}

	/**
	 * @param createdVMList to set created vm list.
	 */
	public void setCreatedVMList(List<VM> createdVMList) {
		this.createdVMList = createdVMList;
	}

	/**
	 * @param event 
	 * handles datacenter configuration response.
	 */
	private void handle_DATACENTER_CONFIGURATION_RESPONSE(VirtEvent event) {
		// TODO Auto-generated method stub
		availableConfigurationsList.add((DatacenterConfiguration) event.getData());
		if(availableConfigurationsList.size() == availableDatacenterIdList.size()){
			selectedDatacenterIdList = userPolicy.selectDatacenters(availableConfigurationsList);
			createNextVm();
		}
		
	}

	/**
	 *  create next vm
	 */
	private void createNextVm() {
		if(getVmList().size() != 0){
			VM vm = getVmList().get(0);
			int datacenterId = userPolicy.selectDatacenterForVM(selectedDatacenterIdList, vm);
			scheduleNow(datacenterId, TagEnum.CREATE_VM_WITH_ACK, vm);
		}else{
			CloudVirt.writeLog(null, "No more VMs to create.");
		}
	}

	/**
	 * @param event contains availabel datacenter id list.
	 * handle datacenter info response.
	 */
	private void handle_DATACENTERS_INFO_RESPONSE(VirtEvent event) {
		availableDatacenterIdList = (List<Integer>) event.getData();
		//TO-DO send events to all dc
		for(int destinationId : availableDatacenterIdList){
			scheduleNow(destinationId, TagEnum.DATACENTER_CONFIGURATION_REQUEST);
		}
	}

	/**
	 * @return vm list.
	 */
	public List<VM> getVmList() {
		return vmList;
	}

	/**
	 * @param vmList to set vm list.
	 */
	public void setVmList(List<VM> vmList) {
		this.vmList = vmList;
	}
	/**
	 * @return tasklist.
	 */
	public List<Task> getTaskList() {
		return taskList;
	}

	/**
	 * @param taskList to set tasklist.
	 */
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	/**
	 * @param vmList vm's to be submitted
	 */
	public void submitVMs(List<VM> vmList) {
		this.getVmList().addAll(vmList);
	}

	/**
	 * @param taskList list of tasks to be sumbmitted.
	 */
	public void submitTasks(List<Task> taskList) {
		for(Task task : taskList){
			VM vm = task.getVm();
			int vmId = vm.getId();
			if(vmToNumTasks.get(vmId) == null){
				vmToNumTasks.put(vmId, 1);
			}else{
				int numTasks = vmToNumTasks.get(vmId);
				vmToNumTasks.put(vmId, numTasks+1);
			}
		}
		this.getTaskList().addAll(taskList);		
	}

	/**
	 * @return the failedVMList
	 */
	public List<VM> getFailedVMList() {
		return failedVMList;
	}

	/**
	 * @param failedVMList the failedVMList to set
	 */
	public void setFailedVMList(List<VM> failedVMList) {
		this.failedVMList = failedVMList;
	}

}
