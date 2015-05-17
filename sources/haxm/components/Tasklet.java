package haxm.components;

/**
 * this class models a tasklet.
 *
 */
public abstract class Tasklet {
	/**
	 *  code for CPU tasklet.
	 */
	public final static int CPU = 0; 
	/**
	 * code for network tasklet.
	 */
	public final static int NETWORKIO = 1; 
	/**
	 * code for disk I/O tasklet.
	 */
	public final static int DISKIO = 2;
	
	/**
	 * type of the tasklet.
	 */
	private int taskletType;
	
	/**
	 * @return tasklettype
	 */
	public int getTaskletType() {
		return taskletType;
	}
	/**
	 * @param taskletType to set the tasklet type.
	 */
	public void setTaskletType(int taskletType) {
		this.taskletType = taskletType;
	}
	
	/**
	 * @param mips mips of VM
	 * @param memory memory for VM
	 * @param bw bw for VM
	 * @param diskLatency dislatency
	 * @return remaining time for tasklet completion
	 */
	public abstract double calculateRemainingTime(double mips,  double memory, double bw, double diskLatency);
	

}
