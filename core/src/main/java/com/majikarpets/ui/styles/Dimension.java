package com.majikarpets.ui.styles;

public class Dimension {
	
	public static final Dimension ZERO = new Dimension(0, false);
	
	/** whether or not the value is to be considered per-unit */
	public final boolean isPerUnit;
	/** the value */
	public final float val;
	
	public Dimension(float val, boolean isPerUnit) {
		this.val = val;
		this.isPerUnit = isPerUnit;
	}
	
	@Override
	public String toString() {
		if (isPerUnit) {
			return + Math.round(val * 100) + "%";
		} else {
			return "" + val + "u";
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isPerUnit ? 1231 : 1237);
		result = prime * result + Float.floatToIntBits(val);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || obj.getClass() != getClass())
			return false;
		Dimension other = (Dimension) obj;
		return isPerUnit == other.isPerUnit && val == other.val;
	}
	
}
