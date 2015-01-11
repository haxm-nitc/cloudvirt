public class Entity{
	/**A unique Entity Identifier*/
	private int id;
	/**/
	private String name;
	
	/**Current State of the entity*/
	private State currentState;

	/**Event queue of the entity. Events to be processed are kept in the buffered queue*/
	private BufferQueue bufferQueue;

	
}
