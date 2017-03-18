package de.rkable.foomuckl.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.inject.Inject;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.DoNothing;
import de.rkable.foomuckl.core.action.SaySomething;
import de.rkable.foomuckl.core.action.judgment.HumanHarmed;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.action.judgment.OrderIgnored;
import de.rkable.foomuckl.core.action.judgment.RobotHarmed;
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

	final static Action DO_NOTHING = new DoNothing();
	private final static Action ACTION_BORED = new SaySomething("I am bored!", false);

	// injected dependencies

	// needs section
	int boredom = 0;

	private List<Event> inputs = new ArrayList<>();
	private Set<Action> options = new HashSet<>();

	private Environment environment;

	@Inject public FooMuckl(Environment environment) {
		this.environment = environment;
	}

	/**
	 * Added an input. FooMuckl will evaluate all inputs in due time.
	 *
	 * @param input The input to be added
	 */
	public void addInput(Event input) {
		inputs.add(input);
	}

	/**
	 * Evaluates the current options based on the received inputs
	 * and returns the action and the predicted outcome.
	 */
	public Entry<Action, Judgment> chooseFromOptions() {

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
		SortedSet<Entry<Action, Judgment>> sortedJudgements = new TreeSet<>((entry1, entry2) -> {
			int value1 = mapToInteger(entry1.getValue());
			int value2 = mapToInteger(entry2.getValue());
			System.out.println(entry1.getValue() + " vs " + entry2.getValue() + " = " + (value1 - value2));
			return value1 - value2;
		});

		sortedJudgements.addAll(judgments.entrySet());
		return sortedJudgements.last();
	}

	/**
	 * Maps a judgment to a value how desired it is.
	 * @param value
	 * @return The integer describing the judgment, smaller values refer to worse outcomes
	 */
	public int mapToInteger(Judgment value) {
		int MIN_VALUE = Integer.MIN_VALUE / 2;
		if (value instanceof HumanHarmed) return MIN_VALUE;
		if (value instanceof OrderIgnored) return MIN_VALUE + 1;
		if (value instanceof RobotHarmed) return MIN_VALUE + 2;

		if (value instanceof SatisfiesNeed) {
			int score = judgeBoredom((SatisfiesNeed) value);
			score += judgeShareInformation((SatisfiesNeed) value);
			// never get over values that check for Asimov's laws
			return (Math.max(score, 0));
		}

		return 0;
	}

	private int judgeShareInformation(SatisfiesNeed value) {
		return value.getSatisfaction(Need.SHARE_INFORMATION) * 10000;
	}

	private int judgeBoredom(SatisfiesNeed value) {
		return Math.min(boredom, value.getSatisfaction(Need.NOT_BORED));
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
