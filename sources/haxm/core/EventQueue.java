package haxm.core;

import java.util.Collection;
import java.util.SortedSet;
import java.util.TreeSet;

public class EventQueue {
	private SortedSet<VirtEvent> eventCollection = new TreeSet<VirtEvent>();
	
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
	public void clear(){
		eventCollection.clear();
	}
}