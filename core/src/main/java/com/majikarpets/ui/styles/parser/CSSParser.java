package com.majikarpets.ui.styles.parser;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.majikarpets.ui.styles.Property;
import com.majikarpets.ui.styles.PropertyRule;
import com.majikarpets.ui.styles.PropertyValue;
import com.majikarpets.ui.styles.PropertyRuleMatch;
import com.majikarpets.ui.styles.PropertyRuleSet;
import com.majikarpets.ui.styles.selector.Selector;

public class CSSParser {
	
	private static final Pattern blockPattern = Pattern.compile(
			"\\s*([\\w\\-\\.#:\\*>,\\s]+?)\\s*\\{\\s*([@\\w,#%';:\\.\\-\\(\\)\\[\\]\\s]*?)\\s*\\}\\s*"
			);
	
	public static void parseCss(PropertyRuleSet ruleSet, String cssString) throws CSSParserException {
		int i=0;
		Matcher matcher = blockPattern.matcher(cssString);
		while (matcher.find()) {
			if (matcher.start() != i)
				throw new CSSParserException("Invalid css (probably an invalid character somewhere)");
			String selectorStr = matcher.group(1);
			String rulesStr = matcher.group(2);
			List<Selector> selectors = Selector.parse(selectorStr);
			List<PropertyValue> styles = new ArrayList<PropertyValue>();
			EnumMap<Property, Float> transitonTimes = new EnumMap<Property, Float>(Property.class);
			parseStyles(rulesStr, styles, transitonTimes);
			ruleSet.addRule(new PropertyRule(selectors, styles, transitonTimes));
			i = matcher.end();
		}
	}
	
	private static void parseStyles(String str, List<PropertyValue> styles, EnumMap<Property, Float> transitonTimes) throws CSSParserException {
		for (String rule: str.trim().split("\\s*;\\s*")) {
			if (rule.isEmpty())
				continue;
			String[] parts = rule.trim().split("\\s*:\\s*");
			if (parts.length != 2)
				throw new CSSParserException("Invalid style declaration: '" + rule + "'");
			if (parts[0].equals("transition")) {
				for (String timeDef: parts[1].split("\\s*,\\s*")) {
					String[] tparts = timeDef.split("\\s+");
					if (tparts.length != 2)
						throw new CSSParserException("Invalid transition declaration: '" + parts[1] + "'");
					Property p = Property.getByName(tparts[0]);
					if (p == null)
						throw new CSSParserException("Invalid property name in transition declaration: '" + tparts[0] + "'");
					if (!p.type.isInterpolatable())
						throw new CSSParserException("Un-transitionable property in transition declaration: '" + tparts[0] + "'");
					String time = tparts[1];
					float mult = 1;
					if (time.endsWith("ms")) {
						time = time.substring(0, time.length() - 2);
					} else if (time.endsWith("s")) {
						mult = 1000;
						time = time.substring(0, time.length() - 1);
					} else {
						throw new CSSParserException("Invalid time string in transition declaration: '" + tparts[1] + "'");
					}
					try {
						transitonTimes.put(p, Float.parseFloat(time) * mult);
					} catch (NumberFormatException e) {
						throw new CSSParserException("Invalid time string in transition-time declaration: '" + tparts[1] + "'");
					}
				}
			} else {
				Property prop = Property.getByName(parts[0]);
				if (prop == null)
					throw new CSSParserException("Invalid property: '" + parts[0] + "'");
				Object val = prop.type.parseValue(parts[1]);
				styles.add(new PropertyValue(prop, val));
			}
			
		}
	}
	
	public static void main(String[] args) throws CSSParserException, IOException {
		String text = readFile("test.css");
		//System.out.println("Text: " + text);
		PropertyRuleSet rs = new PropertyRuleSet();
		parseCss(rs, text);
		System.out.println(rs);
	}
	
	private static String readFile(String pathname) throws IOException {

	    File file = new File(pathname);
	    StringBuilder fileContents = new StringBuilder((int)file.length());
	    Scanner scanner = new Scanner(file);
	    String lineSeparator = System.getProperty("line.separator");

	    try {
	        while(scanner.hasNextLine()) {        
	            fileContents.append(scanner.nextLine() + lineSeparator);
	        }
	        return fileContents.toString();
	    } finally {
	        scanner.close();
	    }
	}

}
