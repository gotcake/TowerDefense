package com.majikarpets.ui.styles.types;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.FontStyle;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class FontStylePropertyType implements PropertyType {

	@Override
	public Object parseValue(String prop) throws InvalidPropertyValueException {
		if (prop.equals("inherit"))
			return null;
		FontStyle f = FontStyle.getByName(prop);
		if (f == null)
			throw new InvalidPropertyValueException("Invalid font style value: '" + prop + "'");
		return f;
	}

	@Override
	public boolean verifyValue(Object val) {
		return val == null || val instanceof FontStyle;
	}

	@Override
	public String stringifyValue(Object val) {
		if (val == null)
			return "inherit";
		return ((FontStyle)val).name;
	}

	@Override
	public Class<?> getMostGenericType() {
		return FontStyle.class;
	}

	@Override
	public boolean isInterpolatable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Interpolator getInterpolator(Object start, Object end,
			Property prop, Component context) {
		// TODO Auto-generated method stub
		return null;
	}

}
