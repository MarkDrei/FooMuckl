package de.rkable.foomuckl.core.action.judgment;

/**
 * Combines multiple judgments into one.
 * Returns the most significant judgment or a summary of the judgments
 * 
 * @author Mark
 *
 */
public class JudgmentCombinator {

	public Judgment combine(Judgment one, Judgment two) {
		// This part implements Asimov's robot laws
		if (one instanceof HumanHarmed) return one;
		if (two instanceof HumanHarmed) return two;
		if (one instanceof OrderIgnored) return one;
		if (two instanceof OrderIgnored) return two;
		if (one instanceof RotoberHarmed) return one;
		if (two instanceof RotoberHarmed) return two;
		
		if (one instanceof SatisfiesNeed && two instanceof SatisfiesNeed) {
			return ((SatisfiesNeed) one).combine((SatisfiesNeed) two);
		}
		
		if (one instanceof SatisfiesNeed) return one;
		if (two instanceof SatisfiesNeed) return two;
		
		return new NoConsequence();
	}
}
