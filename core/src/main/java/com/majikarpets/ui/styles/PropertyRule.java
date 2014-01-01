package com.majikarpets.ui.styles;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map.Entry;

import com.majikarpets.ui.styles.selector.Selector;

public class PropertyRule {
	
	private static DecimalFormat df = new DecimalFormat("0.#####");
	
	private static void transitionTimeString(EnumMap<Property, Float> transitionTime, StringBuilder sb) {
		sb.append("\ttransition: ");
		boolean first = true;
		for (Entry<Property, Float> entry: transitionTime.entrySet()) {
			Float val = entry.getValue();
			if (val != null) {
				if (first) {
					first = false;
				} else {
					sb.append(", ");
				}
				sb.append(entry.getKey().name);
				sb.append(' ');
				if (val >= 1000) {
					sb.append(df.format(val / 1000));
					sb.append("ms");
				} else {
					sb.append(df.format(val));
					sb.append("s");
				}
				
			}
		}
		sb.append(";\n");
	}
	
	public final List<Selector> selectors;
	public final EnumMap<Property, Float> transitionTimes;
	public final List<PropertyValue> styles;
	
	public PropertyRule(List<Selector> selectors, List<PropertyValue> styles, EnumMap<Property, Float> transitionTime) {
		if (selectors == null || transitionTime == null || styles == null)
			throw new NullPointerException("PropertyRule constructor parameters may not be null.");
		this.selectors = selectors;
		this.styles = Collections.unmodifiableList(styles);
		this.transitionTimes = transitionTime;
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		boolean firstSel = true;
		for (Selector s: selectors) {
			if (firstSel) {
				firstSel = false;
			} else {
				b.append(", ");
			}
			b.append(s.toString());
		}
		b.append(" {\n");
		if (!transitionTimes.isEmpty())
			transitionTimeString(transitionTimes, b);
		for (PropertyValue style: styles) {
			b.append("\t");
			b.append(style.toString());
			b.append("\n");
		}
		b.append("}");
		return b.toString();
	}

}
