package com.majikarpets.ui.styles;

import java.util.EnumMap;

import com.majikarpets.td.GameRuntimeException;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class QuadDimensionCoalescer extends PropertyDependancy {

	public QuadDimensionCoalescer(Property a, Property b, Property c, Property d) {
		super(new Property[] {a, b, c, d});
	}

	@Override
	public void handleDependancyChange(PropertySet ps, Property thisProp, EnumMap<Property, Object> pending) {
		
		Dimension a = (Dimension)getProp(dependancies[0], ps, pending);
		Dimension b = (Dimension)getProp(dependancies[1], ps, pending);
		Dimension c = (Dimension)getProp(dependancies[2], ps, pending);
		Dimension d = (Dimension)getProp(dependancies[3], ps, pending);
		
		try {
			ps.setPropertyRaw(thisProp, new QuadDimension(a, b, c, d));
		} catch (InvalidPropertyValueException e) {
			throw new GameRuntimeException("This should not happen!", e);
		}
		
	}

	

}
