package haxm.policies;

import haxm.components.DatacenterConfiguration;
import haxm.components.VM;

import java.util.List;

/**
 * this class models the virtuser policy
 *
 */
public interface VirtUserPolicy {
	/**
	 * @param configurationList configuration of datacenters
	 * @return selected datacenter id's
	 * selects datacenters based on policy
	 */
	public List<Integer> selectDatacenters(List<DatacenterConfiguration> configurationList);

	/**
	 * @param datacenterIdList
	 * @param vm
	 * @return datacenter for vm
	 */
	public int selectDatacenterForVM(List<Integer> datacenterIdList, VM vm);
}
