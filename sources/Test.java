import haxm.components.Datacenter;
import haxm.core.CloudVirt;

public class Test {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = new Datacenter("Datacenter1");
		CloudVirt.startSimulation();
		CloudVirt.stopSimulation();	
	}
}