package haxm.policies;

import java.util.List;

import haxm.components.Datacenter;
import haxm.components.Host;
import haxm.components.VM;

/**
 * this class models the vm provisioning policy
 *
 */
public abstract class VMProvisioningPolicy {
	/**
	 * list of hosts
	 */
	private List<Host> hostList;
	
	/**
	 * @param vm vm specified
	 * @param datacenter datacenter that contains vm
	 * @return the host that contains the vm
	 */
	public abstract Host allocateHostToVM(VM vm, Datacenter datacenter);
	/**
	 * @return the hostList
	 */
	public List<Host> getHostList() {
		return hostList;
	}
	/**
	 * @param hostList the hostList to set
	 */
	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}

}
