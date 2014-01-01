package com.majikarpets.ui.styles.selector;

import java.util.Collection;

import com.majikarpets.ui.Component;

public class CompoundSubSelector extends SubSelector {
	
	private final Collection<SubSelector> selectors;

	public CompoundSubSelector(Collection<SubSelector> selectors, boolean immediateChild) {
		super(immediateChild);
		this.selectors = selectors;
	}

	@Override
	public boolean matches(Component c) {
		for (SubSelector s: selectors) {
			if (!s.matches(c))
				return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (SubSelector s: selectors) {
			str += s.toString();
		}
		return str;
	}

	@Override
	public int getSpecificity() {
		int total = 0;
		for (SubSelector s: selectors) {
			total += s.getSpecificity();
		}
		return total;
	}

}
