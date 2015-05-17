package haxm.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;
import haxm.policies.VMProvisioningPolicy;

/**
 * This class models the Datacenter object
 *
 */
public class Datacenter extends VirtEntity{
	
	/**
	 *  to know if Datacenter object is executing or not.
	 */
	private boolean executing = false;
	/**
	 *  configuration of the datacenter.
	 */
	private DatacenterConfiguration datacenterConfiguration;
	
	/**
	 *  policy for vmprovisioning.
	 */
	private VMProvisioningPolicy vmProvisioningPolicy;
	
	/**
	 * list of Vm's.
	 */
	private List<VM> vmList;
	/**
	 *  list of finished Vm's.
	 */
	private List<VM> finishedVmList;
	/**
	 *  list of destroyed Vm's.
	 */
	private List<VM> destroyedVmList;
	
	/**
	 * @return the vmList
	 */
	public List<VM> getVmList() {
		return vmList;
	}

	/**
	 * @param vmList the vmList to set
	 */
	public void setVmList(List<VM> vmList) {
		this.vmList = vmList;
	}

	/**
	 *  maps Vm id to the Vm object.
	 */
	private HashMap<Integer, VM> vmIdToVmMap;
	
	/**
	 * @param name
	 * @param datacenterConfiguration
	 * @param vmProvisioningPolicy
	 * constructor for initializing variables.
	 */
	public Datacenter(String name, DatacenterConfiguration datacenterConfiguration, VMProvisioningPolicy vmProvisioningPolicy) {
		super(name);
		this.datacenterConfiguration = datacenterConfiguration;		
		this.datacenterConfiguration.setDatacenterId(getId());
		
		this.setVmProvisioningPolicy(vmProvisioningPolicy);
		this.getVmProvisioningPolicy().setHostList(datacenterConfiguration.getHostList());
		
		this.vmList = new ArrayList<VM>();
		this.finishedVmList = new ArrayList<VM>();
		this.setDestroyedVmList(new ArrayList<VM>());
		vmIdToVmMap = new HashMap<Integer, VM>();
		
		for(Host host : datacenterConfiguration.getHostList()){
			host.setDatacenterId(getId());
		}
		
		datacenterConfiguration.setDatacenterId(getId());
	}
	
