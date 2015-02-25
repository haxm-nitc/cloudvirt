package haxm.components;

public abstract class Tasklet {
	public final static int CPU = 0; 
	public final static int NETWORKIO = 1; 
	public final static int DISKIO = 2;
	private int taskletType;
	public int getTaskletType() {
		return taskletType;
	}
	public void setTaskletType(int taskletType) {
		this.taskletType = taskletType;
	}
	
	public abstract double calculateTime(double l);
	

}