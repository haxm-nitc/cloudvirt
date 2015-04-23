package haxm.policies;


import java.util.ArrayList;
import java.util.HashMap;

import haxm.components.VM;

public class VMSchedulerPolicySimple extends VMSchedulerPolicy{
	private int vmno;
	private HashMap<VM,  Double> vmToMipsMap;
	
	public VMSchedulerPolicySimple(double mips){
		vmToMipsMap = new HashMap<VM,  Double>();
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
	public  double getAllocatedMips(VM vm) {
		return vmToMipsMap.get(vm);
	}

	@Override
	public boolean canAllocateMips(VM vm,  double mips) {
		if(getAvailableMips() < mips){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void allocateMips(VM vm,  double mips) {
		vmToMipsMap.put(vm, mips);
		setAvailableMips(getAvailableMips() - mips);
	}

	@Override
	public void deallocateMips(VM vm) {
		vmToMipsMap.remove(vm);
		setAvailableMips(getAvailableMips() + vm.getAllocatedMips());
	}
}
