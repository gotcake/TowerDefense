package com.majikarpets.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Container extends Component {
	
	protected final List<Component> children;
	
	public Container() {
		children = new ArrayList<Component>();
	}
	
	public int getNumChildren() {
		return children.size();
	}
	
	public Iterator<Component> childIterator() {
		return new ChildIterator();
	}
	
	private class ChildIterator implements Iterator<Component> {
		
		private Iterator<Component> it = children.iterator();

		@Override
		public boolean hasNext() {
			return it.hasNext();
		}

		@Override
		public Component next() {
			return it.next();
		}

		@Override
		public void remove() {
			it.remove();
		}
		
	}

}
