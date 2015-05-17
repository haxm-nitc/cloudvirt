package haxm.components;

import haxm.VirtState;
import haxm.VirtStateEnum;
import haxm.core.CloudVirt;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

/**
 * this class models a task object.
 *
 */
public class Task {
	/**
	 * id of task.
	 */
	private int id;
	/**
	 * total number of tasks.
	 */
	private static int numTasks = 0;
	/**
	 * list of tasklets inside task.
	 */
	private List<Tasklet> taskletList;
	/**
	 * finished tasklets' list.
	 */
	private List<Tasklet> finishedTaskletList;
	/**
	 * @return list of finshed tasklets.
	 */
	public List<Tasklet> getFinishedTaskletList() {
		return finishedTaskletList;
	}

	/**
	 * @param finishedTaskletList to set finished tasklet list.
	 */
	public void setFinishedTaskletList(List<Tasklet> finishedTaskletList) {
		this.finishedTaskletList = finishedTaskletList;
	}
	/**
	 * start time of task.
	 */
	private double startTime;
	/**
	 * end time of task.
	 */
	private double finishTime;
	/**
	 * remaining time of task.
	 */
	private double remainingTime;
	/**
	 * VM on which the task is to be executed.
	 */
	private VM vm;
	/**
	 * user who submitted the task.
	 */
	private int userId;
	/**
	 * datacenter on which the task is executed.
	 */
	private int datacenterId;
	/**
	 * current state of the task.
	 */
	private VirtState taskState;
	


	/**
	 * @param taskletlist to set taskletlist.
	 * constructor.
	 */
	public Task(List<Tasklet> taskletlist){
		this.id = ++numTasks;
		this.setTaskletList(taskletlist);
		finishedTaskletList = new ArrayList<Tasklet>();
		taskState = new VirtState(VirtStateEnum.INVALID);
		
	}
	
	/**
	 * @return taskletlist.
	 */
	public List<Tasklet> getTaskletList() {
		return taskletList;
	}

	/**
	 * @param taskletList to set tasklet list.
	 */
	public void setTaskletList(List<Tasklet> taskletList) {
		this.taskletList = taskletList;
	}

	/**
	 * @param tasklet tasklet to be added.
	 * adds tasklet to list.
	 */
	public void addTasklet(Tasklet tasklet){
		this.getTaskletList().add(tasklet);
	}
	
	/**
	 * @param tasklets add list of tasklets.
	 */
	public void addAllTasklets(List<Tasklet> tasklets){
		this.getTaskletList().addAll(tasklets);
	}
	/**
	 * @return userid of task.
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId to set user id.
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * @return datacenter id.
	 */
	public int getDatacenterId() {
		return datacenterId;
	}

	/**
	 * @param datacenterId to set datacenterid.
	 */
	public void setDatacenterId(int datacenterId) {
		this.datacenterId = datacenterId;
	}

