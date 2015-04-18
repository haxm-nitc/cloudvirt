package haxm.policies;

import java.util.List;

import haxm.components.CPU;
import haxm.components.VM;

public abstract class VMSchedulerPolicy {

	private List<VM> vmList;
	private CPU cpu;
	
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
	
	public abstract long getAllocatedMipsForVM(VM vm);
	

}
