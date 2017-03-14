package de.rkable.foomuckl.core;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.SaySomething;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.event.ComeToLife;
import de.rkable.foomuckl.core.event.TimeElapsed;

public class TestFooMuckl {

	private Injector injector;

	@Before
	public void before() {
		injector = Guice.createInjector(new TestModule());
	}
	
	@Test
	public void testThatFooMucklAnnouncesBirth() {
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new ComeToLife());
		Entry<Action, Judgment> option = fooMuckl.chooseOptions();
		assertThatSpeachContainsString(option.getKey(), "FooMuckl should anounce that he was born", " born");
	}
	
	@Test
	public void testThatFooMucklGetsBored()	{
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new TimeElapsed(20000));
		Entry<Action, Judgment> option = fooMuckl.chooseOptions();
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
}
