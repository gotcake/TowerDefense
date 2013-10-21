package com.majikarpets.td.model;

import java.util.HashMap;
import java.util.Map;

import com.majikarpets.td.util.IntegerPair;

/**
 * A utility class for managing relative element strengths
 * @author Aaron Cake
 */
public class Element {
	
	/** Holds the relative strengths of elements */
	private static Map<IntegerPair, Float> damageModifers = new HashMap<IntegerPair, Float>();
	
	/**
	 * Sets the damage modifier for a given element attacking another element
	 * @param attacker the attacker element id
	 * @param target the target element id
	 * @param relativeDamage the damage modifier
	 * @param both true to apply the inverse
	 */
	public static void setDamageModifer(int attacker, int target, float relativeDamage, boolean both) {
		damageModifers.put(new IntegerPair(attacker, target), relativeDamage);
		if (both) {
			damageModifers.put(new IntegerPair(target, attacker), 1f / relativeDamage);
		}
	}
	
	/**
	 * Gets the damage modifier for an element attacking another element
	 * @param attacker the attacking element
	 * @param target the target element
	 * @return the damage modifier
	 */
	public static float getDamageModifier(int attacker, int target) {
		if (attacker == target) return 1;
		Float val = damageModifers.get(new IntegerPair(attacker, target));
		if (val != null)
			return val;
	   return 1;
	}
	 
	 /** Private constructor. Don't instantiate this class. */
	 private Element() { }
	
}
