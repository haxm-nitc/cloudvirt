package haxm.policies;
import java.util.List;

import haxm.components.VM;

/**
 * this class models the memory provisioning policy
 *
 */
public abstract class MemoryProvisioningPolicy {
    /**
     *  list of vm
     */
    private List<VM> vmList;
    /**
     * available memory
     */
    private  double availableMemory;            //memory of host

    /**
     * @param vm vm to be added
     */
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

	/**
	 * @param vm vm specified
	 * @return allocated memory for vm.
	 */
	public abstract  double getAllocatedMemoryForVM(VM vm);
	
	/**
	 * @param vm vm specified
	 * @param memory memory specified
	 * @return boolean
	 */
	public abstract boolean canAllocateMemory(VM vm,  double memory);
	
	/**
	 * @param vm vm specified
	 * @param memory memory specified
	 */
	public abstract void allocateMemory(VM vm,  double memory);
	
	/**
	 * @param vm vm specified
	 * deallocates memory
	 */
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
