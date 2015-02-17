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
	
	//private List<Datacenter> datacenterList;
	private List<Integer> datacenterIdList;
	
	public CloudRegistry(String name) {
		super(name);
		datacenterIdList = new ArrayList<Integer>();
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
				handle_REGISTER_DATACENTER(event);
				break;
			case DATACENTERS_INFO_REQUEST:
				handle_DATACENTERS_INFO_REQUEST(event);
	
				break;
		}
		return true;
	}

	private void handle_DATACENTERS_INFO_REQUEST(VirtEvent event) {
		schedule(event.getSourceId(), TagEnum.SEND, TagEnum.DATACENTERS_INFO_RESPONSE, 0.00, datacenterIdList);
		
	}

	private void handle_REGISTER_DATACENTER(VirtEvent event) {
		datacenterIdList.add((Integer) event.getData());
	}
}