package haxm.components;

import haxm.VirtStateEnum;
import haxm.components.Datacenter;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

import java.util.ArrayList;
import java.util.List;

public class CloudRegistry extends VirtEntity{
	
	private List<Datacenter> datacenterList;
	private List<DatacenterConfiguration> datacenterConfigurationsList;
	
	public CloudRegistry(String name) {
		super(name);
		datacenterList = new ArrayList<Datacenter>();
	}

	public boolean startEntity(){
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		return true;
	}

	public boolean shutdownEntity(){
		this.currentState.setState(VirtStateEnum.FINISHED);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " finished at " + CloudVirt.getCurrentTime());
		return true;
	}
	
	public boolean processEvent(VirtEvent event){
		switch(event.getTag()){
			case REGISTER_DATACENTER:
				datacenterConfigurationsList.add((DatacenterConfiguration) event.getData());
				break;
			case DATACENTERS_INFO_REQUEST:
				schedule(event.getSourceId(), TagEnum.SEND, TagEnum.DATACENTERS_INFO_RESPONSE, 0.00, datacenterConfigurationsList);
				break;
		}
		return true;
	}
}