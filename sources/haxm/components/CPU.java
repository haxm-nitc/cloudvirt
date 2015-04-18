package haxm.components;

import java.util.List;

public class CPU {
	private List<Core> cores;

	/**
	 * @param cores
	 */
	public CPU(List<Core> cores) {
		super();
		this.cores = cores;
	}
	/**
	 * @return the cores
	 */
	public List<Core> getCores() {
		return cores;
	}

	/**
	 * @param cores the cores to set
	 */
	public void setCores(List<Core> cores) {
		this.cores = cores;
	}


}
