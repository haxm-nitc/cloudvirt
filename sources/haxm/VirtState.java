package haxm;

public class VirtState{
	
	private VirtStateEnum currentStateEnum;
	
	public VirtState(VirtStateEnum currentStateEnum) {
		super();
		this.currentStateEnum = currentStateEnum;
	}
	
	public void setState(VirtStateEnum newStateEnum){
		this.currentStateEnum = newStateEnum;
	}
	public VirtStateEnum getState(){
		return currentStateEnum;
	}

}
