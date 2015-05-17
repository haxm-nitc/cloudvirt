package haxm.components;



import haxm.policies.PricingPolicy;

import java.util.List;

/**
 * This class models the Datacenter configuration.
 *
 */
public class DatacenterConfiguration {
	
	/**
	 *  id of datacenter.
	 */
	private int datacenterId;

	/**
	 *  list of hosts in the datacenter.
	 */
	public List<Host> hostList;
	

	
	/**
	 * total bandwidth of datacenter.
	 */
	public double bandwidth;
	
	/**
	 * policy for charging users for usage.
	 */
	private PricingPolicy pricingPolicy;
		

	/**
	 * @param hostList 
	 * @param bandwidth
	 * @param pricingPolicy
	 * constructor to set variables.
	 */
	public DatacenterConfiguration(List<Host> hostList, double bandwidth, PricingPolicy pricingPolicy) {
		super();
		this.hostList = hostList;
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
