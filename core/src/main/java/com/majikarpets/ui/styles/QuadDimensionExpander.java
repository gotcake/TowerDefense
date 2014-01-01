package com.majikarpets.ui.styles;

import java.util.EnumMap;

import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class QuadDimensionExpander extends PropertyDependancy {
	
	public static final int A = 1, B = 2, C = 3, D = 4;
	
	private final int part;
	
	public QuadDimensionExpander(Property quadProp, int part) {
		super(new Property[] {quadProp});
		this.part = part;
	}

	@Override
	public void handleDependancyChange(PropertySet ps, Property thisProp, EnumMap<Property, Object> pending) {
		QuadDimension d = (QuadDimension)getProp(dependancies[0], ps, pending);
		try {
			if (part == A) {
				ps.setPropertyRaw(thisProp, d.a);
			} else if (part == B) {
				ps.setPropertyRaw(thisProp, d.b);
			} else if (part == C) {
				ps.setPropertyRaw(thisProp, d.c);
			} else {
				ps.setPropertyRaw(thisProp, d.d);
			}
		} catch (InvalidPropertyValueException e) {
			throw new RuntimeException(e);
		}
	}
	
}
