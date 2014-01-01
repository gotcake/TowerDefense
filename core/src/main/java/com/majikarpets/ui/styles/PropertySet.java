package com.majikarpets.ui.styles;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.Set;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.event.EventHandler;
import com.majikarpets.ui.event.EventProxy;
import com.majikarpets.ui.event.PropertyBinding;
import com.majikarpets.ui.event.PropertyBindingProxy;
import com.majikarpets.ui.event.PropertyChangeHandler;
import com.majikarpets.ui.event.PropertyChangeListener;
import com.majikarpets.ui.event.PropertyChangeProxy;
import com.majikarpets.ui.styles.parser.InvalidPropertyValueException;
import com.majikarpets.ui.styles.selector.Selector;
import com.majikarpets.ui.styles.types.PropertyType;

/**
 * A set of properties of a component and their values
 * @author Aaron Cake
 * TODO: call listeners when inherited properties are updated
 */
public class PropertySet {
	
	/** the property set of the parent component */
	private PropertySet parent;
	/** the owner component */
	private Component component;
	/** the list of currently active rules */
	private final List<PropertyRuleMatch> activeRules;
	/** the list of rules that will be applies at the next update */
	private List<PropertyRuleMatch> pendingRules;
	/** the resulting properties from the currently matched rules */
	private final EnumMap<Property, Object> ruleProperties;
	/** any override properties set on this property set */
	private final EnumMap<Property, Object> overrideProperties;
	/** any pending overrides to be applied on the next update */
	private final EnumMap<Property, Object> pendingOverrides;
	/** the transition times defined by the active rules */
	private final EnumMap<Property, Float> ruleTransitionTimes;
	/** the override transition times */
	private final EnumMap<Property, Float> overrideTransitionTimes;
	/** the property changes made on the last update */
	private final EnumMap<Property, PropertyChange> lastChanges;
	/** the property change listeners */
	private final EnumMap<Property, List<PropertyChangeListener>> listeners;
	
	public PropertySet(Component c) {
		parent = null;
		component = c;
		ruleProperties = new EnumMap<Property, Object>(Property.class);
		overrideProperties = new EnumMap<Property, Object>(Property.class);
		ruleTransitionTimes = new EnumMap<Property, Float>(Property.class);
		overrideTransitionTimes = new EnumMap<Property, Float>(Property.class);
		pendingOverrides = new EnumMap<Property, Object>(Property.class);
		activeRules = new ArrayList<PropertyRuleMatch>();
		lastChanges = new EnumMap<Property, PropertyChange>(Property.class);
		pendingRules = null;
		listeners = new EnumMap<Property, List<PropertyChangeListener>>(Property.class);
	}
	
	public void setParent(PropertySet parent) {
		this.parent = parent;
	}
	
	protected int findActiveRule(PropertyRule rule) {
		for (int i=0; i<activeRules.size(); i++) {
			if (activeRules.get(i).rule.equals(rule))
				return i;
		}
		return -1;
	}
	
	
	/**
	 * Adds a rule, to be applied on the next update
	 * @param rule the rule to add
	 */
	public void addRule(PropertyRule rule, Selector selector) {
		int index = findActiveRule(rule);
		if (index == -1) {
			if (pendingRules == null) {
				pendingRules = new ArrayList<PropertyRuleMatch>(activeRules);
				pendingRules.add(new PropertyRuleMatch(selector, rule));
			} else if (!pendingRules.contains(rule)) {
				pendingRules.add(new PropertyRuleMatch(selector, rule));
			}
		}
	}
	
	/**
	 * Removes a rule, to applied on the next update
	 * @param rule the rule to remove
	 */
	public void removeRule(PropertyRule rule) {
		int index = findActiveRule(rule);
		if (index != -1) {
			if (pendingRules == null)
				pendingRules = new ArrayList<PropertyRuleMatch>(activeRules);
			pendingRules.remove(index);
		}
	}
	
	/**
	 * Removes all rules, applied on the next update
	 */
	public void clearRules() {
		pendingRules = new ArrayList<PropertyRuleMatch>();
	}
	
	/**
	 * Gets the parent PropertySet
	 * @return the parent
	 */
	public PropertySet getParent() {
		return parent;
	}
	
