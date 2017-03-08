package de.rkable.foomuckl.core;

import de.rkable.foomuckl.core.input.ComeToLife;
import de.rkable.foomuckl.core.input.TimeElapsed;

public class Starter {

	private static final int INTERVALL = 100;

	public static void main(String[] args) {
//		System.out.println("Hello there, I am FooMuckl!");
		
		FooMuckl fooMuckl = new FooMuckl(new Environment());
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
