package com.majikarpets.td.model;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.majikarpets.td.GameRuntimeException;
import com.majikarpets.td.util.IntegerPair;


/**
 * A combination of different Elements in different amounts which can be compared to determine damage modifiers
 * Attack and defense modifiers are defined separately. Defense modifiers are implicitly defined through the attack modifier
 * of an attacking Elemental. Elements can be combined in different parts/unit for fine-tuned damage customization.
 * @author Aaron Cake
 */
public class Elemental {
	
	private static IntMap<Elemental> elementalMap = new IntMap<Elemental>();
	private static Map<IntegerPair, Float> damageModifierCache = new HashMap<IntegerPair, Float>();
	
	/**
	 * Clears all modifiers from the cache.
	 * Call if you modified an Elemental or element.
	 */
	public static void clearCachedModifiers() {
		damageModifierCache.clear();
	}
	
	/**
	 * Gets the damage modifier of an Elemental attacking another Elemental
	 * @param attacker the attacking Elemental ID
	 * @param target the target Elemental ID
	 * @return the damage modifier
	 */
	private static float getDamageModifer(int attacker, int target) {
		IntegerPair pair = new IntegerPair(attacker, target);
		Float val = damageModifierCache.get(pair);
		if (val == null) {
			val = getElemental(attacker)._getAttackDamageModifer(getElemental(target));
			damageModifierCache.put(pair, val);
		}
		return val;
	}
	
	/**
	 * Gets the Elemental with the given ID.
	 * Throws GameRuntimeException if the id does not exist.
	 * @param id the Elemental id
	 * @return the Elemental
	 */
	public static Elemental getElemental(int id) {
		Elemental e = elementalMap.get(id);
		if (e == null)
			throw new GameRuntimeException("No elemental with the id " + id + " exists.");
		return e;
	}
	
	/** The id of this Elemental **/
	public final int id;
	/** An array of element ids */
	private IntArray elements;
	/** An array of part counts for each element */
	private IntArray parts;
	/** The total number of parts in this Elemental */
	private int totalParts;
	
	/**
	 * Create a new Elemental containing no Elements
	 */
	public Elemental(int id) {
		parts = new IntArray();
		elements = new IntArray();
		totalParts = 0;
		this.id = id;
	}
	
	/**
	 * Adds a given element to this Elemental with a given number of parts per unit
	 * @param elementId the element Id
	 * @param numParts the number of parts per unit
	 */
	public void addElement(int elementId, int numParts) {
		
		// add the data
		parts.add(numParts);
		elements.add(elementId);
		
		// update the total parts
		totalParts += numParts;
	}
	
	/**
	 * Gets the attack damage modifier for attacking another Elemental
	 * @param target the target that is being attacked
	 * @return the damage modifier
	 */
	public float getAttackDamageModifer(Elemental target) {
		return getDamageModifer(this.id, target.id);
	}
	
	/** Calculates the damage modifier */
	private float _getAttackDamageModifer(Elemental target) {
		if (elements.size == 0 || target.elements.size == 0) return 1;
		float total = 0, aTotal;
	    int i, a, id;
		for (i=0; i<elements.size; ++i) {
			aTotal = 0;
			id = elements.items[i];
			for (a=0; a<target.elements.size; ++a) {
				aTotal += target.parts.items[a] * Element.getDamageModifier(id, target.elements.items[a]);
			}
			total += aTotal / target.totalParts;
		}
		return total / totalParts;
	}
	
}
