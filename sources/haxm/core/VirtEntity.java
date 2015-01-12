package haxm.core;

import haxm.VirtState;

public abstract class VirtEntity{
	/**A unique Entity Identifier*/
	private int id;
	/**/
	private String name;
	
	/**Current State of the entity*/
	private VirtState currentState;

	/**Event queue of the entity. Events to be processed are kept in the buffered queue*/
	private BufferQueue bufferQueue;
	
	
	protected abstract boolean startEntity();
	protected abstract boolean stopEntity();
	protected abstract boolean processEvent(VirtEvent event);
	

	
}
