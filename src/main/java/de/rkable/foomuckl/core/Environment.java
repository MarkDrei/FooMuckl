package de.rkable.foomuckl.core;

import java.util.List;

import de.rkable.foomuckl.core.output.Output;
import de.rkable.foomuckl.core.output.SaySomething;

/**
 * The environment that can be influenced by FooMuckl
 * 
 * @author Mark
 *
 */
public class Environment {

	public void reactOn(List<Output> outputs) {
		for(Output output : outputs) {
			if (output instanceof SaySomething) {
				System.out.println("FooMuckl says: \"" + ((SaySomething) output).getSpeech() + "\"");
			}
		}
	}
}
