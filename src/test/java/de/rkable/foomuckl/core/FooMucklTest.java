package de.rkable.foomuckl.core;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.rkable.foomuckl.core.input.ComeToLife;
import de.rkable.foomuckl.core.input.TimeElapsed;
import de.rkable.foomuckl.core.output.Output;
import de.rkable.foomuckl.core.output.SaySomething;

public class FooMucklTest {

	private Injector injector;

	@Before
	public void before() {
		injector = Guice.createInjector(new TestModule());
	}
	
	@Test
	public void testThatFooMucklAnnouncesBirth() {
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new ComeToLife());
		fooMuckl.evaluateOptions();
		assertThatSpeachContainsString("FooMuckl should anounce that he was born", " born");
	}
	
	@Test
	public void testThatFooMucklGetsBored()	{
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new TimeElapsed(20000));
		fooMuckl.evaluateOptions();
		assertThatSpeachContainsString("FooMuckl should anounce that he was born", "bored");
	}
	
	/**
	 * Asserts that the environment contains a string with the given value
	 * 
	 * @param message The message of the assert message
	 * @param searchPattern the string to look for in the messages
	 */
	private void assertThatSpeachContainsString(String message, String searchPattern) {
		Environment environment = injector.getInstance(Environment.class);

		assertTrue(environment instanceof TestEnvironment);
		List<Output> outputs = ((TestEnvironment) environment).outputs;
		
		boolean found = false;
		for (Output output : outputs) {
			if (output instanceof SaySomething) {
				if (((SaySomething) output).getSpeech().contains(searchPattern)) {
					found = true;
					break;
				}
			}
		}
		assertTrue(message, found);
	}
}
