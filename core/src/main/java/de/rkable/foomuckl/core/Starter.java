package de.rkable.foomuckl.core;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Starter {

	private static final int INTERVALL = 100;

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new FooModuleCommandLine());
		Lifecycle instance = injector.getInstance(Lifecycle.class);

		for(;;) {
			try {
				Thread.sleep(INTERVALL);
				instance.executeEventLoop();
			} catch (InterruptedException e) {
				// no op
			}
		}
	}

}
