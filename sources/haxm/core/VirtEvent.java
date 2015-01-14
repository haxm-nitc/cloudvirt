package haxm.core;
public class VirtEvent implements Comparable<VirtEvent>{
	public static final int INVALID = -1;
	public static final int CREATE = 1;
	public static final int SEND = 2;
	public static final int WAKEUP = 3;
	private static int numEvents = 0;	
	
	public VirtEvent(){
		this.id = ++numEvents;	//A unique ID for each event
	}
	private int id;
	/**Source of the event.*/
	private int sourceId;

	/**Destination of the event.*/
	private int destinationId;

	/**Type of the event. Can be INVALID, CREATE, SEND, WAKEUP.*/
	private int type;

	/**Tag of the event. Specifies the purpose of the event.*/
	private TagEnum tag;

	/**Time at which the event is scheduled to occur.*/
	private double time;

	/**Data of the event. Contains additional information to process the event.*/
	private Object data;

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
