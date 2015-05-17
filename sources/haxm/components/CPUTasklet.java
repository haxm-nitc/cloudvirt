package haxm.components;

import java.util.List;

/**
 * Class for CPUTasklet that models the task module for CPU
 *
 */
public class CPUTasklet extends Tasklet{
	/**
	 *   instructionlength of task
	 */
	private long instructionLength;
	/**
	 *   remaining instruction length of task
	 */
	private long remainingInstructionLength;
	
	/**
	 * @param instructionLength instructionlength of task
	 */
	public CPUTasklet(long instructionLength){
		this.setTaskletType(CPU);
		this.setInstructionLength(instructionLength);
		this.setRemainingInstructionLength(instructionLength);
	}
	/**
	 * @return the instructionlength
	 */
	public long getInstructionLength() {
		return instructionLength;
	}
	/**
	 * @param instructionLength to set instructionlength
	 */
	public void setInstructionLength(long instructionLength) {
		this.instructionLength = instructionLength;
	}
	/**
	 * @return the remainingInstructionLength
	 */
	public long getRemainingInstructionLength() {
		return remainingInstructionLength;
	}
	/**
	 * @param remainingInstructionLength the remainingInstructionLength to set
	 */
	public void setRemainingInstructionLength(long remainingInstructionLength) {
		this.remainingInstructionLength = remainingInstructionLength;
	}
	@Override
	public double calculateRemainingTime(double mips, double memory, double bw,
			double diskLatency) {
		// TODO Auto-generated method stub
		//System.out.println(mips + "     " +getRemainingInstructionLength());
		return getRemainingInstructionLength()/(mips*1000000);
	}
	

}
