package com.majikarpets.ui.styles.selector;

import java.util.EnumSet;

import com.majikarpets.ui.Component;

public class ModifierSubSelector extends SubSelector {

	private final EnumSet<Component.ModifierState> modifiers;
	
	public ModifierSubSelector(EnumSet<Component.ModifierState> modifiers, boolean immediateChild) {
		super(immediateChild);
		this.modifiers = modifiers;
	}

	@Override
	public boolean matches(Component c) {
		return c.hasModifiers(modifiers);
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Component.ModifierState mod: modifiers) {
			s += ":" + mod.name;
		}
		return s;
	}

	@Override
	public int getSpecificity() {
		return 1;
	}
	
}
