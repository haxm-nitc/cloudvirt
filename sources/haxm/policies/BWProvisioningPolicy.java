package haxm.policies;

import haxm.components.VM;

import java.util.List;

public abstract class BWProvisioningPolicy {
   
    private List<VM> vmList;
    private double bw;            //bw of host

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

	public double getbw()
	{
		 return bw;
	}

	public void setbw(double a)
	{
		this.bw=a;
	} 	
	public abstract double getAllocatedBwForVM(VM vm);
}
