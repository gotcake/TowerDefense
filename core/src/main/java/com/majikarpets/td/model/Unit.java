package com.majikarpets.td.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.majikarpets.td.StreamSerializable;

/**
 * An unit in the game that gives or takes damage, and can possibly move.
 * This is a "dumb" class that simply keeps track of all of the data for a given unit.
 * @author Aaron Cake
 */
public class Unit implements StreamSerializable {
	
	/*
	 * Fields in Unit are declared public since Unit is simply a data container,
	 * and setters and getters introduce unnecessary overhead 
	 */
	
	/** The type of this unit. This is used to determine how the unit is rendered. */
	public int type;
	
	/** The team of this unit */
	public Team team;
	
	/** The Elemental of this unit */
	public Elemental elemental;
	
	/** The current X grid location of the unit's top-left grid cell */
	public float gridLocationX;
	
	/** The current Y grid location of the unit's top-left grid cell */
	public float gridLocationY;
	
	/** The maximum attack range of the unit */
	public float maxRange;
	
	/** The minimum attack range of the unit */
	public float minRange;
	
	/** The speed of the unit */
	public float speed;
	
	/** Whether or not the unit is movable*/
	public boolean isMovable;
	
	/** The monetary value of the unit when killed */
	public int monetaryValue;
	
	/** The number of grid cells spanned in the X direction by this unit */
	public int cellSizeX;
	
	/** The number of grid cells spanned in the Y direction by this unit */
	public int cellSizeY;
	
	/** A list of target units for this unit, which it is actively attacking */
	private List<Unit> activeTargets;
	
	/** The total amount of life for this unit */
	public float life;
	
	/** Whether or not the unit is killable */
	public boolean isKillable;
	
	/** Whether or not the unit is an obstable */
	public boolean isObstacle;
	
	/**
	 * Create a new unit
	 */
	public Unit() {
		activeTargets = new ArrayList<Unit>();
	}

	/**
	 * Removes a target from the active targets for this unit
	 * @param target the target to remove
	 */
	public void removeTarget(Unit target) {
		activeTargets.remove(target);
	}
	
	/**
	 * Adds a target to the active targets for this unit
	 * @param target the target to add
	 */
	public void addTarget(Unit target) {
		activeTargets.add(target);
	}
	
	/**
	 * Gets a collection of the active targets for this unit
	 * @return the active targets for this unit
	 */
	public Collection<Unit> getTargets() {
		return activeTargets;
	}
	
	/**
	 * Removes all the active targets for this Unit
	 */
	public void clearTargets() {
		activeTargets.clear();
	}

	@Override
	public void serializeBinary(DataOutputStream out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deserializeBinary(DataInputStream in) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
