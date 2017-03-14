package de.rkable.foomuckl.core.event;

/**
 * This input represents time that has elapsed
 * @author Mark
 *
 */
public class TimeElapsed implements Event {

	private long elapsedMillis;
	
	public TimeElapsed(long elapsedMillis) {
		this.elapsedMillis = elapsedMillis;
	}
	
	/**
	 * 
	 * @return the elapsed time in milli seconds
	 */
	public long getElapsedMillis() {
		return elapsedMillis;
	}
}
