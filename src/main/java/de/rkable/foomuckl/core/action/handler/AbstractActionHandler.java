package de.rkable.foomuckl.core.action.handler;

import java.util.List;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.action.judgment.NoConsequence;

public abstract class AbstractActionHandler implements ActionHandler {

	@Override
	public void handle(List<Action> actions) {
		for (Action action : actions) {
			handle(action);
		}
	}

	@Override
	public Judgment judge(Action action) {
		return new NoConsequence();
	}

}
