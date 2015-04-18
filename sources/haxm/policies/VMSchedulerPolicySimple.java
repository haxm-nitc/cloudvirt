package haxm.policies;


import java.util.ArrayList;
import java.util.HashMap;

import haxm.components.VM;

public class VMSchedulerPolicySimple extends VMSchedulerPolicy{
	private int vmno;
	private HashMap<VM, Long> vmToMipsMap;
	
	public VMSchedulerPolicySimple(long mips){
		vmToMipsMap = new HashMap<VM, Long>();
		vmList = new ArrayList<VM>();
		setAvailableMips(mips);
		vmno=0;
	}

    public void setvmno(int vmno){
    	 this.vmno=vmno;
    }
    public int getvmno(){
    	return vmno;
    }

	@Override
	public long getAllocatedMips(VM vm) {
		return vmToMipsMap.get(vm);
	}

	@Override
	public boolean canAllocateMips(VM vm, long mips) {
		if(getAvailableMips() < mips){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void allocateMips(VM vm, long mips) {
		vmToMipsMap.put(vm, mips);
		setAvailableMips(getAvailableMips() - mips);
	}

	@Override
	public void deallocateMips(VM vm) {
		vmToMipsMap.remove(vm);
	}
}
