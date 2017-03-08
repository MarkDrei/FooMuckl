package de.rkable.foomuckl.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.rkable.foomuckl.core.input.ComeToLife;
import de.rkable.foomuckl.core.input.Input;
import de.rkable.foomuckl.core.input.TimeElapsed;
import de.rkable.foomuckl.core.output.DoNothing;
import de.rkable.foomuckl.core.output.Output;
import de.rkable.foomuckl.core.output.SaySomething;

/**
 * FooMuckl is an automaton. It collects inputs, evaluates its options and
 * then can trigger actions
 * 
 * @author Mark
 *
 */
public class FooMuckl {
	
	private final static int GETTING_BORED_MS = 5000;
	
	private List<Input> inputs = new ArrayList<>();

	private Environment environment;
	
	public FooMuckl(Environment environment) {
		this.environment = environment;
	}
	
	public void addInput(Input input) {
		inputs.add(input);
	}
	
	/**
	 * Evaluates the current options based on the received inputs
	 */
	public void evaluateOptions() {
		
		List<Output> outputs = new ArrayList<>();
		
		List<Input> inputsToProcess = new ArrayList<>(inputs);
		int elapsedTime = 0;
		for (Input input : inputsToProcess) {
			if (input instanceof ComeToLife) {
				outputs.add(new SaySomething("Oh I was born!"));
				inputs.remove(input);
			} else if (input instanceof TimeElapsed) {
				elapsedTime += ((TimeElapsed) input).getElapsedMillis();
				inputs.remove(input);
			}
		}
		
		if (elapsedTime > GETTING_BORED_MS) {
			outputs.add(new SaySomething("I am bored!"));
		} else {
			inputs.add(new TimeElapsed(elapsedTime));
		}
		
		if (outputs.isEmpty()) {
			outputs.add(new DoNothing());
		}
		
		environment.reactOn(outputs);
	}

}
