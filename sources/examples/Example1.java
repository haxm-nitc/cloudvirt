package examples;
import java.util.ArrayList;
import java.util.List;

import haxm.components.CPU;
import haxm.components.CPUTasklet;
import haxm.components.CloudRegistry;
import haxm.components.Core;
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

public class Example1 {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		CloudVirt.addUser(virtUser);
		
		TaskSchedulerPolicy taskSchedulerPolicy = new TaskSchedulerPolicySimple();
		
		//(mips,memory,bw,policy)
		VM vm = new VM(2, 1000, 10, taskSchedulerPolicy);
		List<VM> vmList = new ArrayList<VM>();
		vmList.add(vm);
		virtUser.submitVMs(vmList);
		
		CPUTasklet cpuTasklet = new CPUTasklet(10000000);
		DIOTasklet diskTasklet = new DIOTasklet(500);
		
		List<Tasklet> taskletList = new ArrayList<Tasklet>();
		taskletList.add(diskTasklet);
		taskletList.add(cpuTasklet);
		
		Task task = new Task(taskletList);
		task.setVm(vm);
		task.setUserId(virtUser.getId());
		
		List<Task> taskList = new ArrayList<Task>();
		taskList.add(task);
		virtUser.submitTasks(taskList);
		System.out.println("Simulation started");
		CloudVirt.startSimulation();	
		System.out.println("Simulation finished");
		
	}

	private static Datacenter createDatacenter(String string) {
		
		Storage storage = null;
		
		long mips = 5;
		double bw = 100; //mbps
		long memory = 8000;
		double disklatency=10;
		
		Host host = new Host(storage, mips, memory, bw,disklatency);
		
		List<Host> hostList = new ArrayList<Host>();
		hostList.add(host);
		
		VMProvisioningPolicySimple vmProvisioner = new VMProvisioningPolicySimple();
		
		DatacenterConfiguration config = new DatacenterConfiguration(hostList, null, 0, 0, 0, 0);
		Datacenter datacenter = new Datacenter(string, config, vmProvisioner);
		
		return datacenter;
	}
}