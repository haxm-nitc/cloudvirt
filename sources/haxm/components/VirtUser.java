package haxm.components;

import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

public class VirtUser extends VirtEntity{
	public VirtUser(String name){
		super(name);
	}

	@Override
	public boolean startEntity() {
		// TODO Auto-generated method stub
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
