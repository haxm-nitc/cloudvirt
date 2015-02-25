package haxm.components;



import java.util.List;

public class DatacenterConfiguration {
	
	private int datacenterId;

	public List<Host> hostList;
	
	public List<Storage> storageList;
	
	public double bandwidth;
		
	public double costPerMemory;
	
	public double costPerBW;
	
	public double costPerStorage;

	public DatacenterConfiguration(List<Host> hostList,
			List<Storage> storageList, double bandwidth, double costPerMemory,
			double costPerBW, double costPerStorage) {
		super();
		this.hostList = hostList;
		this.storageList = storageList;
		this.bandwidth = bandwidth;
		this.costPerMemory = costPerMemory;
		this.costPerBW = costPerBW;
		this.costPerStorage = costPerStorage;
	}

	/**
	 * @return the datacenterId
	 */
	public int getDatacenterId() {
		return datacenterId;
	}

	/**
	 * @param datacenterId the datacenterId to set
	 */
	public void setDatacenterId(int datacenterId) {
		this.datacenterId = datacenterId;
	}

	/**
	 * @return the hostList
	 */
	public List<Host> getHostList() {
		return hostList;
	}

	/**
	 * @param hostList the hostList to set
	 */
	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}

	/**
	 * @return the storageList
	 */
	public List<Storage> getStorageList() {
		return storageList;
	}

	/**
	 * @param storageList the storageList to set
	 */
	public void setStorageList(List<Storage> storageList) {
		this.storageList = storageList;
	}

	/**
	 * @return the bandwidth
	 */
	public double getBandwidth() {
		return bandwidth;
	}

	/**
	 * @param bandwidth the bandwidth to set
	 */
	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}

	/**
	 * @return the costPerMemory
	 */
	public double getCostPerMemory() {
		return costPerMemory;
	}

	/**
	 * @param costPerMemory the costPerMemory to set
	 */
	public void setCostPerMemory(double costPerMemory) {
		this.costPerMemory = costPerMemory;
	}

	/**
	 * @return the costPerBW
	 */
	public double getCostPerBW() {
		return costPerBW;
	}

	/**
	 * @param costPerBW the costPerBW to set
	 */
	public void setCostPerBW(double costPerBW) {
		this.costPerBW = costPerBW;
	}

	/**
	 * @return the costPerStorage
	 */
	public double getCostPerStorage() {
		return costPerStorage;
	}

	/**
	 * @param costPerStorage the costPerStorage to set
	 */
	public void setCostPerStorage(double costPerStorage) {
		this.costPerStorage = costPerStorage;
	}
}
