package haxm.components;

import haxm.VirtStateEnum;
import haxm.components.Datacenter;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the cloudregistry class where datacenters can be registered.
 *
 */
/**
 * @author Xavier Jose
 *
 */
public class CloudRegistry extends VirtEntity{
	
	//private List<Datacenter> datacenterList;
	/**
	 *  list of datacenter id's
	 */
	private List<Integer> datacenterIdList;
	
	/**
	 * @return the datacenterIdList
	 */
	public List<Integer> getDatacenterIdList() {
		return datacenterIdList;
	}

	/**
	 * @param datacenterIdList the datacenterIdList to set
	 */
	public void setDatacenterIdList(List<Integer> datacenterIdList) {
		this.datacenterIdList = datacenterIdList;
	}

	/**
	 * @param name name of the registry
	 */
	public CloudRegistry(String name) {
		super(name);
		datacenterIdList = new ArrayList<Integer>();
	}

	

	/* (non-Javadoc)
	 * @see haxm.core.VirtEntity#startEntity()
	 */
	public boolean startEntity(){
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		return true;
	}

	
	/* (non-Javadoc)
	 * @see haxm.core.VirtEntity#shutdownEntity()
	 */
	public boolean shutdownEntity(){
		this.currentState.setState(VirtStateEnum.FINISHED);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " finished at " + CloudVirt.getCurrentTime());
		return true;
	}
	
	/* (non-Javadoc)
	 * @see haxm.core.VirtEntity#processEvent(haxm.core.VirtEvent)
	 */
	public boolean processEvent(VirtEvent event){
/*		
		String message = "[CR PE] EventID:"+event.getId()
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
			case REGISTER_DATACENTER:
				handle_REGISTER_DATACENTER(event);
				break;
			case DATACENTERS_INFO_REQUEST:
				handle_DATACENTERS_INFO_REQUEST(event);
	
				break;
		}
		return true;
	}

	/**
	 * @param event contains target destination to send the datacenter_info_response
	 */
	private void handle_DATACENTERS_INFO_REQUEST(VirtEvent event) {
		scheduleNow(event.getSourceId(), TagEnum.DATACENTERS_INFO_RESPONSE, datacenterIdList);
		
	}

	/**
	 * @param event EVENT object containing the datacenter id to be registered.
	 */
	private void handle_REGISTER_DATACENTER(VirtEvent event) {
		datacenterIdList.add((Integer) event.getData());
	}
}