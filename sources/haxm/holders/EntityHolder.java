package haxm.holders;

import haxm.VirtStateEnum;
import haxm.components.CloudRegistry;
import haxm.components.Datacenter;
import haxm.components.VirtUser;
import haxm.core.CloudVirt;
import haxm.core.VirtEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityHolder {
	CloudRegistry registry;
	List<Datacenter> datacenterList;
	List<VirtUser> userList;
	HashMap<Integer, VirtEntity> entityMap;
	
	public EntityHolder(){
		datacenterList = new ArrayList<Datacenter>();
		userList = new ArrayList<VirtUser>();
		entityMap = new HashMap<Integer, VirtEntity>();
	}
	
	public void addEntity(VirtEntity entity){
		if(entity instanceof Datacenter){
			datacenterList.add((Datacenter) entity);
		}
		if(entity instanceof VirtUser){
			userList.add((VirtUser) entity);
		}
		if(entity instanceof CloudRegistry){
			registry = (CloudRegistry) entity;
		}

		entityMap.put(entity.getId(), entity);
	}
	
	public VirtEntity getEntityByID(int id){
		return entityMap.get(id);
	}

	public String getEntityNameByID(int id){
		return entityMap.get(id).getName();
	}

	public void startEntities() {
		registry.startEntity();
		for(Datacenter datacenter : getDatacenterList() ){
			datacenter.startEntity();
		}
		for(VirtUser user : getUserList()){
			user.startEntity();
		}
/*
		for(VirtEntity entity:entityList){
			entity.startEntity();
		}
*/			
	}
	public void runAll() {
		registry.run();
		for(Datacenter datacenter : getDatacenterList() ){
			datacenter.run();
		}
		for(VirtUser user : getUserList()){
			user.run();
		}

	}

	public void shutdownEntities() {
		for(VirtUser user : getUserList()){
			user.shutdownEntity();
		}
		for(Datacenter datacenter : getDatacenterList() ){
			datacenter.shutdownEntity();
		}
		registry.shutdownEntity();
	}

	/**
	 * @return the registry
	 */
	public CloudRegistry getRegistry() {
		return registry;
	}

	/**
	 * @param registry the registry to set
	 */
	public void setRegistry(CloudRegistry registry) {
		this.registry = registry;
	}

	/**
	 * @return the datacenterList
	 */
	public List<Datacenter> getDatacenterList() {
		return datacenterList;
	}

	/**
	 * @param datacenterList the datacenterList to set
	 */
	public void setDatacenterList(List<Datacenter> datacenterList) {
		this.datacenterList = datacenterList;
	}

	/**
	 * @return the userList
	 */
	public List<VirtUser> getUserList() {
		return userList;
	}

	/**
	 * @param userList the userList to set
	 */
	public void setUserList(List<VirtUser> userList) {
		this.userList = userList;
	}

	/**
	 * @return the entityMap
	 */
	public HashMap<Integer, VirtEntity> getEntityMap() {
		return entityMap;
	}

	/**
	 * @param entityMap the entityMap to set
	 */
	public void setEntityMap(HashMap<Integer, VirtEntity> entityMap) {
		this.entityMap = entityMap;
	}

}
