package com.majikarpets.ui.styles;

public class QuadDimension {
	
	public static final QuadDimension ZERO = new QuadDimension(Dimension.ZERO, Dimension.ZERO, Dimension.ZERO, Dimension.ZERO);
	public static final QuadDimension FULL_BOX = new QuadDimension(new Dimension(0, true), new Dimension(1, true), new Dimension(1, true), new Dimension(0, true));
	
	/** top or top-left */
	public final Dimension a;
	/** right or top-right */
	public final Dimension b;
	/** bottom or bottom-right */
	public final Dimension c;
	/** left or bottom-left **/
	public final Dimension d;
	
	public QuadDimension(Dimension a, Dimension b, Dimension c, Dimension d) {
		if (a == null || b == null || c == null || d == null)
			throw new NullPointerException("No Dimension argument may be null.");
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		result = prime * result + ((c == null) ? 0 : c.hashCode());
		result = prime * result + ((d == null) ? 0 : d.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		QuadDimension other = (QuadDimension) obj;
		return other.a.equals(a) && other.b.equals(b) && other.c.equals(c) && other.d.equals(d);
	}

	@Override
	public String toString() {
		return a.toString() + " " + b.toString() + " " + c.toString() + " " + d.toString();
	}
	
	
}
