package haxm.policies;


import haxm.components.VM;

public class VMSchedulerPolicySimple extends VMSchedulerPolicy
{
	private int vmno;

	VMSchedulerPolicySimple()
	{
		vmno=0;
	}

    public void setvmno(int vmno)
    {
    	 this.vmno=vmno;
    }
    public int getvmno()
    {
    	return vmno;
    }
/*
	public VM getNextVM()
	{
		return getVmList().get(getvmno());
		setvmno((getvmno()+1)%getVmList().size());
	}
*/

	@Override
	public long getAllocatedMipsForVM(VM vm) {
		// TODO Auto-generated method stub
		return 0;
	}
}
