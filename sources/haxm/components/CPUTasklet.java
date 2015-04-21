package haxm.components;

import java.util.List;

public class CPUTasklet extends Tasklet{
	private long instructionLength;
	private long remainingInstructionLength;
	
	public CPUTasklet(long instructionLength){
		this.setTaskletType(CPU);
		this.setInstructionLength(instructionLength);
		this.setRemainingInstructionLength(instructionLength);
	}
	public long getInstructionLength() {
		return instructionLength;
	}
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
	public double calculateRemainingTime(long mips, long memory, double bw,
			double diskLatency) {
		// TODO Auto-generated method stub
		return getRemainingInstructionLength()/(mips*1000000);
	}
	

}
