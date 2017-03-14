package de.rkable.foomuckl.core;

import java.util.Arrays;
import java.util.Map.Entry;

import com.google.inject.Inject;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.event.ComeToLife;
import de.rkable.foomuckl.core.event.TimeElapsed;

/**
 * Lifecycle of FooMuckl; 1:1 relationship
 * 
 * @author Mark
 *
 */
public class Lifecycle {
	
	private FooMuckl fooMuckl;
	private Environment environment;
	
	private long lastUpdateTime;
	
	@Inject
	public Lifecycle(FooMuckl fooMuckl, Environment environment) {
		this.fooMuckl = fooMuckl;
		this.environment = environment;
		
		fooMuckl.addInput(new ComeToLife());
		lastUpdateTime = System.currentTimeMillis();
	}
	


	public void executeEventLoop() {
		long currentTime = System.currentTimeMillis();
		long timeElapsed = currentTime - lastUpdateTime;
		lastUpdateTime = currentTime;
		
		// add inputs
		TimeElapsed timeElapsedEvent = new TimeElapsed(timeElapsed);
		environment.reactOnEvents(Arrays.asList(timeElapsedEvent));
		fooMuckl.addInput(timeElapsedEvent);
		
		// evaluate and process the options
		Entry<Action, Judgment> evaluateOptions = fooMuckl.chooseOptions();
		environment.reactOnActions(Arrays.asList(evaluateOptions.getKey()));
		fooMuckl.applyJudgment(evaluateOptions.getValue());
	}
}
