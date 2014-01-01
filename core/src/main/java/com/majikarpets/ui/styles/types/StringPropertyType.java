package com.majikarpets.ui.styles.types;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class StringPropertyType implements PropertyType {

	@Override
	public Object parseValue(String prop) throws InvalidPropertyValueException {
		return prop;
	}

	@Override
	public boolean verifyValue(Object val) {
		return val instanceof String;
	}

	@Override
	public String stringifyValue(Object val) {
		return val.toString();
	}

	@Override
	public Class<?> getMostGenericType() {
		return String.class;
	}

	@Override
	public boolean isInterpolatable() {
		return false;
	}

	@Override
	public Interpolator getInterpolator(Object start, Object end, Property prop, Component context) {
		throw new RuntimeException("String property types cannot be interpolated.");
	}
	
}
