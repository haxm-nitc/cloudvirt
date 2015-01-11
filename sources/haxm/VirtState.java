package haxm;

public class VirtState{
	private VirtStateEnum currentState;
	
	public void setState(VirtStateEnum newState){
		this.currentState = newState;
	}
	public VirtStateEnum getState(){
		return currentState;
	}

}
