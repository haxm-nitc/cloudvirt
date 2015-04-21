package haxm.components;

public class DIOTasklet extends Tasklet {
	private long data;
	private long remainingData;

	public DIOTasklet(long data){
		this.setTaskletType(DISKIO);
		this.setData(data);
		this.setRemainingData(data);
	}




	public long getRemainingData() {
		// TODO Auto-generated method stub
		return remainingData;
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
	 * @param remainingData the remainingData to set
	 */
	public void setRemainingData(long remainingData) {
		this.remainingData = remainingData;
	}



	@Override
	public double calculateRemainingTime(long mips, long memory, double bw,
			double diskLatency) {
		// TODO Auto-generated method stub
		return getRemainingData()/diskLatency;
	}
}
