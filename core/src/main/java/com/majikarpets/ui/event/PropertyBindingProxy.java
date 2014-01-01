package com.majikarpets.ui.event;

import java.lang.reflect.Field;

import com.majikarpets.td.GameRuntimeException;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.types.PropertyType;

public class PropertyBindingProxy implements PropertyChangeListener {
	
	private final Object target;
	private final Field field;
	
	public PropertyBindingProxy(Property p, Object target, Field field) {
		if (!field.getType().isAssignableFrom(p.type.getMostGenericType()))
			throw new GameRuntimeException("Field " + field.getName() + " is not assignable from type " + p.type.getMostGenericType().getCanonicalName());
		this.target = target;
		this.field = field;
	}

	@Override
	public void propertyChanged(Property prop, Object value) {
		try {
			field.set(target, value);
		} catch (IllegalArgumentException e) {
			throw new GameRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new GameRuntimeException(e);
		}
	}
	
}
