package haxm.policies;
import java.util.List;

import haxm.components.VM;

public abstract class MemoryProvisioningPolicy {
    private List<VM> vmList;
    private  double availableMemory;            //memory of host

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

	public abstract  double getAllocatedMemoryForVM(VM vm);
	
	public abstract boolean canAllocateMemory(VM vm,  double memory);
	
	public abstract void allocateMemory(VM vm,  double memory);
	
	public abstract void deallocateMemory(VM vm);
	/**
	 * @return the availableMemory
	 */
	public  double getAvailableMemory() {
		return availableMemory;
	}
	/**
	 * @param availableMemory the availableMemory to set
	 */
	public void setAvailableMemory(double availableMemory) {
		this.availableMemory = availableMemory;
	}


}
