package de.rkable.foomuckl.ui;

import de.rkable.foomuckl.core.Environment;
import de.rkable.foomuckl.core.FooModule;
import de.rkable.foomuckl.core.action.handler.SpeechHandler;
import de.rkable.foomuckl.ui.action.handler.SpeechDisplay;

public class FooModuleUi extends FooModule {

	private SpeechDisplay speechDisplay = new SpeechDisplay();

	@Override
	protected void configure() {
		bind(SpeechHandler.class).toInstance(speechDisplay);
		bind(SpeechDisplay.class).toInstance(speechDisplay);
	}

	@Override
	protected void setupEnvironmentHandlers(Environment environment) {
		environment.addActionHandler(speechDisplay);
	}

}
