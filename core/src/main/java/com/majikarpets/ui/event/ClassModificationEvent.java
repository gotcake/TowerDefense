package com.majikarpets.ui.event;

import java.util.Collections;
import java.util.Set;

import com.majikarpets.ui.Component;

public class ClassModificationEvent extends Event {
	
	public final Set<String> classes;
	
	public ClassModificationEvent(Component src, Set<String> classes) {
		super("classModified", src);
		this.classes = Collections.unmodifiableSet(classes);
	}

}
