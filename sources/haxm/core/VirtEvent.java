package haxm.core;
public class VirtEvent implements Comparable<VirtEvent>{

	private static int numEvents = 0;	
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	private void initParams(int sourceId, int destinationId, TagEnum type, TagEnum tag, double time, Object data){
		this.id = ++numEvents;	//A unique ID for each event
		this.sourceId = sourceId;
		this.destinationId = destinationId;
		this.type = type;
		this.tag = tag;
		this.time = time;
		this.data = data;
	}
	
	public VirtEvent(int sourceId, int destinationId, TagEnum type, TagEnum tag, double time){
		initParams(sourceId, destinationId, type, tag, time, null);
	}

	public VirtEvent(int sourceId, int destinationId, TagEnum type, TagEnum tag, double time, Object data){
		initParams(sourceId, destinationId, type, tag, time, data);
	}
	public int getId() {
		return id;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public int getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(int destinationId) {
		this.destinationId = destinationId;
	}

	public TagEnum getTag() {
		return tag;
	}

	public void setTag(TagEnum tag) {
		this.tag = tag;
	}

	public void setType(TagEnum type) {
		this.type = type;
	}

	public TagEnum getType(){
		return type;
	}

	public void setTime(double time) {
		this.time = time;
	}

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
