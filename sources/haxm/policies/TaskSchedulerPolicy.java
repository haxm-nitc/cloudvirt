package haxm.policies;

import haxm.components.Task;
import haxm.components.VM;

import java.util.ArrayList;
import java.util.List;

/**
 * this class models the task scheduler policy
 *
 */
public abstract class TaskSchedulerPolicy {
	/**
	 * list of tasks.
	 */
	private List<Task> taskList;
	/**
	 * list of running tasks.
	 */
	protected List<Task> runningTaskList;
//	protected List<Task> finishedTaskList;
	/**
	 * list of failed tasks
	 */
	private List<Task> failedTaskList;
	/**
	 * allocated bw
	 */
	private double allocatedBW;
	/**
	 * allocated memory
	 */
	private double allocatedMemory;
	/**
	 * vm for applying the policy
	 */
	protected VM vm;
	/**
	 *  previous processed time
	 */
	private double previousProcessedTime;

	
	/**
	 *  constructor
	 */
	public TaskSchedulerPolicy(){
		taskList = new ArrayList<Task>();
		runningTaskList = new ArrayList<Task>();
//		finishedTaskList = new ArrayList<Task>();
		failedTaskList = new ArrayList<Task>();
		previousProcessedTime = Double.MAX_VALUE;
	}
	/**
	 * @return vm
	 */
	public VM getVm() {
		return vm;
	}
	/**
	 * @param vm vm to be set
	 */
	public void setVm(VM vm) {
		this.vm = vm;
	}
/*
	public void submitTasks(List<Task> taskList) {
		// TODO Auto-generated method stub
		this.getTaskList().addAll(taskList);
		this.getRunningTaskList().addAll(taskList);
	}
*/
	/**
	 * @return the runningTaskList
	 */
	public List<Task> getRunningTaskList() {
		return runningTaskList;
	}
	/**
	 * @param runningTaskList the runningTaskList to set
	 */
	public void setRunningTaskList(List<Task> runningTaskList) {
		this.runningTaskList = runningTaskList;
	}
	/**
	 * @return the failedTaskList
	 */
	public List<Task> getFailedTaskList() {
		return failedTaskList;
	}
	/**
	 * @param failedTaskList the failedTaskList to set
	 */
	public void setFailedTaskList(List<Task> failedTaskList) {
		this.failedTaskList = failedTaskList;
	}
	/**
	 * @return the allocatedBW
	 */
	public double getAllocatedBW() {
		return allocatedBW;
	}
	/**
	 * @param allocatedBW the allocatedBW to set
	 */
	public void setAllocatedBW(double allocatedBW) {
		this.allocatedBW = allocatedBW;
	}
	/**
	 * @return the allocatedMemory
	 */
	public double getAllocatedMemory() {
		return allocatedMemory;
	}
	/**
	 * @param allocatedMemory the allocatedMemory to set
	 */
	public void setAllocatedMemory(double allocatedMemory) {
		this.allocatedMemory = allocatedMemory;
	}

	/**
	 * @return the taskList
	 */
	public List<Task> getTaskList() {
		return taskList;
	}
	/**
	 * @param taskList the taskList to set
	 */
	public void setTaskList(List<Task> taskList) {
		this.taskList = taskList;
	}
	/**
	 * @return the previousProcessedTime
	 */
	public double getPreviousProcessedTime() {
		return previousProcessedTime;
	}
	/**
	 * @param previousProcessedTime the previousProcessedTime to set
	 */
	public void setPreviousProcessedTime(double previousProcessedTime) {
		this.previousProcessedTime = previousProcessedTime;
	}
	/**
	 * @param mips
	 * @param memory
	 * @param bw
	 * @param diskLatency
	 * @return time after completion
	 * runs the tasks
	 */
	public abstract double runTasks(double mips,  double memory, double bw, double diskLatency);

	/**
	 * @param task task to be added
	 */
	public void addTask(Task task) {
		// TODO Auto-generated method stub
		getTaskList().add(task);
		getRunningTaskList().add(task);
	}


}
