package haxm.components;

public class DIOTasklet extends Tasklet {
	private long diskIO;

	public DIOTasklet(long diskIO){
		this.setTaskletType(DISKIO);
		this.setDiskIO(diskIO);
	}

	
	/**
	 * @return the diskIO
	 */
	public long getDiskIO() {
		return diskIO;
	}

	/**
	 * @param diskIO the diskIO to set
	 */
	public void setDiskIO(long diskIO) {
		this.diskIO = diskIO;
	}

	
	public double calculateTime(double diskLatency){
		return diskLatency*this.getDiskIO();
	}
}
