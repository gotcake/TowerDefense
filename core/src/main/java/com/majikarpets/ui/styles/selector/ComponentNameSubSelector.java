package com.majikarpets.ui.styles.selector;

import com.majikarpets.ui.Component;

public class ComponentNameSubSelector extends SubSelector {
	
	private final String name;
	
	public ComponentNameSubSelector(String name, boolean immediateChild) {
		super(immediateChild);
		this.name = name;
	}
	
	@Override
	public boolean matches(Component c) {
		return c.getComponentName().equals(name);
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int getSpecificity() {
		return 1;
	}
	
}
