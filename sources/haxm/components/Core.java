package haxm.components;

public class Core {
	/**
	 * @param mips
	 */
	public Core(long mips) {
		super();
		this.mips = mips;
	}

	/**
	 * @return the mips
	 */
	public long getMips() {
		return mips;
	}

	/**
	 * @param mips the mips to set
	 */
	public void setMips(long mips) {
		this.mips = mips;
	}

	private long mips;
	
}
