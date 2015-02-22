package haxm.components;

import java.util.List;

import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;
import haxm.policies.VMProvisioningPolicy;

public class Datacenter extends VirtEntity{
	
	
	private DatacenterConfiguration datacenterConfiguration;
	
	public VMProvisioningPolicy vmProvisioningPolicy;
	
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

	public Datacenter(String name, DatacenterConfiguration datacenterConfiguration, VMProvisioningPolicy vmProvisioningPolicy) {
		super(name);
		this.datacenterConfiguration = datacenterConfiguration;		
		this.datacenterConfiguration.setDatacenterId(getId());
		this.setVmProvisioningPolicy(vmProvisioningPolicy);
	}
	
	public DatacenterConfiguration getDatacenterConfiguration() {
		return datacenterConfiguration;
	}
	
	public void setDatacenterConfiguration(DatacenterConfiguration datacenterConfiguration) {
		this.datacenterConfiguration = datacenterConfiguration;
		this.datacenterConfiguration.setDatacenterId(getId());
	}

	@Override
	public boolean startEntity() {
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		schedule(CloudVirt.cloudRegistry.getId(), TagEnum.SEND, TagEnum.REGISTER_DATACENTER, 0.0, getId());
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
		}
		return false;
	}

	private void handle_CREATE_VM_WITH_ACK(VirtEvent event) {
		VM vm = (VM) event.getData();
		boolean result = false;
		if(vmProvisioningPolicy.allocateHostToVM(vm, this)){
			result = true;
		}
		schedule(event.getSourceId(), TagEnum.SEND, TagEnum.ACK_CREATE_VM, 0.00, result);
	}

	private void handle_DATACENTER_CONFIGURATION_REQUEST(VirtEvent event) {
		
		schedule(event.getSourceId(), TagEnum.SEND, TagEnum.DATACENTER_CONFIGURATION_RESPONSE, 0.0,  getDatacenterConfiguration());
		
	}
	
	
	

}