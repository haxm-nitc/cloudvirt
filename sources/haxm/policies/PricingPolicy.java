package haxm.policies;

import haxm.components.Task;
import haxm.components.VM;

public abstract class PricingPolicy {

	/**
	 * @param costPerInstruction
	 * @param costPerNio
	 * @param costPerMemory
	 * @param costPerDio
	 */
	public PricingPolicy(double costPerInstruction, double costPerDio, double costPerNio, double costPerMemory) {
		super();
		this.costPerInstruction = costPerInstruction;
		this.costPerNio = costPerNio;
		this.costPerMemory = costPerMemory;
		this.costPerDio = costPerDio;
	}
	private double costPerInstruction;
	private double costPerNio;
	private double costPerMemory;
	private double costPerDio;
	
	public abstract double costOfVM(VM vm);
	public abstract double costOfTask(Task task);
	
	/**
	 * @return the costPerNio
	 */
	public double getCostPerNio() {
		return costPerNio;
	}
	/**
	 * @param costPerNio the costPerNio to set
	 */
	public void setCostPerNio(double costPerNio) {
		this.costPerNio = costPerNio;
	}
	/**
	 * @return the costPerMemory
	 */
	public double getCostPerMemory() {
		return costPerMemory;
	}
	/**
	 * @param costPerMemory the costPerMemory to set
	 */
	public void setCostPerMemory(double costPerMemory) {
		this.costPerMemory = costPerMemory;
	}
	/**
	 * @return the costPerDio
	 */
	public double getCostPerDio() {
		return costPerDio;
	}
	/**
	 * @param costPerDio the costPerDio to set
	 */
	public void setCostPerDio(double costPerDio) {
		this.costPerDio = costPerDio;
	}
	/**
	 * @return the costPerInstruction
	 */
	public double getCostPerInstruction() {
		return costPerInstruction;
	}
	/**
	 * @param costPerInstruction the costPerInstruction to set
	 */
	public void setCostPerInstruction(double costPerInstruction) {
		this.costPerInstruction = costPerInstruction;
	}
}
