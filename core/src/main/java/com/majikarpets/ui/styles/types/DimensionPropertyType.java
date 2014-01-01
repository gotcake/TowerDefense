package com.majikarpets.ui.styles.types;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.Dimension;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Orientation;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;


public class DimensionPropertyType implements PropertyType {
	
	private final boolean allowAuto;
	private final Orientation orientation;
	
	public DimensionPropertyType(Orientation o, boolean allowAuto) {
		this.orientation = o;
		this.allowAuto = allowAuto;
	}
	
	
	@Override
	public Object parseValue(String prop) throws InvalidPropertyValueException {
		
		if (prop.equals("auto")) {
			if (allowAuto)
				return null;
			else 
				throw new InvalidPropertyValueException("Auto value not allowed for this property.");
		}
		
		String orig = prop;
		
		boolean isPercent = false;
		
		if (prop.endsWith("%")) {
			isPercent = true;
			prop = prop.substring(0, prop.length() - 1);
		} else if (prop.endsWith("u")) {
			prop = prop.substring(0, prop.length() - 1);
		} else {
			throw new InvalidPropertyValueException("Invalid dimensional value: '" + orig + "'. Must end in '%' or 'u'.");
		}
		
		try {
			
			if (isPercent) {
				int val = Integer.parseInt(prop);
				return new Dimension(val / 100f, true);
			} else {
				float val = Float.parseFloat(prop);
				return new Dimension(val, false);
			}
			
		} catch (NumberFormatException e) {
			
			throw new InvalidPropertyValueException("Invalid dimensional value: '" + orig + "'");
			
		}
	}

	@Override
	public boolean verifyValue(Object val) {
		return (allowAuto && val == null) || (val != null && val instanceof Dimension);
	}

	@Override
	public String stringifyValue(Object val) {
		if (val == null)
			return "auto";
		return val.toString();
	}

	@Override
	public Class<?> getMostGenericType() {
		return Dimension.class;
	}

	@Override
	public boolean isInterpolatable() {
		return true;
	}

	@Override
	public Interpolator getInterpolator(Object start, Object end, Property prop, Component context) {
		Dimension ds = (Dimension)start;
		Dimension de = (Dimension)end;
		if (ds.isPerUnit && !de.isPerUnit) {
			float s = context.resolvePerUnitDimension(orientation, ds.val);
			return new DimensionInterpolator(s, de.val, false);
		} else if (!ds.isPerUnit && de.isPerUnit) {
			float e = context.resolvePerUnitDimension(orientation, de.val);
			return new DimensionInterpolator(ds.val, e, false);
		} else {
			return new DimensionInterpolator(ds.val, de.val, ds.isPerUnit);
		}
	}
	
	public static class DimensionInterpolator implements Interpolator {
		
		private boolean perUnit;
		private float start, end;
		
		public DimensionInterpolator(float start, float end, boolean perUnit) {
			this.start = start;
			this.end = end;
			this.perUnit = perUnit;
		}

		@Override
		public Object getValueAt(float t) {
			return new Dimension((end - start) * t + start, perUnit);
		}
		
	}
	
}
