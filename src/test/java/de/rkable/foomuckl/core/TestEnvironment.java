package de.rkable.foomuckl.core;

import java.util.ArrayList;
import java.util.List;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.judgment.JudgmentCombinator;

/**
 * Simply collects all the outputs
 * @author Mark
 *
 */
public class TestEnvironment extends Environment {

	public TestEnvironment(JudgmentCombinator judgmentCombinator) {
		super(judgmentCombinator);
	}

	public List<Action> outputs = new ArrayList<>();
	

	@Override
	public void reactOnActions(List<Action> outputs) {
		this.outputs.addAll(outputs);
	}
}
