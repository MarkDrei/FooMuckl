package de.rkable.foomuckl.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import de.rkable.foomuckl.core.action.handler.SpeechHandler;
import de.rkable.foomuckl.core.action.judgment.JudgmentCombinator;

public class FooModule extends AbstractModule  {
	
	private Environment environment = null;

	@Override
	protected void configure() {
	}
	
	@Provides Environment provideEnvironment() {
		if (environment == null) {
			environment = new Environment(new JudgmentCombinator());
			
			// set up all handler
			environment.addActionHandler(new SpeechHandler());
		}
		
		return environment;
	}

}
