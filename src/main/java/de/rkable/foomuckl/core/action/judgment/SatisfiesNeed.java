package de.rkable.foomuckl.core.action.judgment;

import java.util.HashMap;
import java.util.Map;

public class SatisfiesNeed implements Judgment {

	public enum Need {
		NOT_BORED,
	 	SHARE_INFORMATION
	}
	
	Map<Need, Integer> satisfactions = new HashMap<>();
	
	public void addSatisfaction(Need need, int valueChange) {
		Integer integer = satisfactions.get(need);
		if (integer == null) {
			integer = new Integer(0);
		}
		satisfactions.put(need, Integer.valueOf(integer.intValue() + valueChange));
	}

	/**
	 * Combines another SatisfiesNeed to this one.
	 * 
	 * @param two The other SatisfiesNeed
	 * @return this for convenience
	 */
	public SatisfiesNeed combine(SatisfiesNeed two) {
		for (Need need : two.satisfactions.keySet()) {
			if (satisfactions.containsKey(need)) {
				Integer integer = satisfactions.get(need);
				satisfactions.put(need, integer + two.satisfactions.get(need));
			}
		}
		
		return this;
	}

	public int getSatisfaction(Need need) {
		Integer integer = satisfactions.get(need);
		if (integer == null) {
			return 0;
		}
		return integer.intValue();
	}
}
