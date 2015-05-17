package haxm.policies;

import haxm.components.Task;
import haxm.components.VM;

/**
 * this class models the pricing policy
 *
 */
public abstract class PricingPolicy {

	/**
	 * @param costPerInstruction
	 * @param costPerNio
	 * @param costPerMemory
	 * @param costPerDio
	 * constructor
	 */
	public PricingPolicy(double costPerInstruction, double costPerDio, double costPerNio, double costPerMemory) {
		super();
		this.costPerInstruction = costPerInstruction;
		this.costPerNio = costPerNio;
		this.costPerMemory = costPerMemory;
		this.costPerDio = costPerDio;
	}
	/**
	 * cost per instruction
	 */
	private double costPerInstruction;
	/**
	 *  cost per network i/o 
	 */
	private double costPerNio;
	/**
	 *  cost per memory
	 */
	private double costPerMemory;
	/**
	 *  cost per dio.
	 */
	private double costPerDio;
	
	/**
	 * @param vm vm specified
	 * @return cost of vm
	 */
	public abstract double costOfVM(VM vm);
	/**
	 * @param task task to be evaluated
	 * @return cost of task
	 */
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
