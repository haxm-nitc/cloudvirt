package haxm;

/**
 * this class models the state of a entity
 *
 */
public class VirtState{
	
	/**
	 *  current state
	 */
	private VirtStateEnum currentStateEnum;
	
	/**
	 * @param currentStateEnum to set currentstate
	 */
	public VirtState(VirtStateEnum currentStateEnum) {
		super();
		this.currentStateEnum = currentStateEnum;
	}
	
	/**
	 * @param newStateEnum to set state
	 */
	public void setState(VirtStateEnum newStateEnum){
		this.currentStateEnum = newStateEnum;
	}
	/**
	 * @return state
	 */
	public VirtStateEnum getState(){
		return currentStateEnum;
	}

}
