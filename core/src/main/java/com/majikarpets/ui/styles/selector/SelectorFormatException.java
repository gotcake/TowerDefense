package com.majikarpets.ui.styles.selector;

import com.majikarpets.ui.styles.parser.CSSParserException;

public class SelectorFormatException extends CSSParserException {
	public SelectorFormatException(String msg) {
		super(msg);
	}
	
	public SelectorFormatException() {
		super("Invalid selector format");
	}
}
