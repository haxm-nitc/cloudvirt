package haxm.components;

/**
 * this class  models the Network I/O task module
 *
 */
public class NIOTasklet extends Tasklet{
	/**
	 * data to be transferred over network
	 */
	private  double data;
	/**
	 * remaining data to be transferred.
	 */
	private  double remainingData;
	/**
	 * @param data
	 * @param remainingData
	 * constructor.
	 */
	public NIOTasklet(double data) {
		super();
		this.data = data;
		this.remainingData = data;
		this.setTaskletType(NETWORKIO);
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
	 * @return the remainingData
	 */
	public  double getRemainingData() {
		return remainingData;
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
		return getRemainingData()/bw;
	}
	
}
