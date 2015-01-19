package haxm.core;

import haxm.LogFile;
import haxm.VirtState;
import haxm.components.CloudRegistry;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CloudVirt{
	
	/**A CUTE LITTLE VERSION NUMBER AS A STRING :) ^_^*/
	public static  final String CLOUDVIRT_VERSION_STRING = "1.0";
	
	/**A CloudRegistry variable that has the information regarding the cloud resources*/
	private static CloudRegistry cloudRegistry;
	
	/**An event queue to hold the events*/
	private static EventQueue globalQueue;
	
	/**A list of simulation entities*/	
	private static List<VirtEntity> entityList;
	
	/**A State object to maintain the state of simulation*/	
	private static VirtState simulationState;
	
	/**A time variable to represent clock time of the simulation*/
	private  static double time;
	
	private static LogFile mainLog = new LogFile("log.txt");
	private static LogFile eventsLog = new LogFile("events.txt");
	private static LogFile entityLog = new LogFile("entity.txt");
	
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
		invalidateParams();
		finishSimulation();
		
		writeLog(null, "=========================SIMULATION ENDED=========================");
		
		return duration;
	}

	private static void finishSimulation() {
		// TODO Auto-generated method stub
		
	}

	private static void invalidateParams() {
		// TODO Auto-generated method stub
		
	}

	private static double simulate() {

		while( (!toTerminate()) || nextTick()){
			if(toPause()){
				
			}
		}

		// TODO Auto-generated method stub
		return 0.0;
		
	}

	private static boolean toPause() {
		// TODO Auto-generated method stub
		return false;
	}

	private static boolean nextTick() {
		boolean processmore;
		if(globalQueue.empty()){
			return false;
		}else{
			processmore = true;
			List<VirtEvent> removeList = new LinkedList<VirtEvent>();
			VirtEvent currentEvent = globalQueue.next();
			VirtEvent firstEvent = currentEvent;
			
			do{
				processEvent(currentEvent);
				removeList.add(currentEvent);
				if(globalQueue.empty()){
					processmore = false;
				}else{
					currentEvent = globalQueue.next();
					if(firstEvent.getTime() != currentEvent.getTime()){
						processmore = false;
					}
				}
			}while(processmore);
			globalQueue.removeAll(removeList);
			return globalQueue.empty();
		}
	}

	private static void processEvent(VirtEvent currentEvent) {
		// TODO Auto-generated method stub
		
	}

	private static boolean toTerminate() {
		// TODO Auto-generated method stub
		return false;
	}

	/** Method to stop the simulation*/
	public static boolean stopSimulation(){
		return true;
	}

	/** Method to pause the simulation*/
	public static boolean pauseSimulation(){
		return true;
	}

	/** Method to abruptly terminate the simulation*/
	public static boolean abruptlyTerminateSimulation(){
		return true;
	}
}


