import java.util.ArrayList;
import java.util.List;

import haxm.components.CPUTasklet;
import haxm.components.DIOTasklet;
import haxm.components.Datacenter;
import haxm.components.DatacenterConfiguration;
import haxm.components.Host;
import haxm.components.Storage;
import haxm.components.Task;
import haxm.components.Tasklet;
import haxm.components.VM;
import haxm.components.VMM;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;
import haxm.policies.TaskSchedulerPolicy;
import haxm.policies.TaskSchedulerPolicySimple;
import haxm.policies.VMProvisioningPolicySimple;
import haxm.policies.VirtUserPolicySimple;

public class Test {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		
		TaskSchedulerPolicy taskSchedulerPolicy = new TaskSchedulerPolicySimple();
		
		VM vm = new VM(2, 3, 1000, 500, taskSchedulerPolicy);
		List<VM> vmList = new ArrayList<VM>();
		vmList.add(vm);
		virtUser.submitVMs(vmList);
		
		CPUTasklet cpuTasklet = new CPUTasklet(1000);
		DIOTasklet diskTasklet = new DIOTasklet(200);
		
		List<Tasklet> taskletList = new ArrayList<Tasklet>();
		taskletList.add(diskTasklet);
		taskletList.add(cpuTasklet);
		
		Task task = new Task(taskletList);
		task.setVmId(vm.getId());
		task.setUserId(virtUser.getId());
		
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(task);
		virtUser.submitTasks(taskList);
		
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