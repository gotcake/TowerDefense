package com.majikarpets.ui.styles.types;

import java.util.regex.Pattern;

import com.badlogic.gdx.graphics.Color;
import com.majikarpets.ui.Component;
import com.majikarpets.ui.styles.Interpolator;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;

public class ColorPropertyType implements PropertyType {
	
	public static Color parseColor(String str) {
		if (hexPattern.matcher(str).matches()) {
			int r, g, b, a;
			try {
				String rstr = str.substring(1, 3),
						gstr = str.substring(3, 5),
						bstr = str.substring(5, 7);
				r = Integer.parseInt(rstr, 16);
				g = Integer.parseInt(gstr, 16);
				b = Integer.parseInt(bstr, 16);
				if (str.length() == 9) {
					a = Integer.parseInt(str.substring(7, 9), 16);
					return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
				} else {
					return new Color(r / 255f, g / 255f, b / 255f, 1);
				}
			} catch (NumberFormatException ex) {
				return null;
			}
		} else if (rgbaPattern.matcher(str).matches()) {
			String[] parts = str.substring(5, str.length() - 1).trim().split("\\s*,\\s*");
			if (parts.length != 4)
				return null;
			float r, g, b, a;
			try {
				r = Float.parseFloat(parts[0]);
				g = Float.parseFloat(parts[1]);
				b = Float.parseFloat(parts[2]);
				a = Float.parseFloat(parts[3]);
			} catch (NumberFormatException ex) {
				return null;
			}
			return new Color(r, g, b, a);
		} else if (rgbPattern.matcher(str).matches()) {
			String[] parts = str.substring(4, str.length() - 1).trim().split("\\s*,\\s*");
			if (parts.length != 3)
				return null;
			float r, g, b;
			try {
				r = Float.parseFloat(parts[0]);
				g = Float.parseFloat(parts[1]);
				b = Float.parseFloat(parts[2]);
			} catch (NumberFormatException ex) {
				return null;
			}
			return new Color(r, g, b, 1);
		} else {
			return null;
		}
	}
	
	private static final Pattern hexPattern = Pattern.compile("#[0-9A-Fa-f]{6}(([0-9A-Fa-f]){2})?");
	private static final Pattern rgbaPattern = Pattern.compile("rgba\\(\\s*[0-9]*?\\.?[0-9]+\\s*(,\\s*[0-9]*?\\.?[0-9]+\\s*){3}\\)");
	private static final Pattern rgbPattern = Pattern.compile("rgb\\(\\s*[0-9]*?\\.?[0-9]+\\s*(,\\s*[0-9]*?\\.?[0-9]+\\s*){2}\\)");
	
	private final boolean allowInherit;
	
	public ColorPropertyType(boolean allowInherit) {
		this.allowInherit = allowInherit;
	}
	
	@Override
	public Object parseValue(String prop) throws InvalidPropertyValueException {
		Color c = parseColor(prop);
		if (c == null)
			throw new InvalidPropertyValueException("Invalid color value: '" + prop + "'");
		return c;
		
	}

	@Override
	public boolean verifyValue(Object val) {
		return (allowInherit && val == null) || (val != null && val instanceof Color);
	}

	@Override
	public String stringifyValue(Object val) {
		if (val == null)
			return "inherit";
		Color c  = (Color)val;
		return "rgba( " + c.r + ", " + c.g + ", " + c.b + ", " + c.a + " )";
	}

	@Override
	public Class<?> getMostGenericType() {
		return Color.class;
	}

	@Override
	public boolean isInterpolatable() {
		return true;
	}

	@Override
	public Interpolator getInterpolator(Object start, Object end, Property prop, Component context) {
		return new ColorInterpolator((Color)start, (Color)end);
	}
	
	static class ColorInterpolator implements Interpolator {
		
		private Color start, end;
		
		public ColorInterpolator(Color start, Color end) {
			this.start = start;
			this.end = end;
		}

		@Override
		public Object getValueAt(float t) {
			return start.lerp(end, t);
		}
		
	}

}
