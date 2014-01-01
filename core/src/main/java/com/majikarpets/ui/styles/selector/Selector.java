package com.majikarpets.ui.styles.selector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.majikarpets.ui.Component;
import com.majikarpets.ui.Container;

public class Selector implements Comparable<Selector>{
	
	public static List<Selector> parse(String str) throws SelectorFormatException {
		ArrayList<Selector> selectors = new ArrayList<Selector>();
		for (String sub: str.split("\\s*,\\s*")) {
			selectors.add(new Selector(sub));
		}
		return selectors;
	}
	
	private static final Pattern subPattern = Pattern.compile("(([\\.#:])?[\\w\\-]+)|(\\*)");
	
	private final ArrayList<SubSelector> subSelectors;
	
	public Selector(String str) throws SelectorFormatException {
			subSelectors = new ArrayList<SubSelector>();
			boolean immediateChild = false;
			for (String sel: str.split("\\s+")) {
				if (sel.equals(">")) {
					if (immediateChild)
						throw new SelectorFormatException("missing sub selector between > and >");
					immediateChild = true;
				} else {
					Matcher matcher = subPattern.matcher(sel);
					int i = 0;
					boolean any = false;
					String id = null, name = null;
					HashSet<String> classes = new HashSet<String>();
					EnumSet<Component.ModifierState> modifiers = EnumSet.noneOf(Component.ModifierState.class);
					while (matcher.find()) {
						if (matcher.start() != i)
							throw new SelectorFormatException();
						String group = matcher.group();
						if (group.equals("*")) {
							any = true;
						} else if (group.startsWith("#")) {
							if (id != null)
								throw new SelectorFormatException("multiple conflicting ids in selector");
							id = group.substring(1);
						} else if (group.startsWith(":")) {
							Component.ModifierState mod = Component.ModifierState.getByName(group.substring(1));
							if (mod == null)
								throw new SelectorFormatException("invalid modifier " + group + " in selector");
							modifiers.add(mod);
						} else if (group.startsWith(".")) {
							classes.add(group.substring(1));
						} else {
							if (name != null)
								throw new SelectorFormatException("multiple conflicting component names in selector");
							name = group;
						}
						i = matcher.end();
					}
					ArrayList<SubSelector> selectorList = new ArrayList<SubSelector>();
					if (any)
						selectorList.add(new AnySubSelector(immediateChild));
					if (id != null)
						selectorList.add(new IDSubSelector(id, immediateChild));
					if (name != null)
						selectorList.add(new ComponentNameSubSelector(name, immediateChild));
					if (!classes.isEmpty())
						selectorList.add(new ClassSubSelector(classes, immediateChild));
					if (!modifiers.isEmpty())
						selectorList.add(new ModifierSubSelector(modifiers, immediateChild));
					if (selectorList.isEmpty())
						throw new SelectorFormatException("missing sub selector");
					if (selectorList.size() > 1) {
						if (any)
							throw new SelectorFormatException("illegal: sub selectors combined with any (*) sub selector");
						subSelectors.add(new CompoundSubSelector(selectorList, immediateChild));
					} else if (!any || (any && immediateChild)) {
						subSelectors.add(selectorList.get(0));
					}
					immediateChild = false;
				}
			}
			if (subSelectors.isEmpty())
				throw new SelectorFormatException("no sub-selectors given");
	}
	
	public boolean matches(Component c) {
		if (c == null)
			throw new NullPointerException("Component may not be null");
		int sel = subSelectors.size() - 1;
		while(c != null && sel >= 0) {
			if (!subSelectors.get(sel).matches(c))
				return false;
			sel--;
			c = c.getParent();
		}
		if (c == null && sel == -1)
			return true;
		return false;
	}
	
	private void findNonImmediate(Container parent, List<Component> matches, SubSelector sub) {
		Iterator<Component> it = parent.childIterator();
		while (it.hasNext()) {
			Component child = it.next();
			if (sub.matches(child))
				matches.add(child);
			if (child instanceof Container)
				findNonImmediate((Container)child, matches, sub);
		}
	}
	
	private List<Component> find(List<Component> containers, SubSelector sub) {
		List<Component> matches = new ArrayList<Component>();
		for (Component c: containers) {
			if (sub.immediateChild) {
				if (c instanceof Container) {
					Iterator<Component> it = ((Container)c).childIterator();
					while (it.hasNext()) {
						Component child = it.next();
						if (sub.matches(child))
							matches.add(child);
					}
				} else {
					findNonImmediate((Container)c, matches, sub);
				}
			}
		}
		return matches;
	}
	
	/*private List<MatchResult> matchTo(List<MatchResult> containers, SubSelector sub) {
		List<Component> matches = new ArrayList<Component>();
		for (Component c: containers) {
			if (sub.immediateChild) {
				if (c instanceof Container) {
					Iterator<Component> it = ((Container)c).childIterator();
					while (it.hasNext()) {
						Component child = it.next();
						if (sub.matches(child))
							matches.add(child);
					}
				} else {
					findNonImmediate((Container)c, matches, sub);
				}
			}
		}
		return matches;
	}
	
	public List<MatchResult> matchTo(Container root) {
		
	}*/
	
	public List<Component> find(Container root) {
		List<Component> comps = new ArrayList<Component>(1);
		comps.add(root);
		return find(comps);
	}
	
	public List<Component> find(List<Component> components) {
		int sel = 0, numSels = subSelectors.size();
		for (; sel<numSels; sel++) {
			SubSelector sub = subSelectors.get(sel);
			components = find(components, sub);
			if (components.isEmpty())
				return components;
		}
		return components;
	}
	
	public static void main(String[] args) throws SelectorFormatException {
		String str = "> #main .other component, #main .thing * > div.cls:hover";
		System.out.println(new Selector(str));
	}
	
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		boolean firstSel = true;
		for (SubSelector sel: subSelectors) {
			if (firstSel) {
				firstSel = false;
			} else {
				b.append(" ");
			}
			if (sel.immediateChild)
				b.append("> ");
			b.append(sel.toString());
		}
		return b.toString();
	}

	@Override
	public int compareTo(Selector o) {
		int cmp = Integer.compare(subSelectors.size(), o.subSelectors.size());
		if (cmp == 0) {
			SubSelector last = subSelectors.get(subSelectors.size() - 1);
			SubSelector otherLast = subSelectors.get(subSelectors.size() - 1);
			cmp = Integer.compare(last.getSpecificity(), otherLast.getSpecificity());
		}
		return cmp;
	}
	
}
