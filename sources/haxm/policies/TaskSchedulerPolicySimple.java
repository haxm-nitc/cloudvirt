package haxm.policies;

import haxm.VirtStateEnum;
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
	public double runTasks(double mips,  double memory, double bw, double diskLatency) {
		// TODO Auto-generated method stub
		//System.out.println(mips);
		List<Task> runTaskList = getRunningTaskList();
		double minTime = Double.MAX_VALUE;
		int numTasks = runTaskList.size();
		//System.out.println(numTasks+"      "+mips);
		if(numTasks == 0){
			return minTime;
		}
		double currentTime = CloudVirt.getCurrentTime();
		if(getPreviousProcessedTime() == Double.MAX_VALUE){
			setPreviousProcessedTime(currentTime);
		}
		List<Task> removeList = new ArrayList<Task>();
		for(Task task : runTaskList){
			task.updateExecution(currentTime - getPreviousProcessedTime(), mips/numTasks, memory/numTasks, bw/numTasks, diskLatency/numTasks);
			double remainingTime = task.getRemainingTime();
			if(remainingTime == 0){
				removeList.add(task);
			}else if(minTime > remainingTime) {
				minTime = remainingTime;
			}
			
		}
		getRunningTaskList().removeAll(removeList);
		setPreviousProcessedTime(currentTime);
		return minTime;
	}		
}
