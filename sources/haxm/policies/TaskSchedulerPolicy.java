package haxm.policies;

import haxm.components.Task;

import java.util.ArrayList;
import java.util.List;

public abstract class TaskSchedulerPolicy {
	private List<Task> taskList;
	private List<Task> runningTaskList;
	private List<Task> finishedTaskList;
	private List<Task> failedTaskList;
	private int allocatedCores;
	private long allocatedMipsPerCore;
	private double allocatedBW;
	private double allocatedMemory;
	
	private double previousProcessedTime;

	
	public TaskSchedulerPolicy(){
		taskList = new ArrayList<Task>();
		runningTaskList = new ArrayList<Task>();
		finishedTaskList = new ArrayList<Task>();
		failedTaskList = new ArrayList<Task>();
		previousProcessedTime = Double.MAX_VALUE;
	}
	
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
	 * @return the finishedTaskList
	 */
	public List<Task> getFinishedTaskList() {
		return finishedTaskList;
	}
	/**
	 * @param finishedTaskList the finishedTaskList to set
	 */
	public void setFinishedTaskList(List<Task> finishedTaskList) {
		this.finishedTaskList = finishedTaskList;
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
	 * @return the allocatedCores
	 */
	public int getAllocatedCores() {
		return allocatedCores;
	}
	/**
	 * @param allocatedCores the allocatedCores to set
	 */
	public void setAllocatedCores(int allocatedCores) {
		this.allocatedCores = allocatedCores;
	}
	/**
	 * @return the allocatedMipsPerCore
	 */
	public long getAllocatedMipsPerCore() {
		return allocatedMipsPerCore;
	}
	/**
	 * @param allocatedMipsPerCore the allocatedMipsPerCore to set
	 */
	public void setAllocatedMipsPerCore(long allocatedMipsPerCore) {
		this.allocatedMipsPerCore = allocatedMipsPerCore;
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
	public double runTasks() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void addTask(Task task) {
		// TODO Auto-generated method stub
		getTaskList().add(task);
	}

}
