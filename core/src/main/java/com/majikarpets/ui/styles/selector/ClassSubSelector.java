package com.majikarpets.ui.styles.selector;

import java.util.Set;

import com.majikarpets.ui.Component;

public class ClassSubSelector extends SubSelector {
	
	private final Set<String> classes;

	public ClassSubSelector(Set<String> classes, boolean immediateChild) {
		super(immediateChild);
		this.classes = classes;
	}

	@Override
	public boolean matches(Component c) {
		return c.hasClasses(classes);
	}
	
	@Override
	public String toString() {
		String s = "";
		for (String cls: classes) {
			s += "." + cls;
		}
		return s;
	}

	@Override
	public int getSpecificity() {
		return 1;
	}
	

}
