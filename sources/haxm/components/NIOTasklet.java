package haxm.components;

public class NIOTasklet extends Tasklet{
	private long data;
	private long remainingData;
	/**
	 * @param data
	 * @param remainingData
	 */
	public NIOTasklet(long data, long remainingData) {
		super();
		this.data = data;
		this.remainingData = remainingData;
		this.setTaskletType(NETWORKIO);
	}
	/**
	 * @return the data
	 */
	public long getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(long data) {
		this.data = data;
	}
	/**
	 * @return the remainingData
	 */
	public long getRemainingData() {
		return remainingData;
	}
	/**
	 * @param remainingData the remainingData to set
	 */
	public void setRemainingData(long remainingData) {
		this.remainingData = remainingData;
	}
	@Override
	public double calculateRemainingTime(long mips, long memory, double bw,
			double diskLatency) {
		// TODO Auto-generated method stub
		return getRemainingData()/bw;
	}
	
}
