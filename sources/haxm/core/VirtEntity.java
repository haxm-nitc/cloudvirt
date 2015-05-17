package haxm.core;

import haxm.VirtState;
import haxm.VirtStateEnum;

/**
 * this class models the entity
 *
 */
public abstract class VirtEntity{
	
	/**
	 * total number of entities.
	 */
	private static int numEntities = 0;
	
	/**A unique Entity Identifier*/
	private int id;
	
	/**
	 *  name of entity.
	 */
	protected String name;
	
	/**
	 * @return name of entity.
	 */
	public String getName() {
		return name;
	}
	/**Current State of the entity*/
	protected VirtState currentState;

	/**Event queue of the entity. Events to be processed are kept in the buffered queue*/
	private EventQueue localQueue;
	
	/**
	 * @param name name of entity
	 * constructor.
	 */
	public VirtEntity(String name){
		this.name = name;
		this.id = ++numEntities;
		currentState = new VirtState(VirtStateEnum.INVALID);
		localQueue = new EventQueue();
		CloudVirt.addEntity(this);
	}

	/**
	 * @return current state.
	 */
	public VirtStateEnum getCurrentState() {
		return currentState.getState();
	}
	/**
	 * @param currentState to set currentstate
	 */
	public void setCurrentState(VirtStateEnum currentState) {
		this.currentState.setState(currentState);
	}
	/**
	 * @return id of entity.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id to set id of entity.
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return boolean
	 * to start entity.
	 */
	public abstract boolean startEntity();
	/**
	 * @return boolean
	 * to shut down entity
	 */
	public abstract boolean shutdownEntity();
	/**
	 * @param event information about the event
	 * @return boolean
	 * to process the event.
	 */
	public abstract boolean processEvent(VirtEvent event);
	
	/**
	 * @param event event to be added to local queue.
	 */
	public void addToLocalQueue(VirtEvent event) {
/*		
		String message = "[Local] EventID:"+event.getId()
				+"	Source:"+CloudVirt.entityHolder.getEntityNameByID(event.getSourceId())
				+",ID-"+event.getSourceId()
				+"	Destination:"+CloudVirt.entityHolder.getEntityNameByID(event.getDestinationId())
				+",ID-"+event.getDestinationId()
				+ "	Type:"+event.getType()
				+"	Tag:"+event.getTag()
				+"	Time:"+event.getTime();
		CloudVirt.writeLog(CloudVirt.eventsLog, message);
*/		
		this.localQueue.addEvent(event);		
	}
	
	/**
	 * @param destinationId
	 * @param type
	 * @param tag
	 * @param delay
	 * @param data
	 * schedule event
	 */
	public void schedule(int destinationId, TagEnum type, TagEnum tag, double delay, Object data){
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, type, tag, CloudVirt.getCurrentTime() + delay, data));
	}
	
	/**
	 * @param destinationId
	 * @param tag
	 * @param delay
	 * @param data
	 * schedule event
	 */
	public void schedule(int destinationId, TagEnum tag, double delay, Object data){
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, TagEnum.SEND, tag, CloudVirt.getCurrentTime() + delay, data));
	}

	/**
	 * @param destinationId
	 * @param tag
	 * @param data
	 * schedule event
	 */
	public void scheduleNow(int destinationId, TagEnum tag, Object data){
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, TagEnum.SEND, tag, CloudVirt.getCurrentTime(), data));
	}

	/**
	 * @param destinationId
	 * @param type
	 * @param tag
	 * @param delay
	 * schedule event
	 */
	public void schedule(int destinationId, TagEnum type, TagEnum tag, double delay){		
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, type, tag, CloudVirt.getCurrentTime() + delay));
	}
	
	/**
	 * @param destinationId
	 * @param tag
	 * @param delay
	 * schedule event
	 */
	public void schedule(int destinationId, TagEnum tag, double delay){		
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, TagEnum.SEND, tag, CloudVirt.getCurrentTime() + delay));
	}

	/**
	 * @param destinationId
	 * @param tag
	 * schedule event
	 */
	public void scheduleNow(int destinationId, TagEnum tag){		
		addToGlobalQueue(new VirtEvent(this.getId(), destinationId, TagEnum.SEND, tag, CloudVirt.getCurrentTime()));
	}

	/**
	 * @param event event to be added to global queue.
	 */
	public void addToGlobalQueue(VirtEvent event){
		String message = "[Global] EventID:"+event.getId()
				+"	Source:"+CloudVirt.entityHolder.getEntityNameByID(event.getSourceId())
				+",ID-"+event.getSourceId()
				+"	Destination:"+CloudVirt.entityHolder.getEntityNameByID(event.getDestinationId())
				+",ID-"+event.getDestinationId()
				+ "	Type:"+event.getType()
				+"	Tag:"+event.getTag()
				+"	Time:"+event.getTime();
		CloudVirt.writeLog(CloudVirt.eventsLog, message);
		CloudVirt.globalQueue.addEvent(event);
	}
	public void run() {
		while(!localQueue.isEmpty()){
			processEvent(localQueue.extract());
		}
		
	}
	
}
