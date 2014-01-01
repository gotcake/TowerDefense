package com.majikarpets.ui.styles;

import com.majikarpets.ui.styles.types.PropertyType;

public class PropertyValue {
	
	public final Property property;
	public final Object value;
	
	public PropertyValue(Property prop, Object val) {
		property = prop;
		value = val;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append(property.name);
		b.append(": ");
		b.append(property.type.stringifyValue(value));
		b.append(";");
		return b.toString();
	}

}
