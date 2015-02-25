package haxm.components;

public class NIOTasklet extends Tasklet{
	private long networkIO;//in MB
	
	public NIOTasklet(long networkIO){
		this.setNetworkIO(networkIO);
		this.setTaskletType(NETWORKIO);
	}
	public double calculateTime(double bw){
		return networkIO/bw;
	}
	public long getNetworkIO() {
		return networkIO;
	}
	public void setNetworkIO(long networkIO) {
		this.networkIO = networkIO;
	}
}