	@Override
	public boolean startEntity() {
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		scheduleNow(CloudVirt.cloudRegistry.getId(), TagEnum.REGISTER_DATACENTER, getId());
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
		String message = "[DC PE] EventID:"+event.getId()
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
			case DATACENTER_CONFIGURATION_REQUEST:
				handle_DATACENTER_CONFIGURATION_REQUEST(event);
				break;
			case CREATE_VM_WITH_ACK:
				handle_CREATE_VM_WITH_ACK(event);
				break;
			case SUBMIT_TASK:
				handle_SUBMIT_TASK(event);
				break;
			case TASK_EXECUTION:
				handle_TASK_EXECUTION(event);
				break;
			case VM_DESTROY:
				handle_VM_DESTROY(event);
				break;
		}
		return false;
	}

	/**
	 * @param event containing VM object to be destroyed
	 * destroys the VM specified by the event object.
	 */
	private void handle_VM_DESTROY(VirtEvent event) {
		// TODO Auto-generated method stub
		VM vm = (VM) event.getData();
		Host host = vm.getHost();
		host.removeVM(vm);
		host.getVmSchedulerPolicy().deallocateMips(vm);
		host.getMemoryProvisioningPolicy().deallocateMemory(vm);
		host.getBwProvisioningPolicy().deallocateBW(vm);
		//CloudVirt.vmsLog.append("[DC HVD] cost:"+getDatacenterConfiguration().getPricingPolicy().costOfVM(vm));
	}

	/**
	 * @param event 
	 * handles task execution.
	 */
	private void handle_TASK_EXECUTION(VirtEvent event) {
		List<Host> hostList = getDatacenterConfiguration().getHostList();
		double minTime = Double.MAX_VALUE;
		double time;
		for(Host host : hostList){
			host.executeVMs();
			time = host.getNextEventTime();
			if(time < minTime){
				minTime = time;
			}
		}
		// termination
		if(minTime == Double.MAX_VALUE){
			executing = false;
			CloudVirt.mainLog.append("[DC TE] Every VM is finished in the datacenter with ID - " + getId());
			return;
		}else{
			schedule(getId(), TagEnum.TASK_EXECUTION, minTime);
			
		}
	}

	/**
	 * @param event contains the task to be submitted.
	 * submits the task for execution.
	 */
	private void handle_SUBMIT_TASK(VirtEvent event) {	
		Task task = (Task) event.getData();
/*		
		List<Tasklet> tlts = task.getTaskletList();
		System.out.println(task.getId());
		for(Tasklet t: tlts){
			if(t instanceof CPUTasklet){
				System.out.println(((CPUTasklet)t).getRemainingInstructionLength() );
			}
			if(t instanceof DIOTasklet){
				System.out.println(((DIOTasklet)t).getRemainingData() );
			}
			if(t instanceof NIOTasklet){
				System.out.println(((NIOTasklet)t).getRemainingData() );
			}
		}
*/		
		
		VM vm = task.getVm();
		
		vm.addTask(task);
		task.setDatacenterId(getId());
		if(!executing){
			executing = true;
			scheduleNow(getId(), TagEnum.TASK_EXECUTION);
		}
	}

	/**
	 * @param event contains the VM object to be created
	 * handles creation of VM and sends back ACK.
	 */
	private void handle_CREATE_VM_WITH_ACK(VirtEvent event) {
		VM vm = (VM) event.getData();
		boolean result = false;
		Host host = vmProvisioningPolicy.allocateHostToVM(vm, this);
		if(host != null){
			result = true;
			vmList.add(vm);
			host.addVM(vm);
			vmIdToVmMap.put(vm.getId(), vm);
		}
		scheduleNow(event.getSourceId(), TagEnum.ACK_CREATE_VM, result);
	}

	private void handle_DATACENTER_CONFIGURATION_REQUEST(VirtEvent event) {
		
		scheduleNow(event.getSourceId(), TagEnum.DATACENTER_CONFIGURATION_RESPONSE,  getDatacenterConfiguration());
		
	}

	/**
	 * @return the vmProvisioningPolicy
	 */
	public VMProvisioningPolicy getVmProvisioningPolicy() {
		return vmProvisioningPolicy;
	}

	/**
	 * @param vmProvisioningPolicy the vmProvisioningPolicy to set
	 */
	public void setVmProvisioningPolicy(VMProvisioningPolicy vmProvisioningPolicy) {
		this.vmProvisioningPolicy = vmProvisioningPolicy;
	}

	/**
	 * @return the datacenter configuration.
	 */
	public DatacenterConfiguration getDatacenterConfiguration() {
		return datacenterConfiguration;
	}
	
	/**
	 * @param datacenterConfiguration 
	 * setter
	 */
	public void setDatacenterConfiguration(DatacenterConfiguration datacenterConfiguration) {
		this.datacenterConfiguration = datacenterConfiguration;
		this.datacenterConfiguration.setDatacenterId(getId());
	}

	/**
	 * @return finished Vm list.
	 */
	public List<VM> getFinishedVmList() {
		return finishedVmList;
	}

	/**
	 * @param finishedVmList 
	 * setter for finishedVMlist.
	 */
	public void setFinishedVmList(List<VM> finishedVmList) {
		this.finishedVmList = finishedVmList;
	}

	/**
	 * @return destroyed VM list.
	 */
	public List<VM> getDestroyedVmList() {
		return destroyedVmList;
	}

	/**
	 * @param destroyedVmList 
	 * setter for destroyedVMlist.
	 */
	public void setDestroyedVmList(List<VM> destroyedVmList) {
		this.destroyedVmList = destroyedVmList;
	}

	/**
	 * @param task task that is completed.
	 * @param userId userid to send notification.
	 * sends an event to a user to notify that task is completed.
	 */
	public void notifyTaskFinished(Task task, int userId) {
		// TODO Auto-generated method stub
		scheduleNow(userId, TagEnum.TASK_FINISHED, task);
		
	}
	
	

};