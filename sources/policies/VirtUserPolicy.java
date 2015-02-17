package policies;

import haxm.components.DatacenterConfiguration;

import java.util.List;

public interface VirtUserPolicy {
	public List<Integer> selectDatacenters(List<DatacenterConfiguration> configurationList);
}
