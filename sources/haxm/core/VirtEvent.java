package haxm.core;
/**
 * this class models an event in the simulation
 *
 */
public class VirtEvent implements Comparable<VirtEvent>{

	/**
	 * total events
	 */
	private static int numEvents = 0;	
	/**
	 * id of event
	 */
	private int id;

	/**Source of the event.*/
	private int sourceId;

	/**Destination of the event.*/
	private int destinationId;

	/**Type of the event. Can be INVALID, SEND.*/
	private TagEnum type;

	/**Tag of the event. Specifies the purpose of the event.*/
	private TagEnum tag;

	/**Time at which the event is scheduled to occur.*/
	private double time;

	/**Data of the event. Contains additional information to process the event.*/
	private Object data;

	/**
	 * @return data of event
	 */
	public Object getData() {
		return data;
	}

	/**
	 * @param data to set data
	 */
	public void setData(Object data) {
		this.data = data;
	}

	/**
	 * @param sourceId
	 * @param destinationId
	 * @param type
	 * @param tag
	 * @param time
	 * @param data
	 * to initialize the parameters
	 */
	private void initParams(int sourceId, int destinationId, TagEnum type, TagEnum tag, double time, Object data){
		this.id = numEvents++;	//A unique ID for each event
		this.sourceId = sourceId;
		this.destinationId = destinationId;
		this.type = type;
		this.tag = tag;
		this.time = time;
		this.data = data;
	}
	
	/**
	 * @param sourceId
	 * @param destinationId
	 * @param type
	 * @param tag
	 * @param time
	 * constructor
	 */
	public VirtEvent(int sourceId, int destinationId, TagEnum type, TagEnum tag, double time){
		initParams(sourceId, destinationId, type, tag, time, null);
	}

	/**
	 * @param sourceId
	 * @param destinationId
	 * @param type
	 * @param tag
	 * @param time
	 * @param data
	 * constructor
	 */
	public VirtEvent(int sourceId, int destinationId, TagEnum type, TagEnum tag, double time, Object data){
		initParams(sourceId, destinationId, type, tag, time, data);
	}
	/**
	 * @return event id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return source id.
	 */
	public int getSourceId() {
		return sourceId;
	}

	/**
	 * @param sourceId to set source id.
	 */
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * @return destination id.
	 */
	public int getDestinationId() {
		return destinationId;
	}

	/**
	 * @param destinationId to set destination id.
	 */
	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

	/**
	 * @return tag.
	 */
	public TagEnum getTag() {
		return tag;
	}

	/**
	 * @param tag to set tag.
	 */
	public void setTag(TagEnum tag) {
		this.tag = tag;
	}

	/**
	 * @param type to set type
	 */
	public void setType(TagEnum type) {
		this.type = type;
	}

	/**
	 * @return type
	 */
	public TagEnum getType(){
		return type;
	}

	/**
	 * @param time to set time
	 */
	public void setTime(double time) {
		this.time = time;
	}

	/** 
	 * @return time.
	 */
	public double getTime() {
		return time;
	}	
	
	@Override
	public int compareTo(VirtEvent event) {
		if(this.time < event.time) 
			return -1;
		else if(this.time > event.time)
			return 1;
		else if(this.id < event.id)
			return -1;
		else if(this.id > event.id)
			return 1;
		return 0;
	}
}
