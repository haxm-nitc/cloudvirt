public class State{
	private StateEnum currentState;
	
	public setState(StateEnum newState){
		this.currentState = newState;
	}
	public StateEnum getState(){
		return currentState;
	}

}
