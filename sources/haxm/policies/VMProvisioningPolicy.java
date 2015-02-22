package haxm.policies;

import haxm.components.Datacenter;
import haxm.components.VM;

public interface VMProvisioningPolicy {
	public boolean allocateHostToVM(VM vm, Datacenter datacenter);

}
