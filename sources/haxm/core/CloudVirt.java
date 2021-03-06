package haxm.core;
/*
 * time - 1 second
 * bw   - mega Bytes per second
 * disklatency - mega Bytes per second
 * data -  mega bytes
 * 
 * */
import haxm.LogFile;
import haxm.VirtState;
import haxm.VirtStateEnum;
import haxm.components.CloudRegistry;
import haxm.components.VirtUser;
import haxm.holders.EntityHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * This class is the backbone of the simulation.
 *
 */
public class CloudVirt{
	
	/**A CUTE LITTLE VERSION NUMBER AS A STRING :) ^_^*/
	public static  final String CLOUDVIRT_VERSION_STRING = "1.0";
	
	/**A CloudRegistry variable that has the information regarding the cloud resources*/
	public static CloudRegistry cloudRegistry;
	
	/**An event queue to hold the events*/
	public static EventQueue globalQueue;
	
	/**A list of simulation entities*/	
	public static EntityHolder entityHolder;
	

	/**A State object to maintain the state of simulation*/	
	private static VirtState simulationState;
	
	/**A time variable to represent clock time of the simulation*/
	private  static double currentTime;
	public static final int MILLION = 1000000;
	/**
	 * main log file
	 */
	public static LogFile mainLog;
	/**
	 * log file for events.
	 */
	public static LogFile eventsLog;
	/**
	 * log file for entities.
	 */
	public static LogFile entityLog;
	/**
	 * log file for vm's.
	 */
	public static LogFile vmsLog;
	/**
	 * log file for tasks.
	 */
	public static LogFile tasksLog;
	
	/**
	 * to initialize the simulation environment.
	 */
	public static void initSimulationEnvironment(){
		
		globalQueue =  new EventQueue();
		entityHolder = new EntityHolder();
		simulationState = new VirtState(VirtStateEnum.INVALID);
		currentTime = 0.00;
		
		mainLog = new LogFile("log.txt");
		eventsLog = new LogFile("events.txt");
		entityLog = new LogFile("entity.txt");
		vmsLog = new LogFile("vms.txt");
		tasksLog = new LogFile("tasks.txt");
		
		cloudRegistry = new CloudRegistry("CloudRegistry1");
		
	}
	
	/**
	 * @param time to set currenttime
	 */
	public static void setCurrentTime(double time){
		currentTime = time;
	}

	/**
	 * @return currenttime.
	 */
	public static double getCurrentTime(){
		return currentTime;
	}
	
	/**
	 * @param logFile target logfile 
	 * @param message message
	 * writes to log file.
	 */
	public static void writeLog(LogFile logFile, String message){
		if(logFile != null){
			logFile.append(message);
		}
		mainLog.append(message);
	}
	
	/** Method to start the simulation*/
	public static double startSimulation(){
		double duration;
		writeLog(null, "=========================SIMULATION STARTED=========================");
		duration = simulate();		
		finishSimulation();
		return duration;
	}

	
	/**
	 *  cleanup code to finish simulation.
	 */
	public static void finishSimulation() {
		entityHolder.runAll();
		entityHolder.shutdownEntities();
		writeLog(null, "=========================SIMULATION FINISHED==========================");
		invalidateSimulationEnvironment();
	}
	
	/**
	 *  invalidate the simulation environment.
	 */
	private static void invalidateSimulationEnvironment() {
		cloudRegistry = null;
		globalQueue =  null;
		entityHolder = null;
		simulationState = null;
		currentTime = -1;
		
		mainLog.close();
		mainLog = null;
		
		eventsLog.close();
		eventsLog = null;
		
		entityLog.close();
		entityLog = null;
		
	}

	/**
	 * @return finish time of simulation.
	 * starts the simulation.
	 */
	private static double simulate() {
		if(simulationState.getState() != VirtStateEnum.RUNNING){
			simulationState.setState(VirtStateEnum.RUNNING);
			entityHolder.startEntities();
		}
		while( (!toTerminate()) && nextTick()){
		}
		
		return getCurrentTime();		
	}
	
	/**
	 * @return true/false depending on the globalqueue size
	 * models a clock tick in the simulation.
	 */
	private static boolean nextTick() {
		
		entityHolder.runAll();
		
		if(globalQueue.isEmpty()){
			return false;
		}else{
			boolean processmore = true;
			List<VirtEvent> removeList = new LinkedList<VirtEvent>();
			Iterator<VirtEvent> eventIterator = globalQueue.iterator();
			VirtEvent currentEvent = eventIterator.next();
			VirtEvent firstEvent = currentEvent;
			do{
				processEvent(currentEvent);
				removeList.add(currentEvent);
				if(!eventIterator.hasNext()){
					processmore = false;
				}else{
					currentEvent = eventIterator.next();
					if(firstEvent.getTime() != currentEvent.getTime()){
						processmore = false;
					}
				}
			}while(processmore);
			
			globalQueue.removeAll(removeList);
			return true;
		}	
	}

	/**
	 * @param event event send
	 * sends an event to another entity's local queue.
	 */
	private static void processEvent(VirtEvent event) {
/*		
		String message = "[GtoL] EventID:"+event.getId()
				+"	Source:"+CloudVirt.entityHolder.getEntityNameByID(event.getSourceId())
				+",ID-"+event.getSourceId()
				+"	Destination:"+CloudVirt.entityHolder.getEntityNameByID(event.getDestinationId())
				+",ID-"+event.getDestinationId()
				+ "	Type:"+event.getType()
				+"	Tag:"+event.getTag()
				+"	Time:"+event.getTime();
		CloudVirt.writeLog(CloudVirt.eventsLog, message);
*/
		setCurrentTime(event.getTime());
		switch(event.getType()){
			case INVALID:
				break;
			case SEND:
				int destinationId = event.getDestinationId();
				VirtEntity destinationEntity = entityHolder.getEntityByID(destinationId);
				destinationEntity.addToLocalQueue(event);
				break;
		}
	}

	/**
	 * @return true.
	 */
	private static boolean toTerminate() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @param entity to add entity.
	 */
	public static void addEntity(VirtEntity entity){
		entityHolder.addEntity(entity);
	}
}