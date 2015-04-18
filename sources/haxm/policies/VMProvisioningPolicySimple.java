package haxm.policies;

import haxm.components.Datacenter;
import haxm.components.Host;
import haxm.components.VM;

public class VMProvisioningPolicySimple extends VMProvisioningPolicy{

	@Override
	public Host allocateHostToVM(VM vm, Datacenter datacenter) {
		for(Host host : getHostList()){
			if(host.createVM(vm)){
				return host;
			}
		}
		return null;
	}

}
