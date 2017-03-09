package de.rkable.foomuckl.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import de.rkable.foomuckl.core.action.handler.SpeechHandler;
import de.rkable.foomuckl.core.action.judgment.JudgmentCombinator;

public class TestModule extends AbstractModule {

	private static TestEnvironment testEnvironment = null;
	
	@Override
	protected void configure() {
//		bind(Environment.class).toInstance(new TestEnvironment(new JudgmentCombinator()));
	}
	
	@Provides public Environment provideEnvironment() {
		if (testEnvironment == null) {
			testEnvironment = new TestEnvironment(new JudgmentCombinator());
			testEnvironment.addActionHandler(new SpeechHandler());
		}
		return testEnvironment;
	}

}
