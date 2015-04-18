package haxm.policies;

import java.util.List;

import haxm.components.CPU;
import haxm.components.VM;

public abstract class VMSchedulerPolicy {

	protected List<VM> vmList;
	protected long availableMips;
	
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
	public long getAvailableMips() {
		return availableMips;
	}
	/**
	 * @param availableMips the availableMips to set
	 */
	public void setAvailableMips(long availableMips) {
		this.availableMips = availableMips;
	}
	
	public abstract long getAllocatedMips(VM vm);
	
	public abstract boolean canAllocateMips(VM vm, long mips);
	
	public abstract void allocateMips(VM vm, long mips);
	
	public abstract void deallocateMips(VM vm);

}
