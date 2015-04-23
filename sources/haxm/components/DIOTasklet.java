package haxm.components;

public class DIOTasklet extends Tasklet {
	private  double data;
	private  double remainingData;

	public DIOTasklet(double data){
		this.setTaskletType(DISKIO);
		this.setData(data);
		this.setRemainingData(data);
	}




	public  double getRemainingData() {
		// TODO Auto-generated method stub
		return remainingData;
	}



	/**
	 * @return the data
	 */
	public  double getData() {
		return data;
	}



	/**
	 * @param data the data to set
	 */
	public void setData(double data) {
		this.data = data;
	}



	/**
	 * @param remainingData the remainingData to set
	 */
	public void setRemainingData(double remainingData) {
		this.remainingData = remainingData;
	}



	@Override
	public double calculateRemainingTime(double mips,  double memory, double bw,
			double diskLatency) {
		// TODO Auto-generated method stub
		return getRemainingData()/diskLatency;
	}
}
