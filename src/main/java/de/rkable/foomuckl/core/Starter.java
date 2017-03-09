package de.rkable.foomuckl.core;

import java.util.Arrays;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.rkable.foomuckl.core.action.handler.SpeechHandler;
import de.rkable.foomuckl.core.event.ComeToLife;
import de.rkable.foomuckl.core.event.Event;
import de.rkable.foomuckl.core.event.TimeElapsed;

public class Starter {

	private static final int INTERVALL = 100;

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new FooModule());
		Environment environment = injector.getInstance(Environment.class);
		setupEnvironment(injector, environment);
		
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new ComeToLife());
		
		for(;;) {
			try {
				Thread.sleep(INTERVALL);
				Event timeElapsed = new TimeElapsed(INTERVALL);
				environment.reactOnEvents(Arrays.asList(timeElapsed));
				fooMuckl.addInput(timeElapsed);
				fooMuckl.evaluateOptions();
			} catch (InterruptedException e) {
				// screws up the timing, but who cares?
			}
		}
	}

	private static void setupEnvironment(Injector injector, Environment environment) {
		environment.addActionHandler(injector.getInstance(SpeechHandler.class));
		
	}
}
