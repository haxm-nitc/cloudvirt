package haxm.components;

import haxm.components.Datacenter;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

import java.util.List;

public class CloudRegistry extends VirtEntity{
	
	private List<Datacenter> dataCenterList;
	
	public CloudRegistry(String name) {
		super(name);
	}

	public boolean startEntity(){
		
		return true;
	}

	public boolean shutdownEntity(){
		return true;
	}
	
	public boolean processEvent(VirtEvent event){
		return true;
	}
}