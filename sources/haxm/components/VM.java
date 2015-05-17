package haxm.components;

import java.util.ArrayList;
import java.util.List;

import haxm.VirtState;
import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.policies.TaskSchedulerPolicy;

/**
 * this class models the VM object.
 *
 */
public class VM {
	/**
	 *  total number of VM's.
	 */
	private static int numVms = 0; 
	/**
	 *  id of VM
	 */
	private int id;
	/**
	 *  id of datacenter of host which contains the vm.
	 */
	private int datacenterId;
	/**
	 *  id of user who created vm.
	 */
	private int userId;
	/**
	 *  host object that contains the vm.
	 */
	private Host host;
	/**
	 *  list of tasks.
	 */
	private List<Task> taskList;
	/**
	 *  requested mips by vm.
	 */
	private  double requestedMips;
	/**
	 *  allocated mips for vm.
	 */ 
	private  double allocatedMips;
	/**
	 * requested memory by vm.
	 */
	private  double requestedMemory;
	/**
	 *  requested bw by vm.
	 */
	private double requestedBW;
	/**
	 * allocated bw to vm.
	 */
	private double allocatedBW;
	/**
	 *  allocated memory to vm.
	 */
	private double allocatedMemory;
	/**
	 * policy for task scheduling.
	 */
	private TaskSchedulerPolicy taskSchedulerPolicy;
	/**
	 *  next event time.
	 */
	private double nextEventTime;
	/**
	 *  finish time of VM.
	 */
	private double finishTime;
	
	/**
	 *  current state of vm.
	 */
	private VirtState vmState;
	/**
	 * @param requestedCores
	 * @param requestedMipsPerCore
	 * @param requestedMemory
	 * @param requestedBW
	 * @param taskSchedulerPolicy
	 * constructor
	 */
	public VM(double requestedMips,
				double requestedMemory, double requestedBW,
			TaskSchedulerPolicy taskSchedulerPolicy) {
		super();
		this.id = ++numVms;
		this.requestedMips = requestedMips;
		this.requestedMemory = requestedMemory;
		this.requestedBW = requestedBW;
		this.taskSchedulerPolicy = taskSchedulerPolicy;
		this.taskSchedulerPolicy.setVm(this);
		taskList = new ArrayList<Task>();
		vmState = new VirtState(VirtStateEnum.INVALID);
	}
	/**
	 * @param mips mips of vm
	 * @param memory memory of vm
	 * @param bw bw of vm
	 * @param diskLatency disklatency 
	 * execute tasks inside vm.
	 */
	public void executeTasks(double mips,  double memory, double bw, double diskLatency) {
		if(vmState.getState() != VirtStateEnum.RUNNING){
			vmState.setState(VirtStateEnum.RUNNING);
			//getTaskSchedulerPolicy().submitTasks(taskList);
		}
		CloudVirt.vmsLog.append("[exT mips:"+mips+" memory:"+memory+" bw:"+bw+" disklatency:"+diskLatency+" vmid:"+getId());
		setNextEventTime(getTaskSchedulerPolicy().runTasks(mips, memory, bw, diskLatency));		
	}
	/**
	 * @param task task to be added
	 */
	public void addTask(Task task) {
		
		this.getTaskList().add(task);
		this.getTaskSchedulerPolicy().addTask(task);

		
	}
	/**
	 * @return the vm Id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the vm Id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the datacenterId
	 */
	public int getDatacenterId() {
		return datacenterId;
	}
	/**
	 * @param datacenterId the datacenterId to set
	 */
	public void setDatacenterId(int datacenterId) {
		this.datacenterId = datacenterId;
	}
	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(int userId) {
		this.userId = userId;
	}
	/**
	 * @return the host
	 */
	public Host getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(Host host) {
		this.host = host;
	}
	/**
	 * @return the requestedMemory
	 */
	public  double getRequestedMemory() {
		return requestedMemory;
	}
	/**
	 * @param requestedMemory the requestedMemory to set
	 */
	public void setRequestedMemory(double requestedMemory) {
		this.requestedMemory = requestedMemory;
	}
	/**
	 * @return the requestedBW
	 */
	public double getRequestedBW() {
		return requestedBW;
	}
	/**
	 * @param requestedBW the requestedBW to set
	 */
	public void setRequestedBW(double requestedBW) {
		this.requestedBW = requestedBW;
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
	 * @return the taskSchedulerPolicy
	 */
	public TaskSchedulerPolicy getTaskSchedulerPolicy() {
		return taskSchedulerPolicy;
	}
	/**
	 * @param taskSchedulerPolicy the taskSchedulerPolicy to set
	 */
	public void setTaskSchedulerPolicy(TaskSchedulerPolicy taskSchedulerPolicy) {
		this.taskSchedulerPolicy = taskSchedulerPolicy;
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
	 * @return the nextEventTime
	 */
	public double getNextEventTime() {
		return nextEventTime;
	}
	/**
	 * @param nextEventTime the nextEventTime to set
	 */
	public void setNextEventTime(double nextEventTime) {
		this.nextEventTime = nextEventTime;
	}
	
	/**
	 * @return the vmState
	 */
	public VirtState getVmState() {
		return vmState;
	}
	/**
	 * @param vmState the vmState to set
	 */
	public void setVmState(VirtState vmState) {
		this.vmState = vmState;
	}
	/**
	 * @return the requestedMips
	 */
	public  double getRequestedMips() {
		return requestedMips;
	}
	/**
	 * @param requestedMips the requestedMips to set
	 */
	public void setRequestedMips(double requestedMips) {
		this.requestedMips = requestedMips;
	}
	/**
	 * @return the allocatedMips
	 */
	public  double getAllocatedMips() {
		return allocatedMips;
	}
	/**
	 * @param allocatedMips the allocatedMips to set
	 */
	public void setAllocatedMips(double allocatedMips) {
		this.allocatedMips = allocatedMips;
	}
	/**
	 * @return finish time
	 */
	public double getFinishTime() {
		return finishTime;
	}
	/**
	 * @param finishTime finishtime to be set.
	 */
	public void setFinishTime(double finishTime) {
		this.finishTime = finishTime;
	}

}
