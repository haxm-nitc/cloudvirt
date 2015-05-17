package haxm.policies;

import haxm.components.VM;

import java.util.HashMap;
import java.util.List;

/**
 * this class models the bw provisioning policy
 *
 */
public abstract class BWProvisioningPolicy {
	/**
	 *  list of vm's
	 */
	private List<VM> vmList;
	/**
	 *  available bw
	 */
	private double availableBw;            //bw of host
	
	/**
	 * @param vm  vm to be added
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
	 * @return allocated bw
	 */
	public abstract double getAllocatedBwForVM(VM vm);
	
	/**
	 * @param vm vm specified
	 * @param bandwidth bw specified
	 * @return boolean
	 */
	public abstract boolean canAllocateBW(VM vm, double bandwidth);
	
	/**
	 * @param vm vm specified
	 * @param bandwidth bw specified
	 */
	public abstract void allocateBW(VM vm, double bandwidth);
	
	/**
	 * @param vm vm specified
	 * deallocated bw
	 */
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
