package de.rkable.foomuckl.ui.action.handler;

import java.util.HashSet;
import java.util.Set;

import de.rkable.foomuckl.core.action.Action;
import de.rkable.foomuckl.core.action.SaySomething;
import de.rkable.foomuckl.core.action.handler.SpeechHandler;

public class SpeechDisplay extends SpeechHandler {

	private Set<SpeechChangedListener> listeners = new HashSet<>();

	private String lastThingsSaid = "";

	@Override
	public void handle(Action action) {
		if (action instanceof SaySomething) {
			lastThingsSaid = lastThingsSaid + "\n" + ((SaySomething) action).getSpeech();

			for (SpeechChangedListener l : listeners) {
				l.speechChanged(lastThingsSaid);
			}
		}
	}


	@FunctionalInterface
	public interface SpeechChangedListener {
		public void speechChanged(String speech);
	}

	public void addListener(SpeechChangedListener listener) {
		listeners.add(listener);
		listener.speechChanged(lastThingsSaid);
	}


}
