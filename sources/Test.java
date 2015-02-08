import haxm.components.Datacenter;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;

public class Test {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		//Datacenter datacenter1 = new Datacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1"); 
		CloudVirt.startSimulation();	
		System.out.println("done");
		
	}
}