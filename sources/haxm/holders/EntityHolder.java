package haxm.holders;

import haxm.VirtStateEnum;
import haxm.core.VirtEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EntityHolder {
	
	List<VirtEntity> entityList;
	HashMap<Integer, VirtEntity> entityMap;
	
	public EntityHolder(){
		entityList = new ArrayList<VirtEntity>();
		entityMap = new HashMap<Integer, VirtEntity>();
	}
	
	public void addEntity(VirtEntity entity){
		entityList.add(entity);
		entityMap.put(entity.getId(), entity);
	}
	
	public VirtEntity getEntityByID(int id){
		return entityMap.get(id);
	}

	public String getEntityNameByID(int id){
		return entityMap.get(id).getName();
	}

	public void startEntities() {
		for(VirtEntity entity:entityList){
			entity.startEntity();
		}	
	}

	public void runAll() {
		for(VirtEntity entity: entityList){
			if(entity.getCurrentState() == VirtStateEnum.RUNNING){
				entity.run();
			}
		}	
	}

	public void shutdownEntities() {
		for(VirtEntity entity:entityList){
			entity.shutdownEntity();
		}	
	}
}
