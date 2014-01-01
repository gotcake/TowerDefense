package com.majikarpets.ui.styles;

import java.util.HashMap;

import com.majikarpets.td.GameRuntimeException;
import com.majikarpets.ui.styles.types.AttachmentPropertyType;
import com.majikarpets.ui.styles.types.ColorPropertyType;
import com.majikarpets.ui.styles.types.DimensionPropertyType;
import com.majikarpets.ui.styles.types.FilePropertyType;
import com.majikarpets.ui.styles.types.FontStylePropertyType;
import com.majikarpets.ui.styles.types.PropertyType;
import com.majikarpets.ui.styles.types.QuadDimensionPropertyType;
import com.majikarpets.ui.styles.types.StringPropertyType;

public enum Property {
	
	Width("width", false, new DimensionPropertyType(Orientation.Horizontal, true), null),
	Height("height", false, new DimensionPropertyType(Orientation.Vertical, true), null),
	
	Margin("margin", false, new QuadDimensionPropertyType(QuadDimensionPropertyType.TYPE_BOX), QuadDimension.ZERO),
	MarginTop("margin-top", false, new DimensionPropertyType(Orientation.Vertical, false), Dimension.ZERO),
	MarginRight("margin-right", false, new DimensionPropertyType(Orientation.Horizontal, false), Dimension.ZERO),
	MarginBottom("margin-bottom", false, new DimensionPropertyType(Orientation.Vertical, false), Dimension.ZERO),
	MarginLeft("margin-left", false, new DimensionPropertyType(Orientation.Horizontal, false), Dimension.ZERO),
	
	Padding("padding", false, new QuadDimensionPropertyType(QuadDimensionPropertyType.TYPE_BOX), QuadDimension.ZERO),
	PaddingTop("padding-top", false, new DimensionPropertyType(Orientation.Vertical, false), Dimension.ZERO),
	PaddingRight("padding-right", false, new DimensionPropertyType(Orientation.Horizontal, false), Dimension.ZERO),
	PaddingBottom("padding-bottom", false, new DimensionPropertyType(Orientation.Vertical, false), Dimension.ZERO),
	PaddingLeft("padding-left", false, new DimensionPropertyType(Orientation.Horizontal, false), Dimension.ZERO),
	
	BackgroundImage("background-image", false, new FilePropertyType(), null),
	BackgroundColor("background-color", false, new ColorPropertyType(false), com.badlogic.gdx.graphics.Color.CLEAR),
	BackgroundImageAttachment("background-image-attachment", false, new AttachmentPropertyType(), Attachment.Normal),
	BackgroundImageRegion("background-image-region", false, new QuadDimensionPropertyType(QuadDimensionPropertyType.TYPE_BOX), QuadDimension.FULL_BOX),
	
	CornerRadius("corner-radius", false, new QuadDimensionPropertyType(QuadDimensionPropertyType.TYPE_UNDIRECTIONAL), QuadDimension.ZERO),
	CornerRadiusTopLeft("corner-radius-top-left", false, new DimensionPropertyType(Orientation.Unoriented, false),Dimension.ZERO),
	CornerRadiusTopRight("corner-radius-top-right", false, new DimensionPropertyType(Orientation.Unoriented, false), Dimension.ZERO),
	CornerRadiusBottomRight("corner-radius-bottom-right", false, new DimensionPropertyType(Orientation.Unoriented, false), Dimension.ZERO),
	CornerRadiusBottomLeft("corner-radius-bottom-left", false, new DimensionPropertyType(Orientation.Unoriented, false), Dimension.ZERO),
	
	BorderColor("border-color", false, new ColorPropertyType(false), com.badlogic.gdx.graphics.Color.BLACK),
	BorderWidth("border-width", false, new QuadDimensionPropertyType(QuadDimensionPropertyType.TYPE_BOX), QuadDimension.ZERO),
	BorderWidthTop("border-width-top", false, new DimensionPropertyType(Orientation.Vertical, false), Dimension.ZERO),
	BorderWidthRight("border-width-right", false, new DimensionPropertyType(Orientation.Horizontal, false), Dimension.ZERO),
	BorderWidthBottom("border-width-bottom", false, new DimensionPropertyType(Orientation.Vertical, false), Dimension.ZERO),
	BorderWidthLeft("border-width-left", false, new DimensionPropertyType(Orientation.Horizontal, false), Dimension.ZERO),
	
	Color("color", true, new ColorPropertyType(true), com.badlogic.gdx.graphics.Color.BLACK),
	Font("font", true, new StringPropertyType(), "arial"),
	FontStyle("font-style", true, new FontStylePropertyType(), com.majikarpets.ui.styles.FontStyle.Plain),
	
	;
	
	private static HashMap<String, Property> propertyMap = null;
	
	public static Property getByName(String propName) {
		if (propertyMap == null) {
			propertyMap = new HashMap<String, Property>();
			for (Property p: Property.values()) {
				propertyMap.put(p.name, p);
			}
		}
		return propertyMap.get(propName);
	}
	
	private Property(String name, boolean cascading, PropertyType type, Object defaultValue) {
		this.name = name;
		this.cascading = cascading;
		this.type = type;
		if (defaultValue != null & !type.verifyValue(defaultValue))
			throw new GameRuntimeException("Invalid default value for Property: " + this.name());
		this.defaultValue = defaultValue;
	}
	
	public final boolean cascading;
	public final PropertyType type;
	public final String name;
	public final Object defaultValue;
	
}
