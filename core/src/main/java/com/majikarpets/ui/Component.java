package com.majikarpets.ui;

import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import com.badlogic.gdx.graphics.Color;
import com.majikarpets.ui.event.ClassModificationEvent;
import com.majikarpets.ui.event.Event;
import com.majikarpets.ui.event.EventBus;
import com.majikarpets.ui.event.EventListener;
import com.majikarpets.ui.event.ModifierModificationEvent;
import com.majikarpets.ui.styles.Orientation;
import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.PropertySet;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;
import com.majikarpets.ui.styles.types.PropertyType;

public class Component {
	
	public enum ModifierState {
		Hover("hover"),
		Pressed("pressed")
		;
		public final String name;
		private ModifierState(String name) {
			this.name = name;
		}
		public static ModifierState getByName(String name) {
			for (ModifierState m: ModifierState.values()) {
				if (m.name.equals(name))
					return m;
			}
			return null;
		}
	}
	
	/** The delegate used to paint the background */
	protected AreaPainter backgroundPainter;
	/** The delegate used to paint the border, if any */
	protected BorderPainter borderPainter;
	/** The delegate used to paint text */
	protected TextPainter textPainter;
	
	protected EventBus eventBus;
	
	public final PropertySet properties;
	
	protected Container parent = null;
	protected String id = null;
	protected HashSet<String> classes;
	protected EnumSet<ModifierState> modifiers;
	
	public Component() {
		modifiers = EnumSet.noneOf(ModifierState.class);
		classes = new HashSet<String>();
		eventBus = new EventBus();
		properties = new PropertySet(this);
		backgroundPainter = new AreaPainter();
		borderPainter = new BorderPainter();
		textPainter = new TextPainter();
		properties.addPropertyChangeListenerProxy(this);
		properties.addPropertyChangeListenerProxy(backgroundPainter);
		properties.addPropertyChangeListenerProxy(borderPainter);
		properties.addPropertyChangeListenerProxy(textPainter);
		eventBus.addProxiedHandler(this);
	}
	
	public float resolvePerUnitDimension(Orientation orientation, float perUnitDimension) {
		return 0;
	}
	
	public String getComponentName() {
		return "component";
	}
	
	public void addModifier(ModifierState m) {
		if (modifiers.add(m)) {
			eventBus.fireEvent(new ModifierModificationEvent(this, modifiers));
		}
	}
	
	public void removeModifier(ModifierState m) {
		if (modifiers.remove(m)) {
			eventBus.fireEvent(new ModifierModificationEvent(this, modifiers));
		}
	}
	
	public boolean hasClasses(Collection<String> classes) {
		return this.classes.containsAll(classes);
	}
	
	public boolean hasModifiers(Collection<ModifierState> modifiers) {
		return this.modifiers.containsAll(modifiers);
	}
	
	public String getID() {
		return id;
	}
	
	public void setClass(String clss) {
		HashSet<String> newClasses = new HashSet<String>();
		for (String cls: clss.split("\\s+")) {
			newClasses.add(cls);
		}
		if (!newClasses.equals(classes)) {
			classes = newClasses;
			eventBus.fireEvent(new ClassModificationEvent(this, classes));
		}
	}
	
	public void addClass(String name) {
		if (classes.add(name)) {
			eventBus.fireEvent(new ClassModificationEvent(this, classes));
		}
	}
	
	public void removeClass(String name) {
		if (classes.remove(name)) {
			eventBus.fireEvent(new ClassModificationEvent(this, classes));
		}
	}
	
	public Component getParent() {
		return parent;
	}
	
	public void render() {
		
	}
	
	public void paint() {
		// empty implementation
	}

	
	
	
	
	
}
