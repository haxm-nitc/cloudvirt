package haxm.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class EventQueue {
	
	private SortedSet<VirtEvent> eventCollection;

	public EventQueue() {
		super();
		eventCollection = new TreeSet<VirtEvent>();
	}
	
	public void addEvent(VirtEvent event){
		eventCollection.add(event);
	}

	public void removeEvent(VirtEvent event){
		eventCollection.remove(event);
	}
	
	public void removeAll(Collection<VirtEvent> events){
		eventCollection.removeAll(events);
	}
	
	public int size(){
		return eventCollection.size();
	}
	
	public boolean isEmpty(){
		return eventCollection.isEmpty();
	}
	
	public Iterator<VirtEvent> iterator(){
		return eventCollection.iterator();
	}
	
	public void clear(){
		eventCollection.clear();
	}
	
	public VirtEvent extract(){
		if(isEmpty()){
			return null;
		}else{
			VirtEvent event = eventCollection.iterator().next();
			removeEvent(event);
			return event;
		}
	}
}