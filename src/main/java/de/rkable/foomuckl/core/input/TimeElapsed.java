package de.rkable.foomuckl.core.input;

/**
 * This input represents time that has elapsed
 * @author Mark
 *
 */
public class TimeElapsed implements Input {

	private int elapsedMillis;
	
	public TimeElapsed(int elapsedMillis) {
		this.elapsedMillis = elapsedMillis;
	}
	
	/**
	 * 
	 * @return the elapsed time in milli seconds
	 */
	public int getElapsedMillis() {
		return elapsedMillis;
	}
}
