package haxm.components;



import haxm.policies.PricingPolicy;

import java.util.List;

public class DatacenterConfiguration {
	
	private int datacenterId;

	public List<Host> hostList;
	
	public List<Storage> storageList;
	
	public double bandwidth;
	
	private PricingPolicy pricingPolicy;
		

	public DatacenterConfiguration(List<Host> hostList,
			List<Storage> storageList, double bandwidth, PricingPolicy pricingPolicy) {
		super();
		this.hostList = hostList;
		this.storageList = storageList;
		this.bandwidth = bandwidth;
		this.setPricingPolicy(pricingPolicy);
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
	 * @return the pricingPolicy
	 */
	public PricingPolicy getPricingPolicy() {
		return pricingPolicy;
	}

	/**
	 * @param pricingPolicy the pricingPolicy to set
	 */
	public void setPricingPolicy(PricingPolicy pricingPolicy) {
		this.pricingPolicy = pricingPolicy;
	}
}
