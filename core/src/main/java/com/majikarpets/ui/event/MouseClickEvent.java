package com.majikarpets.ui.event;

import com.majikarpets.ui.Component;

public class MouseClickEvent extends Event {
	
	public final int button;
	public final int clicks;
	public final float x;
	public final float y;
	
	
	public MouseClickEvent(Component src, int button, int clicks, float x, float y) {
		super("mouseClick", src);
		this.button = button;
		this.clicks = clicks;
		this.x = x;
		this.y = y;
	}

}
