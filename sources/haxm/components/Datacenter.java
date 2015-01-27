package haxm.components;

import java.util.List;

import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

public class Datacenter extends VirtEntity{
	
	private VMProvisioningPolicy vmProvisioningPolicy;
	
	private List<Host> hostList;
	
	
	
	public Datacenter(String name) {
		super(name);
		
	}
	@Override
	public boolean startEntity() {
		this.currentState.setState(VirtStateEnum.RUNNING);
		schedule(CloudVirt.cloudRegistry.getId(), TagEnum.SEND, TagEnum.REGISTER_DATACENTER, 0.0);
		return false;
	}

	@Override
	public boolean shutdownEntity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processEvent(VirtEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	

}