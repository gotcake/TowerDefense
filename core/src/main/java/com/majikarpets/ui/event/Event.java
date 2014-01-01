package com.majikarpets.ui.event;

import com.majikarpets.ui.Component;

/**
 * Encapsulates a basic event with the minimal amount of data
 * @author Aaron Cake
 */
public class Event {
	
	/** The type or name of the event */
	public final String type;
	/** The time the event was created */
	public final long time;
	/** The source component of the event */
	public final Component source;
	
	private boolean canceled;
	
	/**
	 * Creates a new event of the the given type with the given source component
	 * @param type the event type
	 * @param source the source component
	 */
	public Event(String type, Component source) {
		this.type = type;
		this.source = source;
		time = System.currentTimeMillis();
		canceled = false;
	}
	
	/**
	 * Stops this event from being propogated to any more listeners.
	 * Does not actually revert or stop the event from happening.
	 */
	public void cancel() {
		canceled = true;
	}
	
	/**
	 * Gets whether or not the event has been canceled. No need to check this as it is handled within the event bus.
	 * @return true if the event has been canceled
	 */
	public boolean isCanceled() {
		return canceled;
	}

}
