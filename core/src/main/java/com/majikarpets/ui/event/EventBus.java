package com.majikarpets.ui.event;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.majikarpets.ui.styles.Property;


public class EventBus {
	
	private final HashMap<String, EventDelegate> events;
	private List<Event> eventsOnHold;
	private boolean onHold, suspended;
	private Set<String> suspendedEvents;
	
	public EventBus() {
		events = new HashMap<String, EventDelegate>();
		eventsOnHold = new ArrayList<Event>();
		suspendedEvents = new HashSet<String>();
		onHold = false;
		suspended = false;
	}
	
	public void addProxiedHandler(Object obj) {
		addProxy(obj, obj.getClass());
	}
	
	public void setOnHold(boolean onHold) {
		if (this.onHold && !onHold) {
			this.onHold = false;
			List<Event> hold = eventsOnHold;
			eventsOnHold = new ArrayList<Event>();
			for (Event e: hold) {
				fireEvent(e);
			}
		} else if (!this.onHold && onHold){
			this.onHold = true;
		}
	}
	
	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}
	
	public void setSuspended(String eventType, boolean suspended) {
		if (suspended) {
			suspendedEvents.add(eventType);
		} else {
			suspendedEvents.remove(eventType);
		}
	}
	
	private void addProxy(Object obj, Class<?> cls) {
		Class<?> supr = cls.getSuperclass();
		if (supr != Object.class)
			addProxy(obj, supr);
		for (Method method: cls.getDeclaredMethods()) {
			method.setAccessible(true);
			EventHandler eh = method.getAnnotation(EventHandler.class);
			if (eh != null) {
				for (String eventType: eh.value()) {
					addListener(eventType, new EventProxy(obj, method));
				}
			}
		}
	}
	
	public boolean addListener(String eventName, EventListener el) {
		EventDelegate ed = events.get(eventName);
		if (ed == null) {
			ed = new EventDelegate();
			events.put(eventName, ed);
		}
		return ed.addListener(el);
	}
	
	public boolean removeListener(String eventName, EventListener el) {
		EventDelegate ed = events.get(eventName);
		if (ed != null)
			return ed.removeListener(el);
		else
			return false;
	}
	
	public boolean removeListener(EventListener el) {
		boolean removed = false;
		for (Entry<String, EventDelegate> entry: events.entrySet()) {
			removed |= entry.getValue().removeListener(el);
		}
		return removed;
	}
	
	public void fireEvent(Event e) {
		if (suspended || suspendedEvents.contains(e.type)) {
			return; // ignore event
		} else if (onHold) {
			eventsOnHold.add(e); // add to the list of held events
		} else {
			EventDelegate ed = events.get(e.type);
			if (ed != null)
				ed.fire(e);
		}
	}
	
	private static class EventDelegate {
		
		private final List<EventListener> listeners;
		
		public EventDelegate() {
			listeners = new ArrayList<EventListener>();
		}
		
		public void fire(Event e) {
			for (EventListener el: listeners) {
				if (e.isCanceled())
					break;
				el.handleEvent(e);
			}
		}
		
		public boolean addListener(EventListener el) {
			if (!listeners.contains(el)) {
				listeners.add(el);
				return true;
			} else {
				return false;
			}
		}
		
		public boolean removeListener(EventListener el) {
			return listeners.remove(el);
		}
		
	}

}
