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

/**
 * This is the class that holds the datacenters,users and the cloudregistry object.
 *
 */
public class EntityHolder {
	/**
	 *  	registry object
	 */
	CloudRegistry registry;
	/**
	 *      list of all datacenters
	 */
	List<Datacenter> datacenterList;
	/**
	 *      list of all users
	 */
	List<VirtUser> userList;
	/**
	 *      map entity id to itself
	 */
	HashMap<Integer, VirtEntity> entityMap;
	
	/**
	 *  constructor
	 */
	public EntityHolder(){
		datacenterList = new ArrayList<Datacenter>();
		userList = new ArrayList<VirtUser>();
		entityMap = new HashMap<Integer, VirtEntity>();
	}
	
	/**
	 * @param entity the entity to be added to the holder
	 */
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
	
	/**
	 * @param id is the id of the virtentity object we want to get
	 * @return the entity for which the id matches
	 */
	public VirtEntity getEntityByID(int id){
		return entityMap.get(id);
	}

	/**
	 * @param id id of the entity
	 * @return name of the name of the entity object
	 */
	public String getEntityNameByID(int id){
		return entityMap.get(id).getName();
	}

	/**
	 * to start all the entities in the holder class  
	 */
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
	/**
	 *  calls the run method of all entity objects in the holder
	 */
	public void runAll() {
		registry.run();
		for(Datacenter datacenter : getDatacenterList() ){
			datacenter.run();
		}
		for(VirtUser user : getUserList()){
			user.run();
		}

	}

	/**
	 * shuts down all the entities in the holder class
	 */
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
