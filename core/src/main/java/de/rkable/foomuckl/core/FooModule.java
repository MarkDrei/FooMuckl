package de.rkable.foomuckl.core;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import de.rkable.foomuckl.core.action.judgment.JudgmentCombinator;

public abstract class FooModule extends AbstractModule  {

	private Environment environment = null;

	@Override
	protected void configure() {
	}

	@Provides Environment provideEnvironment() {
		if (environment == null) {
			environment = new Environment(new JudgmentCombinator());
			setupEnvironmentHandlers(environment);
		}
		return environment;
	}

	/**
	 * Subclasses should add all necessary action handler to the environment
	 *
	 * @param environment The environment which will be injected to all dependent classes
	 */
	protected abstract void setupEnvironmentHandlers(Environment environment);
}
