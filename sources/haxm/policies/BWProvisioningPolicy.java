package haxm.policies;

import haxm.components.VM;

import java.util.HashMap;
import java.util.List;

public abstract class BWProvisioningPolicy {
	private List<VM> vmList;
	private double availableBw;            //bw of host
	
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
	
	public abstract double getAllocatedBwForVM(VM vm);
	
	public abstract boolean canAllocateBW(VM vm, double bandwidth);
	
	public abstract void allocateBW(VM vm, double bandwidth);
	
	public abstract void deallocateBW(VM vm);
	/**
	 * @return the availableBw
	 */
	public double getAvailableBw() {
		return availableBw;
	}
	/**
	 * @param availableBw the availableBw to set
	 */
	public void setAvailableBw(double availableBw) {
		this.availableBw = availableBw;
	}
}