	/**
	 * Sets the given property as pending a certain value
	 * @param prop the property to set
	 * @param value the string value to be parsed into a property value
	 * @throws InvalidPropertyValueException the value was an invalid property value
	 */
	public void setProperty(Property prop, String value) throws InvalidPropertyValueException {
		setPropertyRaw(prop, prop.type.parseValue(value));
	}
	
	/**
	 * Sets the given property as pending a certain value
	 * @param prop the property to set
	 * @param value the (pre-parsed/raw) property value to set
	 * @throws InvalidPropertyValueException the value was invalid
	 */
	public void setPropertyRaw(Property prop, Object value) throws InvalidPropertyValueException {
		if (!Objects.equals(ruleProperties.get(prop), value))
			this.pendingOverrides.put(prop, value);
	}
	
	/**
	 * Gets the active property value, recursively through the parent if cascading
	 * @param p the property
	 * @return the property value
	 */
	public Object getProperty(Property p) {
		Object val = overrideProperties.get(p);
		if (val == null)
			val = ruleProperties.get(p);
		if (val == null && p.cascading && parent != null)
			val = parent.getProperty(p);
		if (val == null)
			val = p.defaultValue;
		return val;
	}
	
	/**
	 * Resolves any dependencies among the pending changes and applies all changes.
	 * Must be called prior to callListeners()
	 */
	public void update() {
		lastChanges.clear();
		if (pendingRules != null) {
			Collections.sort(pendingRules);
			ruleTransitionTimes.clear();
			for (PropertyRuleMatch m: pendingRules) {
				ruleTransitionTimes.putAll(m.rule.transitionTimes);
				
			}
		}
		for (Entry<Property, Object> entry: pendingOverrides.entrySet()) {
			
		}
	}
	
	/**
	 * Calls all change listeners for any previously pended changes.
	 * Must be called after update();
	 */
	public void callListeners() {
		
	}
	
	/**
	 * Adds a listener to be called after changes to the given property are applied
	 * @param p the property
	 * @param l the listener
	 * @return true if it was added successfully
	 */
	public boolean addPropertyChangeListener(Property p, PropertyChangeListener l) {
		List<PropertyChangeListener> list = listeners.get(p);
		if (list == null) {
			list = new ArrayList<PropertyChangeListener>();
			listeners.put(p, list);
		}
		if (!list.contains(l)) {
			list.add(l);
			return true;
		} else
			return false;
	}
	
	/**
	 * Removes a listener
	 * @param p the property to remove the listener from
	 * @param l the listener to remove
	 * @return true if it was removed successfully
	 */
	public boolean removePropertyChangeListener(Property p, PropertyChangeListener l) {
		List<PropertyChangeListener> list = listeners.get(p);
		if (list != null) {
			return list.remove(l);
		} else
			return false;
	}
	
	/**
	 * Binds all of the methods annotated with PropertyChangeHandler and fields annotated with PropertyBinding
	 * of the given object with this property set.
	 * @param obj the object to bind
	 */
	public void addPropertyChangeListenerProxy(Object obj) {
		addProxy(obj, obj.getClass());
	}
	
	private void addProxy(Object obj, Class<?> cls) {
		Class<?> supr = cls.getSuperclass();
		if (supr != Object.class)
			addProxy(obj, supr);
		for (Method method: cls.getDeclaredMethods()) {
			method.setAccessible(true);
			PropertyChangeHandler pch = method.getAnnotation(PropertyChangeHandler.class);
			if (pch != null) {
				for (Property prop: pch.value()) {
					addPropertyChangeListener(prop, new PropertyChangeProxy(prop, obj, method));
				}
			}
			
		}
		for (Field field: cls.getDeclaredFields()) {
			field.setAccessible(true);
			PropertyBinding pb = field.getAnnotation(PropertyBinding.class);
			if (pb != null) {
				addPropertyChangeListener(pb.value(), new PropertyBindingProxy(pb.value(), obj, field));
			}
		}
	}
	
	private static class PropertyChange {
		float transitionTime;
		Object value;
		
		private PropertyChange(float transtionTime, Object value) {
			this.transitionTime = transtionTime;
			this.value = value;
		}
	}
	
}
