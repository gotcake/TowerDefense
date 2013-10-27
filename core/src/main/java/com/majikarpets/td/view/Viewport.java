package com.majikarpets.td.view;

/**
 * Contains information about the viewable area of the game
 * 
 * TODO: .apply() logic
 * 
 * @author Aaron Cake
 */
public class Viewport {

	/** The top of the viewport */
	private float top;
	/** The bottom of the viewport */
	private float left;
	/** The bottom of the viewport */
	private float bottom;
	/** The right of the viewport */
	private float right;
	/** Whether or not the GL context needs updating according to the viewport */
	private boolean dirty;
	
	/**
	 * Applies the viewport settings (modelview transform, etc) to the GL context
	 * To be called before the level and all units are rendered;
	 */
	public void apply() {
		if (dirty) {
			// update the GL context to represent the viewport
			dirty = false;
		}
	}
	
	/**
	 * Initializes the viewport
	 */
	public void init() {
		dirty = true;
	}

	/**
	 * @return the top
	 */
	public float getTop() {
		return top;
	}

	/**
	 * @param top the top to set
	 */
	public void setTop(float top) {
		if (top != this.top) {
			this.top = top;
			this.dirty = true;
		}
	}

	/**
	 * @return the left
	 */
	public float getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(float left) {
		if (left != this.left) {
			this.left = left;
			dirty = true;
		}
	}

	/**
	 * @return the bottom
	 */
	public float getBottom() {
		return bottom;
	}

	/**
	 * @param bottom the bottom to set
	 */
	public void setBottom(float bottom) {
		if (bottom != this.bottom) {
			this.bottom = bottom;
			dirty = true;
		}
	}

	/**
	 * @return the right
	 */
	public float getRight() {
		return right;
	}
	
	/**
	 * Gets the height of the viewport
	 * @return the height of the viewport
	 */
	public float getHeight() {
		return Math.abs(bottom - top);
	}
	 
	/**
	 * Gets the width of the viewport
	 * @return the width of the viewport
	 */
	public float getWidth() {
		return Math.abs(right - left);
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(float right) {
		if (this.right != this.right) {
			this.right = right;
			dirty = true;
		}
	}
	
	/**
	 * Moves the viewport the specified number of units in the x and y directions
	 * @param dx the number of x units to move
	 * @param dy the number of y units to move
	 */
	public void move(float dx, float dy) {
		if (dx != 0 || dy != 0) {
			right += dx;
			left += dx;
			top += dy;
			bottom += dy;
			dirty = true;
		}
	}
	
	/**
	 * Zooms in or out by the given factor.
	 * A positive value zooms in by 2^factor, while a negative value zooms out by 2^|factor|
	 * @param factor the factor to zoom in or out by
	 */
	public void zoom(float factor) {
		if (factor != 0) {
			float mult = (float)Math.pow(2, factor);
			float width = getWidth();
			float height = getHeight();
			float difX = ((width * mult) - width) / 2;
			float difY = ((height * mult) - height) / 2;
			top += difY;
			left += difX;
			bottom -= difY;
			right -= difX;
			dirty = true;
		}
		
	}
	
	
	
	
}
