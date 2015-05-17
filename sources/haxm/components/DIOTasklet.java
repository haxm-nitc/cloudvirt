package haxm.components;

/**
 * this class models the I/O task module.
 *
 */
/**
 * @author Xavier Jose
 *
 */
public class DIOTasklet extends Tasklet {
	/**
	 *  data to be read
	 */
	private  double data;
	/**
	 *  remaining data to be read.
	 */
	private  double remainingData;

	/**
	 * @param data 
	 * constructor
	 */
	public DIOTasklet(double data){
		this.setTaskletType(DISKIO);
		this.setData(data);
		this.setRemainingData(data);
	}




	/**
	 * @return remainingdata
	 * getter
	 */
	public  double getRemainingData() {
		// TODO Auto-generated method stub
		return remainingData;
	}



	/**
	 * @return the data
	 * getter
	 */
	public  double getData() {
		return data;
	}



	/**
	 * @param data the data to set
	 * setter
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
