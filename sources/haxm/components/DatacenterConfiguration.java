package haxm.components;

import java.util.List;

public class DatacenterConfiguration {
	
	private int datacenterId;

	public int getDatacenterId() {
		return datacenterId;
	}

	public void setDatacenterId(int datacenterId) {
		this.datacenterId = datacenterId;
	}

	public List<Host> hostList;
	
	public List<Storage> storageList;
	
	public double bandwidth;
	
	public VMProvisioningPolicy vmProvisioningPolicy;;
	
	public double costPerMemory;
	
	public double costPerBW;
	
	public double costPerStorage;

	public DatacenterConfiguration(List<Host> hostList,
			List<Storage> storageList, double bandwidth,
			VMProvisioningPolicy vmProvisioningPolicy, double costPerMemory,
			double costPerBW, double costPerStorage) {
		super();
		this.hostList = hostList;
		this.storageList = storageList;
		this.bandwidth = bandwidth;
		this.vmProvisioningPolicy = vmProvisioningPolicy;
		this.costPerMemory = costPerMemory;
		this.costPerBW = costPerBW;
		this.costPerStorage = costPerStorage;
	}

	public List<Host> getHostList() {
		return hostList;
	}

	public void setHostList(List<Host> hostList) {
		this.hostList = hostList;
	}

	public List<Storage> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<Storage> storageList) {
		this.storageList = storageList;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public void setBandwidth(double bandwidth) {
		this.bandwidth = bandwidth;
	}

	public VMProvisioningPolicy getVmProvisioningPolicy() {
		return vmProvisioningPolicy;
	}

	public void setVmProvisioningPolicy(VMProvisioningPolicy vmProvisioningPolicy) {
		this.vmProvisioningPolicy = vmProvisioningPolicy;
	}

	public double getCostPerMemory() {
		return costPerMemory;
	}

	public void setCostPerMemory(double costPerMemory) {
		this.costPerMemory = costPerMemory;
	}

	public double getCostPerBW() {
		return costPerBW;
	}

	public void setCostPerBW(double costPerBW) {
		this.costPerBW = costPerBW;
	}

	public double getCostPerStorage() {
		return costPerStorage;
	}

	public void setCostPerStorage(double costPerStorage) {
		this.costPerStorage = costPerStorage;
	}
}
