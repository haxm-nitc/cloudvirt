package examples;
import java.util.ArrayList;
import java.util.List;


import haxm.components.CPUTasklet;
import haxm.components.CloudRegistry;
import haxm.components.DIOTasklet;
import haxm.components.Datacenter;
import haxm.components.DatacenterConfiguration;
import haxm.components.Host;
import haxm.components.NIOTasklet;
import haxm.components.Task;
import haxm.components.Tasklet;
import haxm.components.VM;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;
import haxm.policies.TaskSchedulerPolicy;
import haxm.policies.TaskSchedulerPolicySimple;
import haxm.policies.VMProvisioningPolicySimple;
import haxm.policies.VirtUserPolicySimple;

public class Example5 {

	public static void main(String[] args) {
		CloudVirt.initSimulationEnvironment();
		Datacenter datacenter1 = createDatacenter("Datacenter1");
		VirtUser virtUser = new VirtUser("virtUser1", new VirtUserPolicySimple()); 
		
		
		TaskSchedulerPolicy taskSchedulerPolicy = new TaskSchedulerPolicySimple();
		
		//(mips,memory,bw,policy)
		VM vm = new VM(3, 3000, 9, taskSchedulerPolicy);
		vm.setUserId(virtUser.getId());
		List<VM> vmList = new ArrayList<VM>();
		vmList.add(vm);
		virtUser.submitVMs(vmList);
		
		Task task1 = createTask(10, 100, 0);
		task1.setVm(vm);
		task1.setUserId(virtUser.getId());

		Task task2 = createTask(20, 200, 0);
		task2.setVm(vm);
		task2.setUserId(virtUser.getId());

		Task task3 = createTask(30, 300, 0);
		task3.setVm(vm);
		task3.setUserId(virtUser.getId());

		Task task4 = createTask(30, 300, 0);
		task4.setVm(vm);
		task4.setUserId(virtUser.getId());

		List<Task> taskList = new ArrayList<Task>();
		taskList.add(task1);
		taskList.add(task2);
		taskList.add(task3);
		taskList.add(task4);
		virtUser.submitTasks(taskList);
		
		System.out.println("Simulation started");
		CloudVirt.startSimulation();	
		System.out.println("Simulation finished");
		
	}

	private static Task createTask(int instr, int dio, int nio) {
		CPUTasklet cpuTasklet;
		DIOTasklet dioTasklet;
		NIOTasklet nioTasklet;
		
		List<Tasklet> taskletList = new ArrayList<Tasklet>();
		
		if(instr != 0){
			cpuTasklet = new CPUTasklet(instr*CloudVirt.MILLION);
			taskletList.add(cpuTasklet);
		}
		if(dio != 0){
			dioTasklet = new DIOTasklet(dio);
			taskletList.add(dioTasklet);
		}
		if(nio != 0){
			nioTasklet = new NIOTasklet(dio);
			taskletList.add(nioTasklet);
		}
		return (new Task(taskletList));
	}

	private static Datacenter createDatacenter(String string) {		
		double mips = 5;
		double bw = 100; //mbps
		double memory = 8000;
		double disklatency=9;
		
		Host host = new Host(mips, memory, bw,disklatency);
		
		List<Host> hostList = new ArrayList<Host>();
		hostList.add(host);
		
		VMProvisioningPolicySimple vmProvisioner = new VMProvisioningPolicySimple();
		
		DatacenterConfiguration config = new DatacenterConfiguration(hostList, 0, null);
		Datacenter datacenter = new Datacenter(string, config, vmProvisioner);
		
		return datacenter;
	}
}
