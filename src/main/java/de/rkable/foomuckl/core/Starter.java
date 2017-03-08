package de.rkable.foomuckl.core;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.rkable.foomuckl.core.input.ComeToLife;
import de.rkable.foomuckl.core.input.TimeElapsed;

public class Starter {

	private static final int INTERVALL = 100;

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new FooModule());
		
		FooMuckl fooMuckl = injector.getInstance(FooMuckl.class);
		fooMuckl.addInput(new ComeToLife());
		
		for(;;) {
			try {
				Thread.sleep(INTERVALL);
				fooMuckl.addInput(new TimeElapsed(INTERVALL));
				fooMuckl.evaluateOptions();
			} catch (InterruptedException e) {
				// screws up the timing, but who cares?
			}
		}
	}
}
