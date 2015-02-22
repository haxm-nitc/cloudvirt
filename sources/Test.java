import java.util.ArrayList;
import java.util.List;

import haxm.components.Datacenter;
import haxm.components.DatacenterConfiguration;
import haxm.components.Host;
import haxm.components.Storage;
import haxm.components.VM;
import haxm.components.VMM;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;
import haxm.policies.TaskSchedulerPolicy;
import haxm.policies.TaskSchedulerPolicySimple;
import haxm.policies.VMProvisioningPolicy;
import haxm.policies.VMProvisioningPolicySimple;
import haxm.policies.VirtUserPolicySimple;

public class Test {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		
		TaskSchedulerPolicy taskSchedulerPolicy = new TaskSchedulerPolicySimple();
		VM vm = new VM(virtUser.getId(), 1000, 500, taskSchedulerPolicy);
		List<VM> vmList = new ArrayList<VM>();
		vmList.add(vm);
		virtUser.setVmList(vmList);
		
		CloudVirt.startSimulation();	
		System.out.println("done");
		
	}

	private static Datacenter createDatacenter(String string) {
		VMM vmm = new VMM("Xen");
		Storage storage = null;
		double bw = 3000;
		long memory = 8000;
		Host host = new Host(vmm, storage, memory, bw);
		List<Host> hostList = new ArrayList<Host>();
		hostList.add(host);
		VMProvisioningPolicySimple vmProvisioner = new VMProvisioningPolicySimple();
		DatacenterConfiguration config = new DatacenterConfiguration(hostList, null, 0, 0, 0, 0);
		Datacenter datacenter = new Datacenter(string, config, vmProvisioner);
		return datacenter;
	}
}