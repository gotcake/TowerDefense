package com.majikarpets.ui.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.majikarpets.td.GameRuntimeException;
import com.majikarpets.ui.styles.Property;

public class PropertyChangeProxy implements PropertyChangeListener {
	
	private Object target;
	private Method method;
	
	public PropertyChangeProxy(Property prop, Object target, Method method) {
		this.target = target;
		this.method = method;
		Class<?>[] params = method.getParameterTypes();
		if (params.length != 2 || !Property.class.equals(params[0]))
			throw new GameRuntimeException(method.getName() + ": Proxied style change listeners must recieve exactly two parameters: a Property, and a value");
		if (!params[1].isAssignableFrom(prop.type.getMostGenericType())) {
			throw new GameRuntimeException(method.getName() + ": Second parameter not assignable from " + prop.type.getMostGenericType().getCanonicalName());
		}
	}

	@Override
	public void propertyChanged(Property prop, Object value) {
		try {
			method.invoke(target, prop, value);
		} catch (IllegalAccessException e1) {
			throw new GameRuntimeException(e1);
		} catch (IllegalArgumentException e1) {
			throw new GameRuntimeException(e1);
		} catch (InvocationTargetException e1) {
			throw new GameRuntimeException(e1);
		}
	}

}
