package examples;

/*
 * In this example we simulate 2 Tasks in 1 VM.
 * VM is in a Host inside a Datacenter.
 * 
 * */


import java.util.ArrayList;
import java.util.List;


import haxm.components.CPUTasklet;
import haxm.components.CloudRegistry;
import haxm.components.DIOTasklet;
import haxm.components.Datacenter;
import haxm.components.DatacenterConfiguration;
import haxm.components.Host;
import haxm.components.NIOTasklet;
import haxm.components.Storage;
import haxm.components.Task;
import haxm.components.Tasklet;
import haxm.components.VM;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;
import haxm.policies.TaskSchedulerPolicy;
import haxm.policies.TaskSchedulerPolicySimple;
import haxm.policies.VMProvisioningPolicySimple;
import haxm.policies.VirtUserPolicySimple;

public class Example3 {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		
		
		TaskSchedulerPolicy taskSchedulerPolicy = new TaskSchedulerPolicySimple();
		
		//(mips,memory,bw,policy)
		VM vm = new VM(2, 1000, 10, taskSchedulerPolicy);
		vm.setUserId(virtUser.getId());
		List<VM> vmList = new ArrayList<VM>();
		vmList.add(vm);
		virtUser.submitVMs(vmList);
		
		CPUTasklet cpuTasklet1 = new CPUTasklet(10000000);
		DIOTasklet diskTasklet1 = new DIOTasklet(500);
		
		List<Tasklet> taskletList1 = new ArrayList<Tasklet>();
		taskletList1.add(diskTasklet1);
		taskletList1.add(cpuTasklet1);
		
		Task task1 = new Task(taskletList1);
		task1.setVm(vm);
		task1.setUserId(virtUser.getId());
		
		NIOTasklet nioTasklet2 = new NIOTasklet(600);
		DIOTasklet diskTasklet2 = new DIOTasklet(200);
		
		List<Tasklet> taskletList2 = new ArrayList<Tasklet>();
		taskletList2.add(diskTasklet2);
		taskletList2.add(nioTasklet2);
		
		Task task2 = new Task(taskletList2);
		task2.setVm(vm);
		task2.setUserId(virtUser.getId());

		List<Task> taskList = new ArrayList<Task>();
		taskList.add(task1);
		taskList.add(task2);
		
		virtUser.submitTasks(taskList);
		System.out.println("Simulation started");
		CloudVirt.startSimulation();	
		System.out.println("Simulation finished");
		
	}

	private static Datacenter createDatacenter(String string) {
		
		Storage storage = null;
		
			double mips = 5;
		double bw = 100; //mbps
			double memory = 8000;
		double disklatency=10;
		
		Host host = new Host(storage, mips, memory, bw,disklatency);
		
		List<Host> hostList = new ArrayList<Host>();
		hostList.add(host);
		
		VMProvisioningPolicySimple vmProvisioner = new VMProvisioningPolicySimple();
		
		DatacenterConfiguration config = new DatacenterConfiguration(hostList, null, 0, null);
		Datacenter datacenter = new Datacenter(string, config, vmProvisioner);
		
		return datacenter;
	}
}
