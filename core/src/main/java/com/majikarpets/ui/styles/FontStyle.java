package com.majikarpets.ui.styles;


public enum FontStyle {
	Plain("plain"),
	Bold("bold"),
	Italic("italic"),
	Underlined("underlined"),
	;
	public final String name;
	public static FontStyle getByName(String name) {
		for (FontStyle a: values()) {
			if (a.name.equals(name))
				return a;
		}
		return null;
	}
	private FontStyle(String name) {
		this.name = name;
	}
}
