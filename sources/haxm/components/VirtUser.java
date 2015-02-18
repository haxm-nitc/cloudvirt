package haxm.components;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import policies.VirtUserPolicy;
import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

public class VirtUser extends VirtEntity{
	
	private List<Integer> availableDatacenterIdList;
	private List<Integer> selectedDatacenterIdList;
	private List<DatacenterConfiguration> availableConfigurationsList;
	private VirtUserPolicy userPolicy;
	public VirtUser(String name, VirtUserPolicy policy){
		super(name);
		availableDatacenterIdList = new ArrayList<Integer>();
		selectedDatacenterIdList = new ArrayList<Integer>();
		availableConfigurationsList = new ArrayList<DatacenterConfiguration>();
		userPolicy = policy;
	}

	@Override
	public boolean startEntity() {
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		schedule(CloudVirt.cloudRegistry.getId(), TagEnum.SEND, TagEnum.DATACENTERS_INFO_REQUEST, 0.0);
		return false;
	}

	@Override
	public boolean shutdownEntity() {
		this.currentState.setState(VirtStateEnum.FINISHED);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " finished at " + CloudVirt.getCurrentTime());
		return false;
	}

	@Override
	public boolean processEvent(VirtEvent event) {
/*		
		String message = "[VU PE] EventID:"+event.getId()
				+"	Source:"+CloudVirt.entityHolder.getEntityNameByID(event.getSourceId())
				+",ID-"+event.getSourceId()
				+"	Destination:"+CloudVirt.entityHolder.getEntityNameByID(event.getDestinationId())
				+",ID-"+event.getDestinationId()
				+ "	Type:"+event.getType()
				+"	Tag:"+event.getTag()
				+"	Time:"+event.getTime();
		CloudVirt.writeLog(CloudVirt.eventsLog, message);
		
*/
		switch(event.getTag()){
			case DATACENTERS_INFO_RESPONSE:
				handle_DATACENTERS_INFO_RESPONSE(event);
				break;
			case DATACENTER_CONFIGURATION_RESPONSE:
				handle_DATACENTER_CONFIGURATION_RESPONSE(event);
				break;
				
		}
		return false;
	}

	private void handle_DATACENTER_CONFIGURATION_RESPONSE(VirtEvent event) {
		// TODO Auto-generated method stub
		availableConfigurationsList.add((DatacenterConfiguration) event.getData());
		if(availableConfigurationsList.size() == availableDatacenterIdList.size()){
			selectedDatacenterIdList = userPolicy.selectDatacenters(availableConfigurationsList);
		}
		
	}

	private void handle_DATACENTERS_INFO_RESPONSE(VirtEvent event) {
		availableDatacenterIdList = (List<Integer>) event.getData();
		//TO-DO send events to all dc
		for(int destinationId : availableDatacenterIdList){
			schedule(destinationId, TagEnum.SEND, TagEnum.DATACENTER_CONFIGURATION_REQUEST, 0.0);
		}
	}

}
