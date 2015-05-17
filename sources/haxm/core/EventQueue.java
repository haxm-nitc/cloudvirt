package haxm.core;

import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * models a event queue.
 *
 */
public class EventQueue {
	
	/**
	 *  collection object for events.
	 */
	private SortedSet<VirtEvent> eventCollection;

	/**
	 *  constructor.
	 */
	public EventQueue() {
		super();
		eventCollection = new TreeSet<VirtEvent>();
	}
	
	/**
	 * @param event event to be added
	 */
	public void addEvent(VirtEvent event){
		eventCollection.add(event);
	}

	/**
	 * @param event event to be removed
	 */
	public void removeEvent(VirtEvent event){
		eventCollection.remove(event);
	}
	
	/**
	 * @param events events to be removed
	 */
	public void removeAll(Collection<VirtEvent> events){
		eventCollection.removeAll(events);
	}
	
	/**
	 * @return size of eventqueue.
	 */
	public int size(){
		return eventCollection.size();
	}
	
	/**
	 * @return true/false if eventqueue is empty or not
	 */
	public boolean isEmpty(){
		return eventCollection.isEmpty();
	}
	
	/**
	 * @return iterator for traversing the queue.
	 */
	public Iterator<VirtEvent> iterator(){
		return eventCollection.iterator();
	}
	
	/**
	 * clear the queue.
	 */
	public void clear(){
		eventCollection.clear();
	}
	
	/**
	 * @return the next element from the eventqueue and remove it.
	 */
	public VirtEvent extract(){
		if(isEmpty()){
			return null;
		}else{
			VirtEvent event = eventCollection.iterator().next();
			removeEvent(event);
			return event;
		}
	}
	/**
	 * @return the next element from the eventqueue.
	 */
	public VirtEvent peek(){
		return eventCollection.iterator().next();
	}
}