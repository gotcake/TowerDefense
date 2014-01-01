package com.majikarpets.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.majikarpets.td.util.LinearGradient;
import com.majikarpets.ui.event.EventHandler;
import com.majikarpets.ui.event.PropertyChangeHandler;
import com.majikarpets.ui.styles.Property;

public class AreaPainter {
	
	private enum Mode {
		None,
		Gradient,
		Solid,
		Image
	}
	
	private enum ImageMode {
		Plain,
		Stretch,
		Fit,
		RegionPlain,
		RegionFit,
		RegionStretch
	}

	private Mode paintMode = Mode.None;
	private ImageMode imageMode = ImageMode.Plain;
	private Color color = Color.WHITE;
	private LinearGradient gradient = null;
	private Texture tex = null;
	private TextureRegion texRegion = null;
	private float radiusTopLeft = 0;
	private float radiusTopRight = 0;
	private float radiusBottomRight = 0;
	private float radiusBottomLeft = 0;
	
	public AreaPainter() {
		
	}
	
	public void paint(float x, float y, float width, float height) {
		
	}
	
	
}
