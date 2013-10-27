package com.majikarpets.td.view;

import com.badlogic.gdx.utils.IntMap;
import com.majikarpets.td.model.Unit;

/**
 * Currently just a placeholder for something to render the units
 * @author Aaron Cake
 */
public abstract class UnitRenderer {
	
	/** A map of unit types to UnitRenderer */
	private static IntMap<UnitRenderer> rendererMap = new IntMap<UnitRenderer>();
	
	/**
	 * Registers a UnitRenderer with a number of unit types
	 * @param r the UnitRenderer
	 * @param unitTypes the types to register with
	 */
	public static void registerRenderer(UnitRenderer r, int... unitTypes) {
		for (int type: unitTypes)
			rendererMap.put(type, r);
	}

	public abstract void renderUnit(Unit unit, Viewport viewport);
	
}
