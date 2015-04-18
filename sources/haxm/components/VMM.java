package haxm.components;

import haxm.policies.BWProvisioningPolicy;
import haxm.policies.MemoryProvisioningPolicy;
import haxm.policies.VMSchedulerPolicy;

public class VMM {
	String name;
	private VMSchedulerPolicy vmSchedulerPolicy;
	private MemoryProvisioningPolicy memorySchedulerPolicy;
	private BWProvisioningPolicy bwSchedulerPolicy;
	
	public VMM(String name){
		this.name = name;
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
	 * @return the memorySchedulerPolicy
	 */
	public MemoryProvisioningPolicy getMemorySchedulerPolicy() {
		return memorySchedulerPolicy;
	}

	/**
	 * @param memorySchedulerPolicy the memorySchedulerPolicy to set
	 */
	public void setMemorySchedulerPolicy(MemoryProvisioningPolicy memorySchedulerPolicy) {
		this.memorySchedulerPolicy = memorySchedulerPolicy;
	}

	/**
	 * @return the bwSchedulerPolicy
	 */
	public BWProvisioningPolicy getBwSchedulerPolicy() {
		return bwSchedulerPolicy;
	}

	/**
	 * @param bwSchedulerPolicy the bwSchedulerPolicy to set
	 */
	public void setBwSchedulerPolicy(BWProvisioningPolicy bwSchedulerPolicy) {
		this.bwSchedulerPolicy = bwSchedulerPolicy;
	}
}
