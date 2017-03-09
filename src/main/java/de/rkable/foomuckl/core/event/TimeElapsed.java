package de.rkable.foomuckl.core.event;

/**
 * This input represents time that has elapsed
 * @author Mark
 *
 */
public class TimeElapsed implements Event {

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
