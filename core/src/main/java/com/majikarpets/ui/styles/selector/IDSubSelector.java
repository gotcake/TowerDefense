package com.majikarpets.ui.styles.selector;

import com.majikarpets.ui.Component;

public class IDSubSelector extends SubSelector {

	private final String id;
	
	public IDSubSelector(String id, boolean immediateChild) {
		super(immediateChild);
		this.id = id;
	}
	
	@Override
	public boolean matches(Component c) {
		return c.getID().equals(id);
	}
	
	@Override
	public String toString() {
		return "#" + id;
	}

	@Override
	public int getSpecificity() {
		return 100;
	}
	

}
