package de.rkable.foomuckl.core;

import de.rkable.foomuckl.core.action.handler.SpeechHandler;

public class FooModuleCommandLine extends FooModule {

	@Override
	protected void setupEnvironmentHandlers(Environment environment) {
		environment.addActionHandler(new SpeechHandler());

	}
}
