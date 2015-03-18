package haxm.policies;

import haxm.components.Host;
import haxm.components.Task;
import haxm.core.CloudVirt;

import java.util.List;

public class TaskSchedulerPolicySimple extends TaskSchedulerPolicy{

	
	public TaskSchedulerPolicySimple() {
		// TODO Auto-generated constructor stub
		super();
		
	}
	
	public double runTasks() {
		double currentTime = CloudVirt.getCurrentTime();
		if(getPreviousProcessedTime() == Double.MAX_VALUE){
			setPreviousProcessedTime(currentTime);
		}
		List<Task> taskList = getRunningTaskList();
		double minTime = Double.MAX_VALUE;
		for(Task task : taskList){
			task.updateExecution(currentTime - getPreviousProcessedTime());
			double remainingTime = task.getRemainingTime();
			if(minTime>remainingTime)
				minTime = remainingTime;
		}
		return minTime;
	
	}
	
}
