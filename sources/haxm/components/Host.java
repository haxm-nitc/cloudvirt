package haxm.components;

import java.util.ArrayList;
import java.util.List;

public class Host {
	private VMM vmm;
	private List<VM> vmList;
	private Storage storage;
	private long memory;
	private double bandwidth;
	private CPU cpu;
	//5 msec per kb
	private double diskLatency;
	private int datacenterId;
	private double nextEventTime;
	/**
	 * @param vmm
	 * @param storage
	 * @param memory
	 * @param bandwidth
	 * @param datacenter
	 */
	public Host(VMM vmm, Storage storage, long memory, double bandwidth) {
		super();
		this.vmm = vmm;
		this.storage = storage;
		this.memory = memory;
		this.bandwidth = bandwidth;
		this.setVmList(new ArrayList<VM>());
	}
	/**
	 * @return the vmm
	 */
	public VMM getVmm() {
		return vmm;
	}
	/**
	 * @param vmm the vmm to set
	 */
	public void setVmm(VMM vmm) {
		this.vmm = vmm;
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
	public void executeVMs() {
		double minTime = Double.MAX_VALUE;
		double time;
		for(VM vm : getVmList()){
			vm.executeTasks();
			time = vm.getNextEventTime();
			if(time < minTime){
				minTime = time;
			}
		}
		this.setNextEventTime(minTime);
		
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
	
}
