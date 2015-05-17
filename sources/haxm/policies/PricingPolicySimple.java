package haxm.policies;

import java.util.List;

import haxm.components.CPUTasklet;
import haxm.components.DIOTasklet;
import haxm.components.NIOTasklet;
import haxm.components.Task;
import haxm.components.Tasklet;
import haxm.components.VM;
import haxm.core.CloudVirt;

/**
 * simple implementation of pricing policy
 *
 */
public class PricingPolicySimple extends PricingPolicy{

	/**
	 * @param costPerInstruction
	 * @param costPerDio
	 * @param costPerNio
	 * @param costPerMemory
	 * constructor.
	 */
	public PricingPolicySimple(double costPerInstruction, double costPerDio, double costPerNio, double costPerMemory) {
		super(costPerInstruction, costPerNio, costPerMemory, costPerDio);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double costOfVM(VM vm) {
		// TODO Auto-generated method stub
		double cost = 0;
		List<Task> taskList = vm.getTaskList();
		for(Task task : taskList){
			cost += costOfTask(task);
		}
		
		return cost;
	}

	@Override
	public double costOfTask(Task task) {
		// TODO Auto-generated method stub
		double cost = 0;
		List<Tasklet> taskletList = task.getFinishedTaskletList();
		for(Tasklet tasklet : taskletList){
			switch(tasklet.getTaskletType()){
				case Tasklet.CPU:
					CPUTasklet cpuTasklet = (CPUTasklet) tasklet;
					cost += cpuTasklet.getInstructionLength() * getCostPerInstruction();
					break;
				case Tasklet.DISKIO:
					DIOTasklet dioTasklet = (DIOTasklet) tasklet;
					cost += dioTasklet.getData() * getCostPerDio();
					break;
				case Tasklet.NETWORKIO:
					NIOTasklet nioTasklet = (NIOTasklet) tasklet;
					cost += nioTasklet.getData() * getCostPerNio();
					break;
			}
		}
		
		return cost;
	}

}
