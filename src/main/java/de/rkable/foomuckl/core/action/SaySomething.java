package de.rkable.foomuckl.core.action;

public class SaySomething implements Action {

	private final String speech;
	private final boolean isRelevant;

	public SaySomething(String speech, boolean isRelevant) {
		this.speech = speech;
		this.isRelevant = isRelevant;
	}
	
	public String getSpeech() {
		return speech;
	}

	public boolean isRelevant() {
		return isRelevant;
	}
	
	@Override
	public String toString() {
		return "SaySomething[speech=\"" + speech + "\", relevant=" + isRelevant + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isRelevant ? 1231 : 1237);
		result = prime * result + ((speech == null) ? 0 : speech.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SaySomething other = (SaySomething) obj;
		if (isRelevant != other.isRelevant)
			return false;
		if (speech == null) {
			if (other.speech != null)
				return false;
		} else if (!speech.equals(other.speech))
			return false;
		return true;
	}
	
	
}
