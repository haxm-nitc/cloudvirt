package haxm.policies;
import java.util.HashMap;

import haxm.components.VM;
import haxm.policies.BWProvisioningPolicy;

public class BWProvisioningPolicySimple extends BWProvisioningPolicy{
	HashMap<Integer, Double> vmToBWMap;
	
	public BWProvisioningPolicySimple(double bw){
		setAvailableBw(bw);
		vmToBWMap = new HashMap<Integer, Double>();
	}
	

	@Override
	public double getAllocatedBwForVM(VM vm) {
		return vmToBWMap.get(vm.getId());
	}

	@Override
	public boolean canAllocateBW(VM vm, double bw) {
		if(getAvailableBw() < bw){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void allocateBW(VM vm, double bw) {
		vmToBWMap.put(vm.getId(), bw);
	}

	@Override
	public void deallocateBW(VM vm) {
		vmToBWMap.remove(vm.getId());
	}
}
