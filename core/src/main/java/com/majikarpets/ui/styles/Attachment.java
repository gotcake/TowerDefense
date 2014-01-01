package com.majikarpets.ui.styles;

public enum Attachment {
	Normal("normal"),
	Stretch("stretch"),
	Fit("fit")
	;
	public final String name;
	public static Attachment getByName(String name) {
		for (Attachment a: values()) {
			if (a.name.equals(name))
				return a;
		}
		return null;
	}
	private Attachment(String name) {
		this.name = name;
	}
}
