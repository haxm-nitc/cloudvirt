package haxm.components;

import haxm.policies.BWSchedulerPolicy;
import haxm.policies.MemorySchedulerPolicy;
import haxm.policies.VMSchedulerpolicy;

public class VMM {
	String name;
	private VMSchedulerpolicy vmSchedulerPolicy;
	private MemorySchedulerPolicy memorySchedulerPolicy;
	private BWSchedulerPolicy bwSchedulerPolicy;
	
	public VMM(String name){
		this.name = name;
	}
}
