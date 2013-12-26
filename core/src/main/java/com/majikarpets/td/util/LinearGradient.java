package com.majikarpets.td.util;

import com.badlogic.gdx.graphics.Color;

/**
 * A utility for calculating colors along a linear gradient
 * @author Aaron Cake
 */
public class LinearGradient {
	
	private float ax, ay, bx, by;
	private Color a, b;
	
	public LinearGradient(Color a, Color b, float ax, float ay, float bx, float by) {
		this.a = a;
		this.b = b;
		this.ax = ax;
		this.ay = ay;
		this.bx = bx;
		this.by = by;
	}
	
	/**
	 * Calculate the color at a given point in the (0,0) (1,1) range and
	 * store the result in the given color object
	 * @param target the color object to store the result in
	 * @param tx the x position (in the range of 0-1)
	 * @param ty the y position (in the range of 0-1)
	 */
	public void colorAt(Color target, float tx, float ty) {
		// project (tx, ty) onto the line from a to b
        float dx = ax - bx;
        float dy = ay - by;
        float t = ((ay - ty) * (ay - by) - (ax - tx) * (bx - ax)) / (dx * dx + dy * dy);
        if (t < 0)
        	t = 0;
        else if (t > 1)
        	t = 1;
        // interpolate from a to b by t
        target.a = (b.a - a.a) * t + b.a;
        target.r = (b.r - a.r) * t + b.r;
        target.g = (b.g - a.g) * t + b.g;
        target.b = (b.b - a.b) * t + b.b;
	}
	
}
