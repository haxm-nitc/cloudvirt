package haxm.components;

import haxm.VirtState;
import haxm.VirtStateEnum;
import haxm.core.CloudVirt;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Task {
	private int id;
	private static int numTasks = 0;
	private List<Tasklet> taskletList;
	private List<Tasklet> finishedTaskletList;
	public List<Tasklet> getFinishedTaskletList() {
		return finishedTaskletList;
	}

	public void setFinishedTaskletList(List<Tasklet> finishedTaskletList) {
		this.finishedTaskletList = finishedTaskletList;
	}
	private double startTime;
	private double finishTime;
	private double remainingTime;
	private VM vm;
	private int userId;
	private int datacenterId;
	private VirtState taskState;
	


	public Task(List<Tasklet> taskletlist){
		this.id = ++numTasks;
		this.setTaskletList(taskletlist);
		finishedTaskletList = new ArrayList<Tasklet>();
		taskState = new VirtState(VirtStateEnum.INVALID);
		
	}
	
	public List<Tasklet> getTaskletList() {
		return taskletList;
	}

	public void setTaskletList(List<Tasklet> taskletList) {
		this.taskletList = taskletList;
	}

	public void addTasklet(Tasklet tasklet){
		this.getTaskletList().add(tasklet);
	}
	
	public void addAllTasklets(List<Tasklet> tasklets){
		this.getTaskletList().addAll(tasklets);
	}
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(int datacenterId) {
		this.datacenterId = datacenterId;
	}

	public void updateExecution(double duration, long mips, long memory, double bw, double diskLatency) {
		CloudVirt.tasksLog.append("[Task UE] mips:"+mips+" memory:"+memory+" bw:"+bw+" dislatency:"+diskLatency+" duration:"+duration 
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
				long remainingDIOData = dioTasklet.getRemainingData();
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
					dioTasklet.setRemainingData((long) (remainingDIOData - duration*diskLatency));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				break;
			case Tasklet.NETWORKIO:
				//System.out.println("niot");
				NIOTasklet nioTasklet = (NIOTasklet) tasklet;
				long remainingNIOData = nioTasklet.getRemainingData();
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
					nioTasklet.setRemainingData((long) (remainingNIOData - duration*bw));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				
				break;				
		}
		
		
		
	}

	public VirtState getTaskState() {
		return taskState;
	}

	public void setTaskState(VirtState taskState) {
		this.taskState = taskState;
	}

	private void finishTask() {
		// TODO Auto-generated method stub
		setRemainingTime(0);
		taskState.setState(VirtStateEnum.FINISHED);
		Datacenter datacenter = (Datacenter) CloudVirt.entityHolder.getEntityByID(datacenterId);
		datacenter.notifyTaskFinished(this, getUserId());	
	}

	private double calculateRemainingTime(long mips, long memory, double bw,
			double diskLatency) {
		// TODO Auto-generated method stub
		double time = 0;
		for(Tasklet tasklet : taskletList){
			time += tasklet.calculateRemainingTime(mips, memory, bw, diskLatency);
		}
		return time;
	}

	/**
	 * @param remainingTime the remainingTime to set
	 */
	public void setRemainingTime(double remainingTime) {
		this.remainingTime = remainingTime;
	}

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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public double getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

}
