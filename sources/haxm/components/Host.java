package haxm.components;

import haxm.VirtState;
import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.policies.BWProvisioningPolicy;
import haxm.policies.BWProvisioningPolicySimple;
import haxm.policies.MemoryProvisioningPolicy;
import haxm.policies.MemoryProvisioningPolicySimple;
import haxm.policies.VMSchedulerPolicy;
import haxm.policies.VMSchedulerPolicySimple;

import java.util.ArrayList;
import java.util.List;

public class Host {
	private VMSchedulerPolicy vmSchedulerPolicy;
	private MemoryProvisioningPolicy memoryProvisioningPolicy;
	private BWProvisioningPolicy bwProvisioningPolicy;

	private List<VM> vmList;
	private Storage storage;
	private long memory;
	private double bandwidth;
	private long mips;
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
	public Host(Storage storage, long mips, long memory, double bandwidth,double disklatency) {
		super();
		this.diskLatency=disklatency;
		this.storage = storage;
		this.memory = memory;
		this.bandwidth = bandwidth;
		this.mips = mips;
		this.setVmList(new ArrayList<VM>());
		hostState = new VirtState(VirtStateEnum.INVALID);
		vmSchedulerPolicy = new VMSchedulerPolicySimple(mips);
		bwProvisioningPolicy = new BWProvisioningPolicySimple(bandwidth);
		memoryProvisioningPolicy = new MemoryProvisioningPolicySimple(memory);
	}	

	public void executeVMs() {
		double minTime = Double.MAX_VALUE;
		double time;
		for(VM vm : getVmList()){
			// TODO resources as arguments
			vm.executeTasks(vmSchedulerPolicy.getAllocatedMips(vm), 
					memoryProvisioningPolicy.getAllocatedMemoryForVM(vm), 
					bwProvisioningPolicy.getAllocatedBwForVM(vm), diskLatency/vmList.size());
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
	public VMSchedulerPolicy getVmSchedulerPolicy() {
		return vmSchedulerPolicy;
	}
	/**
	 * @param vmSchedulerPolicy the vmSchedulerPolicy to set
	 */
	public void setVmSchedulerPolicy(VMSchedulerPolicy vmSchedulerPolicy) {
		this.vmSchedulerPolicy = vmSchedulerPolicy;
	}
	/**
	 * @return the memoryProvisioningPolicy
	 */
	public MemoryProvisioningPolicy getMemoryProvisioningPolicy() {
		return memoryProvisioningPolicy;
	}
	/**
	 * @param memoryProvisioningPolicy the memoryProvisioningPolicy to set
	 */
	public void setMemoryProvisioningPolicy(MemoryProvisioningPolicy memoryProvisioningPolicy) {
		this.memoryProvisioningPolicy = memoryProvisioningPolicy;
	}
	/**
	 * @return the bwProvisioningPolicy
	 */
	public BWProvisioningPolicy getBwProvisioningPolicy() {
		return bwProvisioningPolicy;
	}
	/**
	 * @param bwProvisioningPolicy the bwProvisioningPolicy to set
	 */
	public void setBwProvisioningPolicy(BWProvisioningPolicy bwProvisioningPolicy) {
		this.bwProvisioningPolicy = bwProvisioningPolicy;
	}
	public void addVM(VM vm) {
		getVmList().add(vm);
		getVmSchedulerPolicy().addVM(vm);
	//	getBwProvisioningPolicy().addVM(vm);
	//	getMemoryProvisioningPolicy().addVM(vm);
	}

	public boolean createVM(VM vm) {
		boolean result = false;
		
		if(!bwProvisioningPolicy.canAllocateBW(vm, vm.getRequestedBW())){
			return result;
		}
		if(!memoryProvisioningPolicy.canAllocateMemory(vm, vm.getRequestedMemory())){
			return result;
		}
		if(!vmSchedulerPolicy.canAllocateMips(vm, vm.getRequestedMips())){
			return result;
		}
		
		result = true;
		bwProvisioningPolicy.allocateBW(vm, vm.getRequestedBW());
		memoryProvisioningPolicy.allocateMemory(vm, vm.getRequestedMemory());
		vmSchedulerPolicy.allocateMips(vm, vm.getRequestedMips());
		
		return result;
	}

	/**
	 * @return the mips
	 */
	public long getMips() {
		return mips;
	}

	/**
	 * @param mips the mips to set
	 */
	public void setMips(long mips) {
		this.mips = mips;
	}
	
}
