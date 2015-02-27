package haxm.policies;

import haxm.components.Datacenter;
import haxm.components.VM;

public class VMProvisioningPolicySimple extends VMProvisioningPolicy{

	@Override
	public boolean allocateHostToVM(VM vm, Datacenter datacenter) {
		
		return true;
	}

}
