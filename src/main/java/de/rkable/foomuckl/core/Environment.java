package de.rkable.foomuckl.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.handler.ActionHandler;
import de.rkable.foomuckl.core.action.handler.SpeechHandler;
import de.rkable.foomuckl.core.action.judgment.Judgment;
import de.rkable.foomuckl.core.action.judgment.JudgmentCombinator;
import de.rkable.foomuckl.core.event.Event;
import de.rkable.foomuckl.core.event.EventHandler;

/**
 * The environment that can be influenced by FooMuckl
 * 
 * @author Mark
 *
 */
public class Environment {

	private List<ActionHandler> actionHandlers = new ArrayList<>();
	private List<EventHandler> eventHandlers = new ArrayList<>();
	private JudgmentCombinator judgmentCombinator;
	
	public Environment(JudgmentCombinator judgmentCombinator) {
		this.judgmentCombinator = judgmentCombinator;
	}

	public void reactOnActions(List<Action> actions) {
		for (ActionHandler handler : actionHandlers) {
			handler.handle(actions);
		}
	}

	/**
	 * React on inputs from the "outside"
	 * @param events
	 */
	public void reactOnEvents(List<Event> events) {
		for (EventHandler handler : eventHandlers) {
			handler.handle(events);
		}
	}

	public Map<Action, Judgment> judge(Set<Action> actions) {
		Map<Action, Judgment> judgments = new HashMap<>();
		
		for (Action action : actions) {
			for (ActionHandler handler : actionHandlers) {
				Judgment result = handler.judge(action);
				judgmentCombinator.combine(result, judgments.get(action));
				judgments.put(action, result);
			}
		}
		return judgments;
	}

	public void addActionHandler(SpeechHandler handler) {
		actionHandlers.add(handler);
		
	}
}
