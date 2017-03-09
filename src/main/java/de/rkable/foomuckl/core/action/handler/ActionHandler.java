package de.rkable.foomuckl.core.action.handler;

import java.util.List;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.judgment.Judgment;

public interface ActionHandler {

	void handle(List<Action> actions);
	
	void handle(Action action);
	
	/**
	 * Calculates the potential outcome of an action
	 * 
	 * @param action
	 * @return The judgment of the action 
	 */
	Judgment judge(Action action);

}
