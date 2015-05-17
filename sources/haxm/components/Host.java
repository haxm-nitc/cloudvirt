package haxm.components;

import haxm.VirtState;
import haxm.VirtStateEnum;
import haxm.core.CloudVirt;
import haxm.policies.BWProvisioningPolicy;
import haxm.policies.BWProvisioningPolicySimple;
import haxm.policies.MemoryProvisioningPolicy;
import haxm.policies.MemoryProvisioningPolicySimple;
import haxm.policies.VMSchedulerPolicy;
import haxm.policies.VMSchedulerPolicySimple;

import java.util.ArrayList;
import java.util.List;

/**
 * This class models the host class
 *
 */
public class Host {
	/**
	 * total hosts
	 */
	private static int numHosts = 0;
	/**
	 * id of host
	 */
	private int id;
	/**
	 * policy for vm scheduling
	 */
	private VMSchedulerPolicy vmSchedulerPolicy;
	/**
	 * policy for memory provisioning
	 */
	private MemoryProvisioningPolicy memoryProvisioningPolicy;
	/**
	 * policy for bw provisioning
	 */
	private BWProvisioningPolicy bwProvisioningPolicy;

	/**
	 * list of vm's/
	 */ 
	private List<VM> vmList;

	/**
	 *  memory in host.
	 */
	private  double memory;
	/**
	 * bw for host.
	 */
	private double bandwidth;
	/**
	 * mips of host.
	 */
	private  double mips;
	//5 msec per kb
	/**
	 * disk latency of host.
	 */
	private double diskLatency;
	/**
	 * id of datacenter it belongs to.
	 */
	private int datacenterId;
	/**
	 *  time of the next event.
	 */
	private double nextEventTime;
	
	/**
	 * state of host.
	 */
	private VirtState hostState;
	/**
	 * @param vmm
	 * @param storage
	 * @param memory
	 * @param bandwidth
	 * @param datacenter
	 * constructor
	 */
	public Host(double mips,  double memory, double bandwidth,double diskLatency) {
		super();
		this.setId(++numHosts);
		this.diskLatency = diskLatency;

		this.memory = memory;
		this.bandwidth = bandwidth;
		this.mips = mips;
		this.vmList = new ArrayList<VM>();
		hostState = new VirtState(VirtStateEnum.INVALID);
		vmSchedulerPolicy = new VMSchedulerPolicySimple(mips);
		bwProvisioningPolicy = new BWProvisioningPolicySimple(bandwidth);
		memoryProvisioningPolicy = new MemoryProvisioningPolicySimple(memory);
	}	

	/**
	 *  execute all VM's inside the host.
	 */
	public void executeVMs() {
		double minTime = Double.MAX_VALUE;
		double time;
		for(VM vm : getVmList()){
			// TODO resources as arguments
/*			
			System.out.println(vmSchedulerPolicy.getAllocatedMips(vm) + "      " +
					memoryProvisioningPolicy.getAllocatedMemoryForVM(vm) + "      " +
					bwProvisioningPolicy.getAllocatedBwForVM(vm) + "       " +
					diskLatency/vmList.size() + "     " + vm.getId());
*/			
			vm.executeTasks(vmSchedulerPolicy.getAllocatedMips(vm), 
					memoryProvisioningPolicy.getAllocatedMemoryForVM(vm), 
					bwProvisioningPolicy.getAllocatedBwForVM(vm), diskLatency/vmList.size());
			time = vm.getNextEventTime();
			if(time < minTime){
				minTime = time;
			}
		}		
		this.setNextEventTime(minTime);
		
	}
	/**
	 * @return the memory
	 */
	public  double getMemory() {
		return memory;
	}
	/**
	 * @param memory the memory to set
	 */
	public void setMemory(double memory) {
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
	 * @return the nextEventTime
	 */
	public double getNextEventTime() {
		return nextEventTime;
	}
	/**
	 * @param nextEventTime the nextEventTime to set
	 */
	public void setNextEventTime(double nextEventTime) {
		this.nextEventTime = nextEventTime;
	}
	/**
	 * @return the diskLatency
	 */
	public double getDiskLatency() {
		return diskLatency;
	}
	/**
	 * @param diskLatency the diskLatency to set
	 */
	public void setDiskLatency(double diskLatency) {
		this.diskLatency = diskLatency;
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
	}
	/**
	 * @return the vmSchedulerPolicy
	 */
	public VMSchedulerPolicy getVmSchedulerPolicy() {
		return vmSchedulerPolicy;
	}
	/**
	 * @param vmSchedulerPolicy the vmSchedulerPolicy to set
	 */
	public void setVmSchedulerPolicy(VMSchedulerPolicy vmSchedulerPolicy) {
		this.vmSchedulerPolicy = vmSchedulerPolicy;
	}
	/**
	 * @return the memoryProvisioningPolicy
	 */
	public MemoryProvisioningPolicy getMemoryProvisioningPolicy() {
		return memoryProvisioningPolicy;
	}
	/**
	 * @param memoryProvisioningPolicy the memoryProvisioningPolicy to set
	 */
	public void setMemoryProvisioningPolicy(MemoryProvisioningPolicy memoryProvisioningPolicy) {
		this.memoryProvisioningPolicy = memoryProvisioningPolicy;
	}
	/**
	 * @return the bwProvisioningPolicy
	 */
	public BWProvisioningPolicy getBwProvisioningPolicy() {
		return bwProvisioningPolicy;
	}
	/**
	 * @param bwProvisioningPolicy the bwProvisioningPolicy to set
	 */
	public void setBwProvisioningPolicy(BWProvisioningPolicy bwProvisioningPolicy) {
		this.bwProvisioningPolicy = bwProvisioningPolicy;
	}
	public void addVM(VM vm) {
		//System.out.println(vm.getId());
		getVmList().add(vm);
		vm.setHost(this);
	//	getVmSchedulerPolicy().addVM(vm);
	//	getBwProvisioningPolicy().addVM(vm);
	//	getMemoryProvisioningPolicy().addVM(vm);
	}

	public boolean createVM(VM vm) {
		boolean result = false;
		
		if(!bwProvisioningPolicy.canAllocateBW(vm, vm.getRequestedBW())){
			//System.out.println("failed bw vmid-"+vm.getId());
			return result;
		}
		if(!memoryProvisioningPolicy.canAllocateMemory(vm, vm.getRequestedMemory())){
			//System.out.println("failed memory vmid-"+vm.getId());
			return result;
		}
		if(!vmSchedulerPolicy.canAllocateMips(vm, vm.getRequestedMips())){
			//System.out.println("failed mips vmid-"+vm.getId());
			return result;
		}
		
		result = true;
		bwProvisioningPolicy.allocateBW(vm, vm.getRequestedBW());
		memoryProvisioningPolicy.allocateMemory(vm, vm.getRequestedMemory());
		vmSchedulerPolicy.allocateMips(vm, vm.getRequestedMips());
		
		return result;
	}

	/**
	 * @return the mips
	 */
	public  double getMips() {
		return mips;
	}

	/**
	 * @param mips the mips to set
	 */
	public void setMips(double mips) {
		this.mips = mips;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void removeVM(VM vm) {
		// TODO Auto-generated method stub
		getVmList().remove(vm);
		
	}
	
}
