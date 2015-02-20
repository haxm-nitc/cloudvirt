package haxm.components;

import java.util.ArrayList;
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
	private List<Task> taskList;
	private List<Integer> availableDatacenterIdList;
	private List<Integer> selectedDatacenterIdList;
	private List<DatacenterConfiguration> availableConfigurationsList;
	private VirtUserPolicy userPolicy;
	
	public VirtUser(String name, VirtUserPolicy policy){
		super(name);
		vmList = new ArrayList<VM>();
		createdVMList = new ArrayList<VM>();
		taskList = new ArrayList<Task>();
		availableDatacenterIdList = new ArrayList<Integer>();
		selectedDatacenterIdList = new ArrayList<Integer>();
		availableConfigurationsList = new ArrayList<DatacenterConfiguration>();
		userPolicy = policy;
	}

	@Override
	public boolean startEntity() {
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		schedule(CloudVirt.cloudRegistry.getId(), TagEnum.SEND, TagEnum.DATACENTERS_INFO_REQUEST, 0.0);
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
				
		}
		return false;
	}

	private void handle_ACK_CREATE_VM(VirtEvent event) {
		int [] data = (int[]) event.getData();
		boolean result = false;
		int vmId = -1;
		if(data[0] == 1){
			result = true;
			vmId = data[1];
			VM vm = getVmList().get(0);
			getVmList().remove(vm);
			getCreatedVMList().add(vm);
			vm.setVmId(vmId);
			vm.setDatacenterId(event.getSourceId());
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
			schedule(datacenterId, TagEnum.SEND, TagEnum.CREATE_VM_WITH_ACK, 0.00, vm);
		}else{
			
		}
	}

	private void handle_DATACENTERS_INFO_RESPONSE(VirtEvent event) {
		availableDatacenterIdList = (List<Integer>) event.getData();
		//TO-DO send events to all dc
		for(int destinationId : availableDatacenterIdList){
			schedule(destinationId, TagEnum.SEND, TagEnum.DATACENTER_CONFIGURATION_REQUEST, 0.0);
		}
	}

	public List<VM> getVmList() {
		return vmList;
	}

	public void setVmList(List<VM> vmList) {
		this.vmList = vmList;
	}

}
