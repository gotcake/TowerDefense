package com.majikarpets.td.model;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * A factory for Units that recycles Units to save on object creation
 * @author Aaron Cake
 */
public abstract class AbstractUnitFactory {
	
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
	public Unit getUnit() {
		Unit unit;
		if (!recycledUnits.isEmpty())
			unit = recycledUnits.poll();
		else
			unit = new Unit();
		readyUnit(unit);
		return unit;
	}
	
	/**
	 * Readies the given Unit by applying all the necessary states
	 * @param unit the unit to ready
	 */
	public abstract void readyUnit(Unit unit);
	
}
