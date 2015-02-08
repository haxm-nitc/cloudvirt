package haxm.components;

import java.util.List;

import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.core.TagEnum;
import haxm.core.VirtEntity;
import haxm.core.VirtEvent;

public class Datacenter extends VirtEntity{
	
	private DatacenterConfiguration datacenterConfiguration;
	
	public Datacenter(String name, DatacenterConfiguration datacenterConfiguration) {
		super(name);
		this.datacenterConfiguration = datacenterConfiguration;		
	}
	
	public DatacenterConfiguration getDatacenterConfiguration() {
		return datacenterConfiguration;
	}
	
	public void setDatacenterConfiguration(DatacenterConfiguration datacenterConfiguration) {
		this.datacenterConfiguration = datacenterConfiguration;
	}

	@Override
	public boolean startEntity() {
		this.currentState.setState(VirtStateEnum.RUNNING);
		CloudVirt.writeLog(CloudVirt.entityLog, name +" ID:"+this.getId()+ " started at " + CloudVirt.getCurrentTime());
		schedule(CloudVirt.cloudRegistry.getId(), TagEnum.SEND, TagEnum.REGISTER_DATACENTER, 0.0, datacenterConfiguration);
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
		// TODO Auto-generated method stub
		return false;
	}
	

}