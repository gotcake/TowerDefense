package com.majikarpets.ui.styles.selector;

import com.majikarpets.ui.Component;

public abstract class SubSelector {
	
	public final boolean immediateChild;
	
	public SubSelector(boolean immediateChild) {
		this.immediateChild = immediateChild;
	}
	
	public abstract boolean matches(Component c);
	
	public abstract int getSpecificity();

}
