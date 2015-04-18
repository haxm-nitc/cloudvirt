package haxm.policies;
import java.util.List;

import haxm.components.VM;

public abstract class MemoryProvisioningPolicy {
    private List<VM> vmList;
    private long availableMemory;            //memory of host

    public void addVM(VM vm){
		getVmList().add(vm);
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
	};

	public abstract long getAllocatedMemoryForVM(VM vm);
	
	public abstract boolean canAllocateMemory(VM vm, long memory);
	
	public abstract void allocateMemory(VM vm, long memory);
	
	public abstract void deallocateMemory(VM vm);
	/**
	 * @return the availableMemory
	 */
	public long getAvailableMemory() {
		return availableMemory;
	}
	/**
	 * @param availableMemory the availableMemory to set
	 */
	public void setAvailableMemory(long availableMemory) {
		this.availableMemory = availableMemory;
	}


}
