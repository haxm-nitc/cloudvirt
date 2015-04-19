package haxm.policies;

import haxm.components.Task;
import haxm.components.Tasklet;
import java.util.ArrayList;

public class PricingPolicySimple extends PricingPolicy
{
   
	public PricingPolicySimple(double cpuPrice,double bwPrice,double ioprice) 
	{
		setcostPerCpu(cpuPrice);
		setCostPerBw(bwPrice);
		setCostPerIo(ioprice);
	}

	@Override
	public void getPrice(long cpu, double bw, double diskLatency) 
	{
	
		for(Task task : getTasks())
		{
		  for(Tasklet tasklet : task.getTaskletList())	
		  {
			 switch(tasklet.getTaskletType()) 
			 {
			   case 0:
				   setPrice(getPrice()+getcostPerCpu());
				   break;
			   case 1:
				   setPrice(getPrice()+getCostPerBw());
				   break;
			   case 2 :
				   setPrice(getPrice()+getCostPerIo());
				   break;
			 }
	   	  }
	    }
	}
}
