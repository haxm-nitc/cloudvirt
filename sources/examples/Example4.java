package examples;

/*
 * In this example we simulate 2 VMs in 2 Hosts. Each VM has a Task.
 * VM is in a Host inside a Datacenter.
 * 
 * */

import java.util.ArrayList;
import java.util.List;


import haxm.components.CPUTasklet;
import haxm.components.DIOTasklet;
import haxm.components.Datacenter;
import haxm.components.DatacenterConfiguration;
import haxm.components.Host;
import haxm.components.Task;
import haxm.components.Tasklet;
import haxm.components.VM;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;
import haxm.policies.TaskSchedulerPolicy;
import haxm.policies.TaskSchedulerPolicySimple;
import haxm.policies.VMProvisioningPolicySimple;
import haxm.policies.VirtUserPolicySimple;

public class Example4 {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		
		
		TaskSchedulerPolicy taskSchedulerPolicy1 = new TaskSchedulerPolicySimple();
		TaskSchedulerPolicy taskSchedulerPolicy2 = new TaskSchedulerPolicySimple();
		
		//(mips,memory,bw,policy)
		VM vm1 = new VM(4, 4000, 40, taskSchedulerPolicy1);
		VM vm2 = new VM(6, 6000, 60, taskSchedulerPolicy2);
		
		vm1.setUserId(virtUser.getId());
		vm2.setUserId(virtUser.getId());
		
		List<VM> vmList = new ArrayList<VM>();
		vmList.add(vm1);
		vmList.add(vm2);
		virtUser.submitVMs(vmList);
		
		DIOTasklet diskTasklet1 = new DIOTasklet(500);
		CPUTasklet cpuTasklet1 = new CPUTasklet(40000000);
		
		List<Tasklet> taskletList1 = new ArrayList<Tasklet>();
		taskletList1.add(diskTasklet1);
		taskletList1.add(cpuTasklet1);
		
		Task task1 = new Task(taskletList1);
		task1.setVm(vm1);
		task1.setUserId(virtUser.getId());
		
		List<Task> taskList1 = new ArrayList<Task>();
		taskList1.add(task1);
		virtUser.submitTasks(taskList1);

		DIOTasklet diskTasklet2 = new DIOTasklet(700);
		CPUTasklet cpuTasklet2 = new CPUTasklet(60000000);
		
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
		double mips1 = 5;
		double bw1 = 50;
			double memory1 = 5000;
		double disklatency1 = 50;
		
		Host host1 = new Host(mips1, memory1, bw1, disklatency1);
		double mips2 = 7;
		double bw2 = 70;
			double memory2 = 7000;
		double disklatency2 = 70;
		
		Host host2 = new Host(mips2, memory2, bw2, disklatency2);

		List<Host> hostList = new ArrayList<Host>();
		hostList.add(host1);
		hostList.add(host2);
		
		VMProvisioningPolicySimple vmProvisioner = new VMProvisioningPolicySimple();
		
		DatacenterConfiguration config = new DatacenterConfiguration(hostList, 0, null);
		Datacenter datacenter = new Datacenter(string, config, vmProvisioner);
		
		return datacenter;
	}
}
