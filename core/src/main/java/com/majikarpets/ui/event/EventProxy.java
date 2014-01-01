package com.majikarpets.ui.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.majikarpets.td.GameRuntimeException;


public class EventProxy implements EventListener {
	
	private Object target;
	private Method method;
	private Class<?> paramType;
	
	public EventProxy(Object target, Method method) {
		this.target = target;
		this.method = method;
		Class<?>[] params = method.getParameterTypes();
		if (params.length != 1 || !Event.class.isAssignableFrom(params[0]))
			throw new GameRuntimeException("Proxied event methods must recieve exactly one parameter of Event of a sublcass of Event");
		paramType = params[0];
	}

	@Override
	public void handleEvent(Event e) {
		if (paramType.isInstance(e)) {
			try {
				method.invoke(target, e);
			} catch (IllegalAccessException e1) {
				throw new GameRuntimeException(e1);
			} catch (IllegalArgumentException e1) {
				throw new GameRuntimeException(e1);
			} catch (InvocationTargetException e1) {
				throw new GameRuntimeException(e1);
			}
		}
	}
	
}
