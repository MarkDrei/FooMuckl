package de.rkable.foomuckl.core;

import com.google.inject.AbstractModule;

public class TestModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(Environment.class).toInstance(new TestEnvironment());

	}

}
