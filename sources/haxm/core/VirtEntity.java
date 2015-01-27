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
	protected VirtState currentState;

	/**Event queue of the entity. Events to be processed are kept in the buffered queue*/
	private EventQueue localQueue;
	
	public VirtEntity(String name){
		this.name = name;
		this.id = ++numEntities;
		currentState = new VirtState(VirtStateEnum.INVALID);
		localQueue = new EventQueue();
		CloudVirt.addEntity(this);
	}

	public VirtStateEnum getCurrentState() {
		return currentState.getState();
	}
	public void setCurrentState(VirtStateEnum currentState) {
		this.currentState.setState(currentState);
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public abstract boolean startEntity();
	public abstract boolean shutdownEntity();
	public abstract boolean processEvent(VirtEvent event);
	
	public void addToLocalQueue(VirtEvent event) {
		this.localQueue.addEvent(event);		
	}
	
	public void schedule(int destinationId, TagEnum type, TagEnum tag, double time, Object data){
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, type, tag, time, data));
	}
	
	public void schedule(int destinationId, TagEnum type, TagEnum tag, double time){		
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, type, tag, time));
	}
	
	public void addToGlobalQueue(VirtEvent event){
		
		CloudVirt.globalQueue.addEvent(event);
	}
	public void run() {
		while(!localQueue.empty()){			
			processEvent(localQueue.extract());
		}
		
	}
	
}
