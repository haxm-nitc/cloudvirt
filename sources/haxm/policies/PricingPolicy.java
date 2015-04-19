package haxm.policies;

import haxm.components.Task;

import java.util.List;


public abstract class PricingPolicy {
    private double costPerCpu;
    private double costPerBw;
    private double costPerIo;
    private double price = 0;
	
    private List<Task> tasks;

	public double getcostPerCpu() {
		return costPerCpu;
	}

	public void setcostPerCpu(double costPerCpu) {
		this.costPerCpu = costPerCpu;
	}

	public double getCostPerBw() {
		return costPerBw;
	}

	public void setCostPerBw(double costPerBw) {
		this.costPerBw = costPerBw;
	}

	public double getCostPerIo() {
		return costPerIo;
	}

	public void setCostPerIo(double costPerIo) {
		this.costPerIo = costPerIo;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
    
    public abstract void getPrice(long cpu, double bw, double diskLatency);
}
