package com.majikarpets.ui.styles;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import org.junit.runner.manipulation.Sortable;
import org.junit.runner.manipulation.Sorter;

import com.majikarpets.ui.styles.selector.Selector;

public class PropertyRuleMatch implements Comparable<PropertyRuleMatch> {
	
	public final Selector selector;
	public final PropertyRule rule;
	
	public PropertyRuleMatch(Selector selector, PropertyRule rule) {
		if (selector == null || rule == null)
			throw new NullPointerException("PropertyRuleMatch constructor parameters may not be null.");
		this.selector = selector;
		this.rule = rule;
	}

	@Override
	public int compareTo(PropertyRuleMatch o) {
		return selector.compareTo(o.selector);
	}


}
