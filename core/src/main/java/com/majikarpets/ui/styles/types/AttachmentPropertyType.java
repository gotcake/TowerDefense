package com.majikarpets.ui.styles.types;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.Attachment;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class AttachmentPropertyType implements PropertyType {

	@Override
	public Object parseValue(String prop) throws InvalidPropertyValueException {
		Attachment a = Attachment.getByName(prop);
		if (a == null)
			throw new InvalidPropertyValueException("Invalid attachment value: '" + prop + "'");
		return a;
	}

	@Override
	public boolean verifyValue(Object val) {
		return val instanceof Attachment;
	}

	@Override
	public String stringifyValue(Object val) {
		return ((Attachment)val).name;
	}

	@Override
	public Class<?> getMostGenericType() {
		return Attachment.class;
	}

	@Override
	public boolean isInterpolatable() {
		return false;
	}

	@Override
	public Interpolator getInterpolator(Object start, Object end, Property prop, Component context) {
		throw new RuntimeException("Attachment type is not interpolatable");
	}

}
