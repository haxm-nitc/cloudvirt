package haxm.core;

import java.util.Collection;
import java.util.Iterator;
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
	public boolean empty(){
		return eventCollection.isEmpty();
	}
	public Iterator<VirtEvent> iterator(){
		return eventCollection.iterator();
	}
	public void clear(){
		eventCollection.clear();
	}
	public VirtEvent next(){
		return eventCollection.iterator().next();
	}
}