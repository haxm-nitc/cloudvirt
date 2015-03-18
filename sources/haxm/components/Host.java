package haxm.components;

import haxm.VirtState;
import haxm.VirtStateEnum;
import haxm.policies.BWProvisioningPolicy;
import haxm.policies.MemoryProvisioningPolicy;
import haxm.policies.VMSchedulerpolicy;

import java.util.ArrayList;
import java.util.List;

public class Host {
	private VMSchedulerpolicy vmSchedulerPolicy;
	private MemoryProvisioningPolicy memorySchedulerPolicy;
	private BWProvisioningPolicy bwSchedulerPolicy;

	private List<VM> vmList;
	private Storage storage;
	private long memory;
	private double bandwidth;
	private CPU cpu;
	//5 msec per kb
	private double diskLatency;
	private int datacenterId;
	private double nextEventTime;
	
	private VirtState hostState;
	/**
	 * @param vmm
	 * @param storage
	 * @param memory
	 * @param bandwidth
	 * @param datacenter
	 */
	public Host(Storage storage, long memory, double bandwidth) {
		super();
		this.storage = storage;
		this.memory = memory;
		this.bandwidth = bandwidth;
		this.setVmList(new ArrayList<VM>());
		hostState = new VirtState(VirtStateEnum.INVALID);
	}

	public void executeVMs() {
		double minTime = Double.MAX_VALUE;
		double time;
		for(VM vm : getVmList()){
			// TODO resources as arguments
			vm.executeTasks();
			time = vm.getNextEventTime();
			if(time < minTime){
				minTime = time;
			}
		}
		this.setNextEventTime(minTime);
		
	}

	/**
	 * @return the storage
	 */
	public Storage getStorage() {
		return storage;
	}
	/**
	 * @param storage the storage to set
	 */
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	/**
	 * @return the memory
	 */
	public long getMemory() {
		return memory;
	}
	/**
	 * @param memory the memory to set
	 */
	public void setMemory(long memory) {
		this.memory = memory;
	}
	/**
	 * @return the bandwidth
	 */
	public double getBandwidth() {
		return bandwidth;
	}
	/**
	 * @param bandwidth the bandwidth to set
	 */
	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
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
	 * @return the diskLatency
	 */
	public double getDiskLatency() {
		return diskLatency;
	}
	/**
	 * @param diskLatency the diskLatency to set
	 */
	public void setDiskLatency(double diskLatency) {
		this.diskLatency = diskLatency;
	}
	/**
	 * @return the cpu
	 */
	public CPU getCpu() {
		return cpu;
	}
	/**
	 * @param cpu the cpu to set
	 */
	public void setCpu(CPU cpu) {
		this.cpu = cpu;
	}
	/**
	 * @return the vmList
	 */
	public List<VM> getVmList() {
		return vmList;
	}
	/**
	 * @param vmList the vmList to set
	 */
	public void setVmList(List<VM> vmList) {
		this.vmList = vmList;
	}
	/**
	 * @return the vmSchedulerPolicy
	 */
	public VMSchedulerpolicy getVmSchedulerPolicy() {
		return vmSchedulerPolicy;
	}
	/**
	 * @param vmSchedulerPolicy the vmSchedulerPolicy to set
	 */
	public void setVmSchedulerPolicy(VMSchedulerpolicy vmSchedulerPolicy) {
		this.vmSchedulerPolicy = vmSchedulerPolicy;
	}
	/**
	 * @return the memorySchedulerPolicy
	 */
	public MemoryProvisioningPolicy getMemorySchedulerPolicy() {
		return memorySchedulerPolicy;
	}
	/**
	 * @param memorySchedulerPolicy the memorySchedulerPolicy to set
	 */
	public void setMemorySchedulerPolicy(MemoryProvisioningPolicy memorySchedulerPolicy) {
		this.memorySchedulerPolicy = memorySchedulerPolicy;
	}
	/**
	 * @return the bwSchedulerPolicy
	 */
	public BWProvisioningPolicy getBwSchedulerPolicy() {
		return bwSchedulerPolicy;
	}
	/**
	 * @param bwSchedulerPolicy the bwSchedulerPolicy to set
	 */
	public void setBwSchedulerPolicy(BWProvisioningPolicy bwSchedulerPolicy) {
		this.bwSchedulerPolicy = bwSchedulerPolicy;
	}
	public void addVM(VM vm) {
		getVmList().add(vm);
		getVmSchedulerPolicy().addVM(vm);
	}
	
}
