package com.majikarpets.td.model;

import java.util.ArrayDeque;
import java.util.Queue;

import com.badlogic.gdx.utils.IntMap;

/**
 * A factory for Units that recycles Units to save on object creation. 
 * Delegates readying (modifying the appropriate states of) the units to the registered UnitReadiers.
 * @author Aaron Cake
 */
public class UnitFactory {
	
	/** A map of unit types to UnitReadiers */
	private static IntMap<UnitReadier> factoryMap = new IntMap<UnitReadier>();
	
	/**
	 * Registers a UnitReadier with a number of unit types
	 * @param r the UnitReadier
	 * @param unitTypes the types to register with
	 */
	public static void registerUnitReadier(UnitReadier r, int... unitTypes) {
		for (int type: unitTypes)
			factoryMap.put(type, r);
	}
	
	/** A queue of recycled units */
	private Queue<Unit> recycledUnits = new ArrayDeque<Unit>();
	
	/**
	 * Removes all recycled Units from the queue
	 */
	public void clearRecycledUnits() {
		recycledUnits.clear();
	}

	/**
	 * Recycle the given unit
	 * @param unit the unit to recycle
	 */
	public void recycleUnit(Unit unit) {
		recycledUnits.add(unit);
	}
	
	/**
	 * Get a Unit from this factory
	 * @return a Unit
	 */
	public Unit getUnit(int unitType) {
		Unit unit;
		if (!recycledUnits.isEmpty())
			unit = recycledUnits.poll();
		else
			unit = new Unit();
		factoryMap.get(unitType).readyUnit(unit, unitType);
		return unit;
	}
	
}
