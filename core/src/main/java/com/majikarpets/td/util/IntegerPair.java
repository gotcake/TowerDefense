package com.majikarpets.td.util;

/**
 * A pair of integers. IntegerPairs only are considered equal if they 
 * contain the same two integers in the same order.
 * @author Aaron Cake
 */
public class IntegerPair {
	
	public final int a;
	public final int b;
	
	/**
	 * Create a new IntegerPair from two integers
	 * @param a the first integer
	 * @param b the second integer
	 */
	public IntegerPair(int a, int b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Create a new IntegerPair with the a and b swapped
	 * @return a new, swapped IntegerPair
	 */
	public IntegerPair swap() {
		return new IntegerPair(b, a);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + a;
		return prime * result + b;
	}

	/**
	 * Two IntegerPair objects are equals only if their a and b's are equal respectively.
	 * @param o the object to compare
	 * @return if the object is not null, is an IntegerPair, and is equal to this instance
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof IntegerPair)) return false;
		IntegerPair other = (IntegerPair)o;
		return (a == other.a && b == other.b);
	}
	
}
