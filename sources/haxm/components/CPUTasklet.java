package haxm.components;

import java.util.List;

public class CPUTasklet extends Tasklet{
	private long instructionLength;
	
	public CPUTasklet(long instructionLength){
		this.setTaskletType(CPU);
		this.setInstructionLength(instructionLength);
	}
	public long getInstructionLength() {
		return instructionLength;
	}
	public void setInstructionLength(long instructionLength) {
		this.instructionLength = instructionLength;
	}
	@Override
	public double calculateTime(double mips) {
		return instructionLength/mips;
	}
	

}
