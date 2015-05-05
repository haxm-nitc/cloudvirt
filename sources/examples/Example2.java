package examples;

/*
 * In this example we simulate 2 VMs with 2 Tasks each(each Task has 2 Tasklets).
 * Those 2 VMs belong to same Host.
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
import haxm.components.Storage;
import haxm.components.Task;
import haxm.components.Tasklet;
import haxm.components.VM;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;
import haxm.policies.PricingPolicy;
import haxm.policies.PricingPolicySimple;
import haxm.policies.TaskSchedulerPolicy;
import haxm.policies.TaskSchedulerPolicySimple;
import haxm.policies.VMProvisioningPolicySimple;
import haxm.policies.VirtUserPolicySimple;

public class Example2{

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		
		
		TaskSchedulerPolicy taskSchedulerPolicy1 = new TaskSchedulerPolicySimple();
		
		//(mips,memory,bw,policy)
		VM vm1 = new VM(2, 1000, 10, taskSchedulerPolicy1);
		vm1.setUserId(virtUser.getId());
		
		TaskSchedulerPolicy taskSchedulerPolicy2 = new TaskSchedulerPolicySimple();
		VM vm2 = new VM(5, 1000, 15, taskSchedulerPolicy2);
		vm2.setUserId(virtUser.getId());
		
		List<VM> vmList = new ArrayList<VM>();
		vmList.add(vm1);
		vmList.add(vm2);

		
		virtUser.submitVMs(vmList);
		
		DIOTasklet diskTasklet1 = new DIOTasklet(500);
		CPUTasklet cpuTasklet1 = new CPUTasklet(10000000);
		
		List<Tasklet> taskletList1 = new ArrayList<Tasklet>();
		taskletList1.add(diskTasklet1);
		taskletList1.add(cpuTasklet1);
		
		Task task1 = new Task(taskletList1);
		task1.setVm(vm1);
		task1.setUserId(virtUser.getId());
		
		List<Task> taskList1 = new ArrayList<Task>();
		taskList1.add(task1);
		virtUser.submitTasks(taskList1);

		DIOTasklet diskTasklet2 = new DIOTasklet(1000);
		CPUTasklet cpuTasklet2 = new CPUTasklet(30000000);

		
		List<Tasklet> taskletList2 = new ArrayList<Tasklet>();
		taskletList2.add(diskTasklet2);
		taskletList2.add(cpuTasklet2);
		
		Task task2 = new Task(taskletList2);
		task2.setVm(vm2);
		task2.setUserId(virtUser.getId());
		
		List<Task> taskList2 = new ArrayList<Task>();
		taskList2.add(task2);
		virtUser.submitTasks(taskList2);

		System.out.println("Simulation started");
		CloudVirt.startSimulation();	
		System.out.println("Simulation finished");
		
	}

	private static Datacenter createDatacenter(String string) {
		
		Storage storage = null;
		
			double mips = 20;
		double bw = 100; //mbps
			double memory = 8000;
		double disklatency=10;
		
		Host host = new Host(storage, mips, memory, bw,disklatency);
		
		List<Host> hostList = new ArrayList<Host>();
		hostList.add(host);
		
		VMProvisioningPolicySimple vmProvisioner = new VMProvisioningPolicySimple();
		PricingPolicy pricingPolicy = new PricingPolicySimple(1, 2, 3, 4);
		
		DatacenterConfiguration config = new DatacenterConfiguration(hostList, null, 0, pricingPolicy);
		Datacenter datacenter = new Datacenter(string, config, vmProvisioner);
		
		return datacenter;
	}
}
