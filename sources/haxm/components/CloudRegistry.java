package haxm.components;

import haxm.components.Datacenter;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

import java.util.List;

public class CloudRegistry extends VirtEntity{
	public CloudRegistry(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	private List<Datacenter> dataCenterList;
	
	protected boolean startEntity(){
		return true;
	}
	protected boolean shutdownEntity(){
		return true;
	}
	protected boolean processEvent(VirtEvent event){
		return true;
	}

	

}
