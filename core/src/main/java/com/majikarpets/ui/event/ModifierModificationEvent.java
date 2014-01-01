package com.majikarpets.ui.event;

import java.util.Collections;
import java.util.Set;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.Component.ModifierState;

public class ModifierModificationEvent extends Event {
	
	public final Set<ModifierState> modifiers;

	public ModifierModificationEvent(Component source, Set<ModifierState> modifiers) {
		super("modifiersModified", source);
		this.modifiers = Collections.unmodifiableSet(modifiers);
	}

}
