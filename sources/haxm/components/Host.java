package haxm.components;

public class Host {
	private VMM vmm;
	private Storage storage;
	private long memory;
	private double bandwidth;
	private Datacenter datacenter;
	/**
	 * @param vmm
	 * @param storage
	 * @param memory
	 * @param bandwidth
	 * @param datacenter
	 */
	public Host(VMM vmm, Storage storage, long memory, double bandwidth) {
		super();
		this.vmm = vmm;
		this.storage = storage;
		this.memory = memory;
		this.bandwidth = bandwidth;
	}
	/**
	 * @return the vmm
	 */
	public VMM getVmm() {
		return vmm;
	}
	/**
	 * @param vmm the vmm to set
	 */
	public void setVmm(VMM vmm) {
		this.vmm = vmm;
	}
	/**
	 * @return the storage
	 */
	public Storage getStorage() {
		return storage;
	}
	/**
	 * @param storage the storage to set
	 */
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
	/**
	 * @return the memory
	 */
	public long getMemory() {
		return memory;
	}
	/**
	 * @param memory the memory to set
	 */
	public void setMemory(long memory) {
		this.memory = memory;
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
	 * @return the datacenter
	 */
	public Datacenter getDatacenter() {
		return datacenter;
	}
	/**
	 * @param datacenter the datacenter to set
	 */
	public void setDatacenter(Datacenter datacenter) {
		this.datacenter = datacenter;
	}
	
}
