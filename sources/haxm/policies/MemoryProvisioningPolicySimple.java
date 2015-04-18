package haxm.policies;
import java.util.HashMap;

import haxm.components.VM;
import haxm.policies.MemoryProvisioningPolicy;

public class MemoryProvisioningPolicySimple extends MemoryProvisioningPolicy{

	private HashMap<VM, Long> vmToMemoryMap;
     public MemoryProvisioningPolicySimple(long Memory){
     	setAvailableMemory(Memory);
     	vmToMemoryMap = new HashMap<VM, Long>();
     }


	@Override
	public long getAllocatedMemoryForVM(VM vm) {
		return vmToMemoryMap.get(vm);
	}

	@Override
	public boolean canAllocateMemory(VM vm, long memory) {
		if(getAvailableMemory() < memory){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public void allocateMemory(VM vm, long memory) {
		vmToMemoryMap.put(vm, memory);
		
	}

	@Override
	public void deallocateMemory(VM vm) {
		vmToMemoryMap.remove(vm);
		
	}
}
