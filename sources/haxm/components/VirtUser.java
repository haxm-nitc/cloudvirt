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

public class VirtUser extends VirtEntity{
	private List<VM> vmList;
	private List<VM> createdVMList;
	private List<VM> failedVMList;
	private List<Task> taskList;
	private List<Integer> availableDatacenterIdList;
	private List<Integer> selectedDatacenterIdList;
	private List<DatacenterConfiguration> availableConfigurationsList;
	private VirtUserPolicy userPolicy;
	private HashMap<Integer, Integer> vmToNumTasks;
	
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

	private void handle_TASK_FINISHED(VirtEvent event) {
		// TODO Auto-generated method stub
		Task task = (Task) event.getData();
		VM vm = task.getVm();
		int vmId = vm.getId();
		CloudVirt.tasksLog.append("[VU HTF] Task id:"+task.getId() + "of user id:"+getId()+"inside VM id:"+vmId);
		int numTasks = vmToNumTasks.get(vmId);
		if(numTasks == 1){
			vmToNumTasks.remove(vmId);
			scheduleNow(task.getDatacenterId(), TagEnum.VM_DESTROY, vm);
		}else{
			vmToNumTasks.put(vm.getId(), numTasks-1);
		}
		
	}

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

	public List<VM> getCreatedVMList() {
		return createdVMList;
	}

	public void setCreatedVMList(List<VM> createdVMList) {
		this.createdVMList = createdVMList;
	}

	private void handle_DATACENTER_CONFIGURATION_RESPONSE(VirtEvent event) {
		// TODO Auto-generated method stub
		availableConfigurationsList.add((DatacenterConfiguration) event.getData());
		if(availableConfigurationsList.size() == availableDatacenterIdList.size()){
			selectedDatacenterIdList = userPolicy.selectDatacenters(availableConfigurationsList);
			createNextVm();
		}
		
	}

	private void createNextVm() {
		if(getVmList().size() != 0){
			VM vm = getVmList().get(0);
			int datacenterId = userPolicy.selectDatacenterForVM(selectedDatacenterIdList, vm);
			scheduleNow(datacenterId, TagEnum.CREATE_VM_WITH_ACK, vm);
		}else{
			CloudVirt.writeLog(null, "No more VMs to create.");
		}
	}

	private void handle_DATACENTERS_INFO_RESPONSE(VirtEvent event) {
		availableDatacenterIdList = (List<Integer>) event.getData();
		//TO-DO send events to all dc
		for(int destinationId : availableDatacenterIdList){
			scheduleNow(destinationId, TagEnum.DATACENTER_CONFIGURATION_REQUEST);
		}
	}

	public List<VM> getVmList() {
		return vmList;
	}

	public void setVmList(List<VM> vmList) {
		this.vmList = vmList;
	}
	public List<Task> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}

	public void submitVMs(List<VM> vmList) {
		this.getVmList().addAll(vmList);
	}

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
