package haxm.components;

import java.util.ArrayList;
import java.util.List;

public class Task {
	private List<Tasklet> taskletList;
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

	public void updateExecution(double duration) {
		// TODO Auto-generated method stub
		
	}

	public double getRemainingTime() {
		// TODO Auto-generated method stub
		return 0;
	}

}
