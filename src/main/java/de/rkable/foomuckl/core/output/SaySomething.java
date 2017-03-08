package de.rkable.foomuckl.core.output;

public class SaySomething implements Output {

	private final String speech;

	public SaySomething(String speech) {
		this.speech = speech;
	}
	
	public String getSpeech() {
		return speech;
	}
}
