package haxm.components;

import java.util.List;

import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

public class Datacenter extends VirtEntity{
	
	private VMProvisioningPolicy vmProvisioningPolicy;
	
	private List<Host> hostList;
	
	
	
	public Datacenter(String name) {
		super(name);
		
	}
	@Override
	protected boolean startEntity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean shutdownEntity() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean processEvent(VirtEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	

}