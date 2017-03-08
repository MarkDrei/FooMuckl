package de.rkable.foomuckl.core;

import java.util.ArrayList;
import java.util.List;

import de.rkable.foomuckl.core.output.Output;

/**
 * Simply collects all the outputs
 * @author Mark
 *
 */
public class TestEnvironment extends Environment {

	public List<Output> outputs = new ArrayList<>();
	
	@Override
	public void reactOn(List<Output> outputs) {
		this.outputs.addAll(outputs);
	}
}
