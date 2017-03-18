package de.rkable.foomuckl.core.event;

import java.util.List;

public interface EventHandler {

	public void handle(List<Event> events);
	
	public void handle(Event event);
	
	

}
