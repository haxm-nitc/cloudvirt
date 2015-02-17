import policies.VirtUserPolicySimple;
import haxm.components.Datacenter;
import haxm.components.DatacenterConfiguration;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;

public class Test {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		CloudVirt.startSimulation();	
		System.out.println("done");
		
	}

	private static Datacenter createDatacenter(String string) {
		// TODO Auto-generated method stub
		DatacenterConfiguration config = new DatacenterConfiguration(null, null, 0, null, 0, 0, 0);
		Datacenter datacenter = new Datacenter(string, config);
		return datacenter;
	}
}