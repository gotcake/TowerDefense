package com.majikarpets.ui.styles;

import java.util.EnumMap;
import static com.majikarpets.ui.styles.Property.*;

public abstract class PropertyDependancy {
	
	private static EnumMap<Property, PropertyDependancy> dependancyMap = new EnumMap<Property, PropertyDependancy>(Property.class);
	
	public static PropertyDependancy get(Property p) {
		return dependancyMap.get(p);
	}
	
	public static  void add(Property p, PropertyDependancy d) {
		dependancyMap.put(p, d);
	}
	
	static {
		
		// padding
		add(Padding, new QuadDimensionCoalescer(PaddingTop, PaddingRight, PaddingBottom, PaddingLeft));
		add(PaddingTop, new QuadDimensionExpander(Padding, QuadDimensionExpander.A));
		add(PaddingRight, new QuadDimensionExpander(Padding, QuadDimensionExpander.B));
		add(PaddingBottom, new QuadDimensionExpander(Padding, QuadDimensionExpander.C));
		add(PaddingTop, new QuadDimensionExpander(Padding, QuadDimensionExpander.D));
		
		// border-width
		add(BorderWidth, new QuadDimensionCoalescer(BorderWidthTop, BorderWidthRight, BorderWidthBottom, BorderWidthLeft));
		add(BorderWidthTop, new QuadDimensionExpander(BorderWidth, QuadDimensionExpander.A));
		add(BorderWidthRight, new QuadDimensionExpander(BorderWidth, QuadDimensionExpander.B));
		add(BorderWidthBottom, new QuadDimensionExpander(BorderWidth, QuadDimensionExpander.C));
		add(BorderWidthTop, new QuadDimensionExpander(BorderWidth, QuadDimensionExpander.D));
		
		// margin
		add(Margin, new QuadDimensionCoalescer(MarginTop, MarginRight, MarginBottom, MarginLeft));
		add(MarginTop, new QuadDimensionExpander(Margin, QuadDimensionExpander.A));
		add(MarginRight, new QuadDimensionExpander(Margin, QuadDimensionExpander.B));
		add(MarginBottom, new QuadDimensionExpander(Margin, QuadDimensionExpander.C));
		add(MarginTop, new QuadDimensionExpander(Margin, QuadDimensionExpander.D));
		
		// corner-radius
		add(CornerRadius, new QuadDimensionCoalescer(CornerRadiusTopLeft, CornerRadiusTopRight, CornerRadiusBottomRight, CornerRadiusBottomLeft));
		add(CornerRadiusTopLeft, new QuadDimensionExpander(CornerRadius, QuadDimensionExpander.A));
		add(CornerRadiusTopRight, new QuadDimensionExpander(CornerRadius, QuadDimensionExpander.B));
		add(CornerRadiusBottomRight, new QuadDimensionExpander(CornerRadius, QuadDimensionExpander.C));
		add(CornerRadiusBottomLeft, new QuadDimensionExpander(CornerRadius, QuadDimensionExpander.D));
	}
	
	public final Property[] dependancies;
	
	public PropertyDependancy(Property[] dependancies) {
		this.dependancies = dependancies;
	}
	
	protected static Object getProp(Property p, PropertySet ps, EnumMap<Property, Object> pending) {
		Object o = pending.get(p);
		if (o == null)
			o = ps.getProperty(p);
		return o;
	}
	
	public abstract void handleDependancyChange(PropertySet ps, Property thisProp, EnumMap<Property, Object> pending);
}
