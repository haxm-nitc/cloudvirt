package haxm.policies;
import haxm.policies.VM;

public abstract class MemoryProvisioningPolicy {
    private List<VM> vmList;
    private long memory;            //memory of host

    public void addVM(VM vm){
		getVmList().add(vm);
	}
    	/**
	 * @return the vmList
	 */
	public List<VM> getVmList() {
		return vmList;
	}
	/**
	 * @param vmList the vmList to set
	 */
	public void setVmList(List<VM> vmList) {
		this.vmList = vmList;
	};

	public long getMemory()
	{
		 return memory;
	}

	public void setMemory(long a)
	{
		this.memory=a;
	}
}
