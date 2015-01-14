package haxm.core;

import haxm.VirtState;
import haxm.VirtStateEnum;

public abstract class VirtEntity{
	
	private static int numEntities = 0;
	
	/**A unique Entity Identifier*/
	private int id;
	/**/
	private String name;
	
	/**Current State of the entity*/
	private VirtState currentState;

	/**Event queue of the entity. Events to be processed are kept in the buffered queue*/
	private EventQueue localQueue;
	
	public VirtEntity(String name){
		this.name = name;
		this.id = ++numEntities;
		this.currentState.setState(VirtStateEnum.INVALID);
	}
	
	
	protected abstract boolean startEntity();
	protected abstract boolean shutdownEntity();
	protected abstract boolean processEvent(VirtEvent event);
	

	
}
