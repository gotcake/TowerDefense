package com.majikarpets.ui.styles.types;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.Dimension;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Orientation;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.QuadDimension;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class QuadDimensionPropertyType implements PropertyType {
	
	public static final int TYPE_BOX = 1, TYPE_UNDIRECTIONAL = 2;
	
	private static final DimensionPropertyType dimParser = new DimensionPropertyType(null, false);
	
	private final int type;
	
	public QuadDimensionPropertyType(int type) {
		this.type = type;
	}

	@Override
	public Object parseValue(String prop) throws InvalidPropertyValueException {
		String[] parts = prop.split("\\s+");
		if (parts.length != 4)
			throw new InvalidPropertyValueException("Invalid quad dimensional value: '" + prop + "'. Expected four individual values.");
		Dimension a = (Dimension)dimParser.parseValue(parts[0]);
		Dimension b = (Dimension)dimParser.parseValue(parts[1]);
		Dimension c = (Dimension)dimParser.parseValue(parts[2]);
		Dimension d = (Dimension)dimParser.parseValue(parts[3]);
		return new QuadDimension(a, b, c, d);
	}

	@Override
	public boolean verifyValue(Object val) {
		return val != null && val instanceof QuadDimension;
	}

	@Override
	public String stringifyValue(Object val) {
		return val.toString();
	}

	@Override
	public Class<?> getMostGenericType() {
		return QuadDimension.class;
	}

	@Override
	public boolean isInterpolatable() {
		return true;
	}

	@Override
	public Interpolator getInterpolator(Object start, Object end, Property prop, Component context) {
		QuadDimension sd = (QuadDimension)start, ed = (QuadDimension)end;
		float startA, startB, startC, startD, endA, endB, endC, endD;
		boolean isPerUnitA, isPerUnitB, isPerUnitC, isPerUnitD;
		
		if (!sd.a.isPerUnit && ed.a.isPerUnit) {
			startA = sd.a.val;
			endA = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Vertical : Orientation.Unoriented, ed.a.val);
			isPerUnitA = false;
		} else if (sd.a.isPerUnit && !ed.a.isPerUnit) {
			startA = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Vertical : Orientation.Unoriented, sd.a.val);
			endA = ed.a.val;
			isPerUnitA = false;
		} else {
			startA = sd.a.val;
			endA = ed.a.val;
			isPerUnitA = sd.a.isPerUnit;
		}
		
		if (!sd.b.isPerUnit && ed.b.isPerUnit) {
			startB = sd.b.val;
			endB = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Horizontal : Orientation.Unoriented, ed.b.val);
			isPerUnitB = false;
		} else if (sd.b.isPerUnit && !ed.b.isPerUnit) {
			startB = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Horizontal : Orientation.Unoriented, sd.b.val);
			endB = ed.b.val;
			isPerUnitB = false;
		} else {
			startB = sd.b.val;
			endB = ed.b.val;
			isPerUnitB = sd.b.isPerUnit;
		}
		
		if (!sd.c.isPerUnit && ed.c.isPerUnit) {
			startC = sd.c.val;
			endC = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Vertical : Orientation.Unoriented, ed.c.val);
			isPerUnitC = false;
		} else if (sd.c.isPerUnit && !ed.c.isPerUnit) {
			startC = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Vertical : Orientation.Unoriented, sd.c.val);
			endC = ed.c.val;
			isPerUnitC = false;
		} else {
			startC = sd.c.val;
			endC = ed.c.val;
			isPerUnitC = sd.c.isPerUnit;
		}
		
		if (!sd.d.isPerUnit && ed.d.isPerUnit) {
			startD = sd.d.val;
			endD = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Horizontal : Orientation.Unoriented, ed.d.val);
			isPerUnitD = false;
		} else if (sd.d.isPerUnit && !ed.d.isPerUnit) {
			startD = context.resolvePerUnitDimension(type == TYPE_BOX ? Orientation.Horizontal : Orientation.Unoriented, sd.d.val);
			endD = ed.d.val;
			isPerUnitD = false;
		} else {
			startD = sd.d.val;
			endD = ed.d.val;
			isPerUnitD = sd.d.isPerUnit;
		}
		
		return new QuadQimensionInterpolator(startA, endA, isPerUnitA, startB, endB, isPerUnitB,
				startC, endC, isPerUnitC, startD, endD, isPerUnitD);
	}
	
	public static class QuadQimensionInterpolator implements Interpolator {
		
		private final float startA, startB, startC, startD;
		private final float endA, endB, endC, endD;
		private final boolean isPerUnitA, isPerUnitB, isPerUnitC, isPerUnitD;
		
		public QuadQimensionInterpolator(float startA, float endA, boolean isPerUnitA,
				float startB, float endB, boolean isPerUnitB,
				float startC, float endC, boolean isPerUnitC,
				float startD, float endD, boolean isPerUnitD) {
			this.startA = startA;
			this.endA = endA;
			this.isPerUnitA = isPerUnitA;
			this.startB = startB;
			this.endB = endB;
			this.isPerUnitB = isPerUnitB;
			this.startC = startC;
			this.endC = endC;
			this.isPerUnitC = isPerUnitC;
			this.startD = startD;
			this.endD = endD;
			this.isPerUnitD = isPerUnitD;
		}

		@Override
		public Object getValueAt(float t) {
			return new QuadDimension(
					new Dimension((endA - startA) * t + startA, isPerUnitA),
					new Dimension((endB - startB) * t + startB, isPerUnitB),
					new Dimension((endC - startC) * t + startC, isPerUnitC),
					new Dimension((endD - startD) * t + startD, isPerUnitD)
					);
		}
		
	}

}
