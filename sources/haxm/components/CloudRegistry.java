package haxm.components;

import haxm.components.DataCenter;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

import java.util.List;

public class CloudRegistry extends VirtEntity{
	private List<DataCenter> dataCenterList;
	
	protected boolean startEntity(){
		return true;
	}
	protected boolean stopEntity(){
		return true;
	}
	protected boolean processEvent(VirtEvent event){
		return true;
	}

	

}
