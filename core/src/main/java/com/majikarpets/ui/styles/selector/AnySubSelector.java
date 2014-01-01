package com.majikarpets.ui.styles.selector;

import com.majikarpets.ui.Component;

public class AnySubSelector extends SubSelector {

	public AnySubSelector(boolean immediateChild) {
		super(immediateChild);
	}

	@Override
	public boolean matches(Component c) {
		return true;
	}
	
	@Override
	public String toString() {
		return "*";
	}

	@Override
	public int getSpecificity() {
		return 0;
	}
	
}