	/**
	 * @param duration
	 * @param mips
	 * @param memory
	 * @param bw
	 * @param diskLatency
	 * to execute the task from previoustime to currenttime.
	 */
	public void updateExecution(double duration,  double mips,  double memory, double bw, double diskLatency) {
		CloudVirt.tasksLog.append("[Task UE] Task id:"+getId()+" mips:"+mips+" memory:"+memory+" bw:"+bw+" dislatency:"+diskLatency+" duration:"+duration 
			+" userid:"+userId+" datacenterid:"+datacenterId+" vmid:"+vm.getId());
		if(duration == 0){
			setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
			setStartTime(CloudVirt.getCurrentTime());
			return;
		}
		if(taskState.getState() == VirtStateEnum.INVALID){
			taskState.setState(VirtStateEnum.RUNNING);
		}
		Tasklet tasklet = taskletList.get(0);
		switch(tasklet.getTaskletType()){
			case Tasklet.CPU:
				CPUTasklet cpuTasklet = (CPUTasklet) tasklet;
				long remInstr = cpuTasklet.getRemainingInstructionLength();
				if(remInstr <= duration * mips * 1000000){
					taskletList.remove(0);
					finishedTaskletList.add(tasklet);
					if(getTaskletList().size() == 0){
						setFinishTime(CloudVirt.getCurrentTime() - (duration - remInstr/(mips*1000000)));
						finishTask();
						return;
					}
					updateExecution(duration - remInstr/(mips*1000000), mips, memory, bw, diskLatency);
				}else{
					cpuTasklet.setRemainingInstructionLength((long) (remInstr - duration*mips*1000000));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				break;
			case Tasklet.DISKIO:
				DIOTasklet dioTasklet = (DIOTasklet) tasklet;
					double remainingDIOData = dioTasklet.getRemainingData();
				if(remainingDIOData <= duration * diskLatency){
					taskletList.remove(0);
					finishedTaskletList.add(tasklet);
					if(getTaskletList().size() == 0){
						setFinishTime(CloudVirt.getCurrentTime() - (duration - remainingDIOData/diskLatency));
						finishTask();
						return;
					}
					updateExecution(duration - remainingDIOData/diskLatency, mips, memory, bw, diskLatency);
				}else{
					dioTasklet.setRemainingData((double) (remainingDIOData - duration*diskLatency));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				break;
			case Tasklet.NETWORKIO:
				//System.out.println("niot");
				NIOTasklet nioTasklet = (NIOTasklet) tasklet;
					double remainingNIOData = nioTasklet.getRemainingData();
				if(remainingNIOData <= duration * bw){
					taskletList.remove(0);
					finishedTaskletList.add(tasklet);
					if(getTaskletList().size() == 0){
						setFinishTime(CloudVirt.getCurrentTime() - (duration - remainingNIOData/bw));
						finishTask();
						return;
					}
					updateExecution(duration - remainingNIOData/bw, mips, memory, bw, diskLatency);
				}else{
					nioTasklet.setRemainingData((double) (remainingNIOData - duration*bw));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				
				break;				
		}
		
		
		
	}

	/**
	 * @return the current state of task.
	 */
	public VirtState getTaskState() {
		return taskState;
	}

	/**
	 * @param taskState to set task state.
	 */
	public void setTaskState(VirtState taskState) {
		this.taskState = taskState;
	}

	/**
	 *  to notify user that task is finished and cleanup.
	 */
	private void finishTask() {
		// TODO Auto-generated method stub
		setRemainingTime(0);
		taskState.setState(VirtStateEnum.FINISHED);
		Datacenter datacenter = (Datacenter) CloudVirt.entityHolder.getEntityByID(datacenterId);
		datacenter.notifyTaskFinished(this, getUserId());	
	}

	/**
	 * @param mips
	 * @param memory
	 * @param bw
	 * @param diskLatency
	 * @return the remaining time for task.
	 */
	private double calculateRemainingTime(double mips,  double memory, double bw,
			double diskLatency) {
		// TODO Auto-generated method stub
		double time = 0;
		for(Tasklet tasklet : taskletList){
			time += tasklet.calculateRemainingTime(mips, memory, bw, diskLatency);
		}
		CloudVirt.tasksLog.append("[Task CR] Task id:"+getId()+" mips:"+mips+" memory:"+memory+" bw:"+bw+" disklatency:"+diskLatency+" remaining time:"+time);
		return time;
	}

	/**
	 * @param remainingTime the remainingTime to set
	 */
	public void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}

	/**
	 * @return remaining time for task.
	 */
	public double getRemainingTime() {
		// TODO Auto-generated method stub
		return remainingTime;
	}

	/**
	 * @return the vm
	 */
	public VM getVm() {
		return vm;
	}

	/**
	 * @param vm the vm to set
	 */
	public void setVm(VM vm) {
		this.vm = vm;
	}

	/**
	 * @return id of task.
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id to set id of task.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return start time of task.
	 */
	public double getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime to set starttime of task.
	 */
	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return finish time of task.
	 */
	public double getFinishTime() {
		return finishTime;
	}

	/**
	 * @param finishTime to set finish time of task.
	 */
	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

}
