package com.majikarpets.ui.styles.selector;

import java.util.List;

import com.majikarpets.ui.Component;

public class MatchResult {
	
	public final boolean exact;
	public final Component component;
	public final Selector selector;
	
	public MatchResult(boolean exact, Component comp, Selector sel) {
		this.exact = exact;
		this.component = comp;
		this.selector = sel;
	}
	

}
