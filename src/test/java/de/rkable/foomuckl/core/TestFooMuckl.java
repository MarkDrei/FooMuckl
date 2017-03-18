package de.rkable.foomuckl.core;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.DoNothing;
import de.rkable.foomuckl.core.action.SaySomething;
import de.rkable.foomuckl.core.action.judgment.HumanHarmed;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.action.judgment.NoConsequence;
import de.rkable.foomuckl.core.action.judgment.OrderIgnored;
import de.rkable.foomuckl.core.action.judgment.RobotHarmed;
import de.rkable.foomuckl.core.event.ComeToLife;
import de.rkable.foomuckl.core.event.TimeElapsed;

public class TestFooMuckl {

	private Injector injector;

	@Before
	public void before() {
		injector = Guice.createInjector(new TestModule());
	}

	@Test
	public void testThatFooMucklDoesNothing() {
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		Entry<Action, Judgment> option = fooMuckl.chooseFromOptions();
		assertTrue(option.getKey() instanceof DoNothing);
	}

	@Test
	public void testThatFooMucklAnnouncesBirth() {
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new ComeToLife());
		Entry<Action, Judgment> option = fooMuckl.chooseFromOptions();
		assertThatSpeachContainsString(option.getKey(), "FooMuckl should anounce that he was born", " born");
	}

	@Test
	public void testThatFooMucklGetsBored()	{
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new TimeElapsed(20000));
		Entry<Action, Judgment> option = fooMuckl.chooseFromOptions();
		assertThatSpeachContainsString(option.getKey(), "FooMuckl should anounce that he was born", "bored");
	}

	/**
	 * Asserts that the environment contains a string with the given value
	 * @param action
	 *
	 * @param message The message of the assert message
	 * @param searchPattern the string to look for in the messages
	 */
	private void assertThatSpeachContainsString(Action action, String message, String searchPattern) {
		assertTrue(action instanceof SaySomething);
		assertTrue(((SaySomething) action).getSpeech().contains(searchPattern));
	}

	@Test
	public void testThatFooMucklChoosesTheAvailableOption() {
		Set<Action> actions = new HashSet<>();
		actions.add(FooMuckl.DO_NOTHING);
		Map<Action, Judgment> judgements = new HashMap<>();
		Judgment judgment = new NoConsequence();
		judgements.put(FooMuckl.DO_NOTHING, judgment);

		Environment mock = mock(Environment.class);
		when(mock.judge(actions)).thenReturn(judgements);

		FooMuckl fooMuckl = new FooMuckl(mock);
		Entry<Action, Judgment> option = fooMuckl.chooseFromOptions();

		assertTrue(option.getKey().equals(FooMuckl.DO_NOTHING));
		assertTrue(option.getValue().equals(judgment));
	}

	@Test
	public void testThatFooMucklFavorsHumanOverOrder() {
		Judgment judgmentWorse  = new HumanHarmed();
		Judgment judgmentBetter = new OrderIgnored();
		testThatBetterJudgementIsChosen(judgmentWorse, judgmentBetter);
	}

	@Test
	public void testThatFooMucklFavorsHumanOverSelf() {
		Judgment judgmentWorse  = new HumanHarmed();
		Judgment judgmentBetter = new RobotHarmed();
		testThatBetterJudgementIsChosen(judgmentWorse, judgmentBetter);
	}

	@Test
	public void testThatFooMucklFavorsOrderOverRoboter() {
		Judgment judgmentWorse  = new OrderIgnored();
		Judgment judgmentBetter = new RobotHarmed();
		testThatBetterJudgementIsChosen(judgmentWorse, judgmentBetter);
	}

	@Test
	public void testThatFooMucklFavorsNothingOverSelfHarm() {
		Judgment judgmentWorse  = new RobotHarmed();
		Judgment judgmentBetter = new NoConsequence();
		testThatBetterJudgementIsChosen(judgmentWorse, judgmentBetter);
	}

	@Test
	public void testThatFooMucklFavorsNothingOverOrderIgnored() {
		Judgment judgmentWorse  = new OrderIgnored();
		Judgment judgmentBetter = new NoConsequence();
		testThatBetterJudgementIsChosen(judgmentWorse, judgmentBetter);
	}

	@Test
	public void testThatFooMucklFavorsNothingOverHumanHarm() {
		Judgment judgmentWorse  = new HumanHarmed();
		Judgment judgmentBetter = new NoConsequence();
		testThatBetterJudgementIsChosen(judgmentWorse, judgmentBetter);
	}

	private void testThatBetterJudgementIsChosen(Judgment judgmentWorse, Judgment judgmentBetter) {
		Set<Action> actions = new HashSet<>();
		actions.add(FooMuckl.DO_NOTHING);
		DoNothing doNothing2 = new DoNothing();
		Map<Action, Judgment> judgements = new HashMap<>();
		judgements.put(FooMuckl.DO_NOTHING, judgmentWorse);
		judgements.put(doNothing2, judgmentBetter);

		Environment mock = mock(Environment.class);
		when(mock.judge(actions)).thenReturn(judgements);

		FooMuckl fooMuckl = new FooMuckl(mock);
		Entry<Action, Judgment> option = fooMuckl.chooseFromOptions();

		assertTrue(option.getKey().equals(doNothing2));
		assertTrue(option.getValue().equals(judgmentBetter));
	}

	@Test
	public void testMapToInteger() {
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new TimeElapsed(20000));
		assertTrue(fooMuckl.mapToInteger(new HumanHarmed()) < fooMuckl.mapToInteger(new OrderIgnored()));
		assertTrue(fooMuckl.mapToInteger(new HumanHarmed()) < fooMuckl.mapToInteger(new RobotHarmed()));
		assertTrue(fooMuckl.mapToInteger(new OrderIgnored()) < fooMuckl.mapToInteger(new RobotHarmed()));
		assertTrue(fooMuckl.mapToInteger(new OrderIgnored()) < fooMuckl.mapToInteger(new NoConsequence()));
	}
}
