package de.rkable.foomuckl.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.google.inject.Inject;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.DoNothing;
import de.rkable.foomuckl.core.action.SaySomething;
import de.rkable.foomuckl.core.action.judgment.HumanHarmed;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.action.judgment.NoConsequence;
import de.rkable.foomuckl.core.action.judgment.OrderIgnored;
import de.rkable.foomuckl.core.action.judgment.RotoberHarmed;
import de.rkable.foomuckl.core.action.judgment.SatisfiesNeed;
import de.rkable.foomuckl.core.action.judgment.SatisfiesNeed.Need;
import de.rkable.foomuckl.core.event.ComeToLife;
import de.rkable.foomuckl.core.event.Event;
import de.rkable.foomuckl.core.event.TimeElapsed;

/**
 * FooMuckl is an automaton. It collects inputs, evaluates its options and
 * then can trigger actions
 * 
 * @author Mark
 *
 */
public class FooMuckl {
	
	private final static Action DO_NOTHING = new DoNothing();
	private final static Action ACTION_BORED = new SaySomething("I am bored!", false);
	
	// needs section
	int boredom = 0;
	
	private List<Event> inputs = new ArrayList<>();
	private Set<Action> options = new HashSet<>();

	@Inject private Environment environment;
	
	public void addInput(Event input) {
		inputs.add(input);
	}
	
	/**
	 * Evaluates the current options based on the received inputs
	 */
	public Entry<Action, Judgment> chooseOptions() {
		
		processInputs();

		updateOptions();
		
		Entry<Action, Judgment> choice = chooseOption();
		
		options.remove(choice.getKey());
		return choice;
	}

	
	/**
	 * Applies the consequences of a judgment
	 * @param value
	 */
	public void applyJudgment(Judgment value) {
		if (value instanceof SatisfiesNeed) {
			boredom -= ((SatisfiesNeed) value).getSatisfaction(Need.NOT_BORED);
		}
	}

	/**
	 * From all available actions, choose the one with the best consequences
	 * @return
	 */
	private Entry<Action, Judgment> chooseOption() {
		Map<Action, Judgment> judgments = environment.judge(options);
		
		Entry<Action, Judgment> bestSatisfaction = null;
		
		Entry<Action, Judgment> noConsequence = null;
		Entry<Action, Judgment> roboterHarmed = null;
		Entry<Action, Judgment> orderIgnored = null;
		Entry<Action, Judgment> humanHarmed = null;
		
		for (Entry<Action, Judgment> e : judgments.entrySet()) {
			Judgment value = e.getValue();
			if (value instanceof SatisfiesNeed) {
				if (bestSatisfaction == null) {
					bestSatisfaction = e;
				} else {
					bestSatisfaction = getBetterSatisfaction(e, bestSatisfaction);
				}
				continue;
			} else if (value instanceof NoConsequence) {
				noConsequence = e;
			} else if (value instanceof RotoberHarmed) {
				roboterHarmed = e;
			} else if (value instanceof OrderIgnored) {
				orderIgnored = e;
			} else if (value instanceof HumanHarmed) {
				humanHarmed = e;
			}
		}
		
		if (bestSatisfaction != null && satisfactionWanted((SatisfiesNeed) bestSatisfaction.getValue())) return bestSatisfaction;
		if (noConsequence != null) return noConsequence;
		if (bestSatisfaction != null) return bestSatisfaction;
		
		// here we are entering the zone of danger!
		if (roboterHarmed != null) return roboterHarmed;
		if (orderIgnored != null) return orderIgnored;
		if (humanHarmed != null) return humanHarmed;

		// should not happen
		throw new IllegalStateException();
	}

	/**
	 * Judge whether the satisfaction fulfills any current needs
	 * 
	 * @param value
	 * @return
	 */
	private boolean satisfactionWanted(SatisfiesNeed value) {
		if (value.getSatisfaction(Need.SHARE_INFORMATION) > 0) {
			return true;
		}
		if (value.getSatisfaction(Need.NOT_BORED) > 0 && boredom > 0) {
			return true;
		}
		return false;
	}

	private Entry<Action, Judgment> getBetterSatisfaction(Entry<Action, Judgment> oneVal, Entry<Action, Judgment> twoVal) {
		if (twoVal == null) {
			return oneVal;
		}
		SatisfiesNeed one = (SatisfiesNeed) oneVal.getValue();
		SatisfiesNeed two = (SatisfiesNeed) twoVal.getValue();

		if (one.getSatisfaction(Need.SHARE_INFORMATION) > two.getSatisfaction(Need.SHARE_INFORMATION)) {
			return oneVal;
		}
		if (one.getSatisfaction(Need.SHARE_INFORMATION) < two.getSatisfaction(Need.SHARE_INFORMATION)) {
			return twoVal;
		}
		
		if (one.getSatisfaction(Need.NOT_BORED) > two.getSatisfaction(Need.NOT_BORED)) {
			return oneVal;
		}
		return twoVal;
	}

	/**
	 * Updates all options.
	 * Can remove outdated options or add new ones.
	 */
	private void updateOptions() {
		if (boredom > 0) {
			options.add(ACTION_BORED);
		}
		options.add(DO_NOTHING);
	}

	/**
	 * Processes all inputs.
	 * Inputs which can currently be processed will be removed, and 
	 * potentially options will be created.
	 */
	private void processInputs() {
		List<Event> inputsToProcess = new ArrayList<>(inputs);
		for (Event input : inputsToProcess) {
			if (input instanceof ComeToLife) {
				options.add(new SaySomething("Oh I was born!", true));
				inputs.remove(input);
			} else if (input instanceof TimeElapsed) {
				boredom += ((TimeElapsed) input).getElapsedMillis();
				inputs.remove(input);
			} else {
				System.out.println("I don't understand " + input);
			}
		}
	}

}
