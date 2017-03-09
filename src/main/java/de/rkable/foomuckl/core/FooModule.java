package de.rkable.foomuckl.core;

import com.google.inject.AbstractModule;

import de.rkable.foomuckl.core.action.judgment.JudgmentCombinator;

public class FooModule extends AbstractModule  {

	@Override
	protected void configure() {
		bind(Environment.class).toInstance(new Environment(new JudgmentCombinator()));;
	}

}
