package haxm.policies;

import haxm.components.DatacenterConfiguration;
import haxm.components.VM;

import java.util.List;

public interface VirtUserPolicy {
	public List<Integer> selectDatacenters(List<DatacenterConfiguration> configurationList);

	public int selectDatacenterForVM(List<Integer> datacenterIdList, VM vm);
}
