package haxm.policies;
import java.util.HashMap;

import haxm.components.VM;
import haxm.policies.MemoryProvisioningPolicy;

public class MemoryProvisioningPolicySimple extends MemoryProvisioningPolicy{

	private HashMap<Integer,  Double> vmToMemoryMap;
     public MemoryProvisioningPolicySimple(double Memory){
     	setAvailableMemory(Memory);
     	vmToMemoryMap = new HashMap<Integer,  Double>();
     }


	@Override
	public  double getAllocatedMemoryForVM(VM vm) {
		return vmToMemoryMap.get(vm.getId());
	}

	@Override
	public boolean canAllocateMemory(VM vm,  double memory) {
		if(getAvailableMemory() < memory){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void allocateMemory(VM vm,  double memory) {
//		setAvailableMemory(getAvailableMemory() - memory);
		vmToMemoryMap.put(vm.getId(), memory);
		
	}

	@Override
	public void deallocateMemory(VM vm) {
		vmToMemoryMap.remove(vm.getId());
		
	}
}
