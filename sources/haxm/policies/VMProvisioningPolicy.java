package haxm.policies;

import java.util.List;

import haxm.components.Datacenter;
import haxm.components.Host;
import haxm.components.VM;

public abstract class VMProvisioningPolicy {
	private List<Host> hostList;
	public abstract boolean allocateHostToVM(VM vm, Datacenter datacenter);
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
