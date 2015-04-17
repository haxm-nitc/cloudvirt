package haxm.policies;
import haxm.policies.MemoryProvisioningPolicy;

public class MemoryProvisioningPolicySimple extends MemoryProvisioningPolicy
{

     MemoryProvisioningPolicySimple(long Memory)
     {
     	setMemory(Memory);
     }

	 public void allocateMemorytoVMs(long Memory) //Memory of host
	 {
	 	long Memorypervm=Memory/getVmList().size();
        for(VM vm: getVmList())
            vm.setAllocatedMemory(Memorypervm);
	 }
}
