package haxm.policies;
import haxm.components.VM;
import haxm.policies.BWProvisioningPolicy;

public class BWProvisioningPolicySimple extends BWProvisioningPolicy
{

     BWProvisioningPolicySimple(double bw)
     {
     	setbw(bw);
     }

	 public void allocateBWtoVMs(double bw) //bw is bandwidth of host
	 {
	 	double bwpervm=bw/getVmList().size();
        for(VM vm: getVmList())
            vm.setAllocatedBW(bwpervm);
	 }

	@Override
	public double getAllocatedBwForVM(VM vm) {
		// TODO Auto-generated method stub
		return 0;
	}
}
