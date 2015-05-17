package haxm.policies;

import java.util.List;

import haxm.components.VM;

/**
 * this class models the vm scheduler policy
 *
 */
public abstract class VMSchedulerPolicy {

	/**
	 * list of vm's
	 */
	protected List<VM> vmList;
	/**
	 * available mips.
	 */
	protected  double availableMips;
	
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
	 * @return the availableMips
	 */
	public  double getAvailableMips() {
		return availableMips;
	}
	/**
	 * @param availableMips the availableMips to set
	 */
	public void setAvailableMips(double availableMips) {
		this.availableMips = availableMips;
	}
	
	/**
	 * @param vm vm specified
	 * @return allocated mips.
	 */
	public abstract  double getAllocatedMips(VM vm);
	
	/**
	 * @param vm vm specified
	 * @param mips mips required
	 * @return boolean if allocation of mips is possible or not
	 */
	public abstract boolean canAllocateMips(VM vm,  double mips);
	
	/**
	 * @param vm vm specified
	 * @param mips mips required
	 * allocates mips.
	 */
	public abstract void allocateMips(VM vm,  double mips);
	
	/**
	 * @param vm vm specified
	 * deallocated mips from vm.
	 */
	public abstract void deallocateMips(VM vm);

}
