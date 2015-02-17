package policies;

import haxm.components.DatacenterConfiguration;

import java.util.ArrayList;
import java.util.List;

public class VirtUserPolicySimple implements VirtUserPolicy{

	@Override
	public List<Integer> selectDatacenters(
			List<DatacenterConfiguration> configurationList) {
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(configurationList.get(0).getDatacenterId());
		return idList;
	}
	
}
