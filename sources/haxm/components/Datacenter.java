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

public class Datacenter extends VirtEntity{
	
	private boolean executing = false;
	private DatacenterConfiguration datacenterConfiguration;
	
	private VMProvisioningPolicy vmProvisioningPolicy;
	
	private List<VM> vmList;
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

	private HashMap<Integer, VM> vmIdToVmMap;
	
	public Datacenter(String name, DatacenterConfiguration datacenterConfiguration, VMProvisioningPolicy vmProvisioningPolicy) {
		super(name);
		this.datacenterConfiguration = datacenterConfiguration;		
		this.datacenterConfiguration.setDatacenterId(getId());
		
		this.setVmProvisioningPolicy(vmProvisioningPolicy);
		this.getVmProvisioningPolicy().setHostList(datacenterConfiguration.getHostList());
		
		this.vmList = new ArrayList<VM>();
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
				handle_TASK_EXECUTION();
				break;
		}
		return false;
	}

	private void handle_TASK_EXECUTION() {
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
		// TODO termination
		if(minTime == Double.MAX_VALUE){
			return;
		}else{
			schedule(getId(), TagEnum.TASK_EXECUTION, minTime);
		}	
		
		//System.out.println("dsfsd");
		// TODO completion check
		for(VM vm : getVmList()){
			List<Task> taskList = vm.getFinishedTaskList();
			if(taskList != null){
				for(Task task : taskList){
					scheduleNow(task.getUserId(), TagEnum.TASK_FINISHED, task);
				}
			}
			vm.getFinishedTaskList().removeAll(taskList);
			
			if(vm.getVmState().getState() == VirtStateEnum.FINISHED){
				scheduleNow(vm.getUserId(), TagEnum.VM_FINISHED, vm);
			}
		}
		
	}

	private void handle_SUBMIT_TASK(VirtEvent event) {		
		Task task = (Task) event.getData();
		VM vm = task.getVm();
		vm.addTask(task);		
		if(!executing){
			executing = true;
			scheduleNow(getId(), TagEnum.TASK_EXECUTION);
		}
	}

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

	public DatacenterConfiguration getDatacenterConfiguration() {
		return datacenterConfiguration;
	}
	
	public void setDatacenterConfiguration(DatacenterConfiguration datacenterConfiguration) {
		this.datacenterConfiguration = datacenterConfiguration;
		this.datacenterConfiguration.setDatacenterId(getId());
	}
	
	

};