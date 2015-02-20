package haxm.components;

import haxm.policies.TaskSchedulerPolicy;

public class VM {
	private int vmId;
	private int datacenterId;
	private int userId;
	private Host host;
	private long requestedMemory;
	private double requestedBW;
	private double allocatedBW;
	private double allocatedMemory;
	private TaskSchedulerPolicy taskSchedulerPolicy;
	/**
	 * @param userId
	 * @param requestedMemory
	 * @param requestedBW
	 * @param taskSchedulerPolicy
	 */
	public VM(int userId, long requestedMemory, double requestedBW,
			TaskSchedulerPolicy taskSchedulerPolicy) {
		super();
		this.userId = userId;
		this.requestedMemory = requestedMemory;
		this.requestedBW = requestedBW;
		this.taskSchedulerPolicy = taskSchedulerPolicy;
	}
	/**
	 * @return the vmId
	 */
	public int getVmId() {
		return vmId;
	}
	/**
	 * @param vmId the vmId to set
	 */
	public void setVmId(int vmId) {
		this.vmId = vmId;
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
	public long getRequestedMemory() {
		return requestedMemory;
	}
	/**
	 * @param requestedMemory the requestedMemory to set
	 */
	public void setRequestedMemory(long requestedMemory) {
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
}
