package com.majikarpets.td.html;

import com.majikarpets.td.Game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new Game();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}

	public void log(String tag, String message, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

	public int getLogLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
