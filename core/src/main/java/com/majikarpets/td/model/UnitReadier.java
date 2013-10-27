package com.majikarpets.td.model;

/**
 * Gets a unit ready for use in the game
 * @author Aaron Cake
 */
public interface UnitReadier {
	
	/**
	 * Gets a unit ready for use in the game by applying all the necessary states to
	 * the given unit according to the given unit type.
	 * @param unit the unit to ready
	 * @param unitType the unit type
	 */
	public void readyUnit(Unit unit, int unitType);
	
}
