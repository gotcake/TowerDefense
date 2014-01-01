package com.majikarpets.ui.styles;

import java.util.ArrayList;
import java.util.List;

import com.majikarpets.ui.Container;

public class PropertyRuleSet {
	
	private final List<PropertyRule> rules;
	private final List<PropertyRuleSetListener> listeners;
	
	public PropertyRuleSet(List<PropertyRule> rules) {
		this.rules = rules;
		this.listeners = new ArrayList<PropertyRuleSetListener>();
	}
	
	public PropertyRuleSet() {
		this(new ArrayList<PropertyRule>());
	}
	
	public void addRule(PropertyRule rule) {
		rules.add(rule);
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (PropertyRule rule: rules) {
			b.append(rule.toString());
			b.append("\n");
		}
		return b.toString();
	}
	
	public void bindTo(Container root) {
		listeners.add(new PropertyRuleSetBinding(root));
	}

}
