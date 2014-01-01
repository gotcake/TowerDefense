package com.majikarpets.ui.styles.types;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public interface PropertyType {
	
	public Class<?> getMostGenericType();
	
	public Object parseValue(String prop) throws InvalidPropertyValueException;
	
	public boolean verifyValue(Object val);
	
	public String stringifyValue(Object val);
	
	public boolean isInterpolatable();
	
	public Interpolator getInterpolator(Object start, Object end, Property prop, Component context);

}
