package haxm.policies;

import haxm.components.Host;
import haxm.components.Task;
import haxm.components.VM;
import haxm.core.CloudVirt;

import java.util.ArrayList;
import java.util.List;



public class TaskSchedulerPolicySimple extends TaskSchedulerPolicy{

	
	public TaskSchedulerPolicySimple() {
		// TODO Auto-generated constructor stub
		super();
		
	}
	
 
	@Override
	public double runTasks(long mips, long memory, double bw, double diskLatency) {
		// TODO Auto-generated method stub
		double currentTime = CloudVirt.getCurrentTime();
		if(getPreviousProcessedTime() == Double.MAX_VALUE){
			setPreviousProcessedTime(currentTime);
		}
		List<Task> taskList = getRunningTaskList();
		List<Task> removeList = new ArrayList<Task>();
		int numTasks = taskList.size();
		double minTime = Double.MAX_VALUE;
		for(Task task : taskList){
			task.updateExecution(currentTime - getPreviousProcessedTime(), mips/numTasks, memory/numTasks, bw/numTasks, diskLatency/numTasks);
			double remainingTime = task.getRemainingTime();
			if(remainingTime == 0){
				removeList.add(task);
				minTime = Double.MAX_VALUE;
			}else{
				if(minTime>remainingTime)
					minTime = remainingTime;
			}	
		}
		for(Task task : removeList){
			VM vm = task.getVm();
			vm.getFinishedTaskList().add(task);
		}
//		finishedTaskList.addAll(removeList);
		runningTaskList.removeAll(removeList);
		
		return minTime;
	}
	
}
