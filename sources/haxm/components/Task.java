package haxm.components;

import java.util.ArrayList;
import java.util.List;

public class Task {
	private List<Tasklet> taskletList;
	private double remainingTime;
	//private TaskExecHistory taskExecHistory;
	private int vmId;
	private int userId;
	private int datacenterId;

	public Task(){
		taskletList = new ArrayList<Tasklet>();
	}

	public Task(List<Tasklet> taskletlist){
		this.setTaskletList(taskletlist);
		
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

	public int getVmId() {
		return vmId;
	}

	public void setVmId(int vmId) {
		this.vmId = vmId;
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
		// TODO Auto-generated method stub
		if(taskletList.size() == 0){
			setRemainingTime(0);
		}
		Tasklet tasklet = taskletList.get(0);
		switch(tasklet.getTaskletType()){
			case Tasklet.CPU:
				CPUTasklet cpuTasklet = (CPUTasklet) tasklet;
				long remInstr = cpuTasklet.getRemainingInstructionLength();
				if(remInstr < duration * mips){
					taskletList.remove(0);
					updateExecution(duration - remInstr/mips, mips, memory, bw, diskLatency);
				}else{
					cpuTasklet.setRemainingInstructionLength((long) (remInstr - duration*mips));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				break;
			case Tasklet.DISKIO:
				DIOTasklet dioTasklet = (DIOTasklet) tasklet;
				long remainingDIOData = dioTasklet.getRemainingData();
				if(remainingDIOData < duration * diskLatency){
					taskletList.remove(0);
					updateExecution(duration - remainingDIOData/diskLatency, mips, memory, bw, diskLatency);
				}else{
					dioTasklet.setRemainingData((long) (remainingDIOData - duration*diskLatency));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				break;
			case Tasklet.NETWORKIO:
				NIOTasklet nioTasklet = (NIOTasklet) tasklet;
				long remainingNIOData = nioTasklet.getRemainingData();
				if(remainingNIOData < duration * diskLatency){
					taskletList.remove(0);
					updateExecution(duration - remainingNIOData/bw, mips, memory, bw, diskLatency);
				}else{
					nioTasklet.setRemainingData((long) (remainingNIOData - duration*diskLatency));
					setRemainingTime(calculateRemainingTime(mips, memory, bw, diskLatency));
				}
				
				break;
				
				
		}
		
		
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

}
