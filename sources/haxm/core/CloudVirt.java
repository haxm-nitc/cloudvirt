package haxm.core;

import haxm.VirtState;
import haxm.components.CloudRegistry;

import java.util.List;

public class CloudVirt{
	
	/**A CUTE LITTLE VERSION NUMBER AS A STRING :) ^_^*/
	public static  final String CLOUDVIRT_VERSION_STRING = "1.0";
	
	/**A CloudRegistry variable that has the information regarding the cloud resources*/
	private static CloudRegistry cloudRegistry;
	
	/**An event queue to hold the events*/
	private static EventQueue eventQueue;
	
	/**A list of simulation entities*/	
	private static List<VirtEntity> entityList;
	
	/**A State object to maintain the state of simulation*/	
	private static VirtState simulationState;
	
	/**A time variable to represent clock time of the simulation*/
	private  static double time;
	
	
	
	
	/** Method to start the simulation*/
	public static boolean startSimulation(){
		System.out.println("------------------SIMULATION STARTED------------------");
		return true;
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


