package com.majikarpets.td.view;

import com.majikarpets.td.model.Level;
import com.majikarpets.td.model.Unit;

public interface Renderer {
	
	public void renderUnit(Unit unit, Viewport viewport, float dX, float dY);
	
	public void renderLevel(Level level, Viewport viewport);
	
	public void renderAttackAnimation(Unit attacker, Unit defender, Viewport viewport);
	
}
