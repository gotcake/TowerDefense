package com.majikarpets.ui.event;

import com.majikarpets.ui.styles.Property;

public interface PropertyChangeListener {
	public void propertyChanged(Property prop, Object value);
}
