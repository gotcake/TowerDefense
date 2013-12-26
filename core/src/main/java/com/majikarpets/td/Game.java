package com.majikarpets.td;

import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;

public class Game extends com.badlogic.gdx.Game {
	
	MainMenuScreen mainMenuScreen;
	GameplayScreen gameplayScreen;

	@Override
	public void create () {
		mainMenuScreen = new MainMenuScreen(this);
		gameplayScreen = new GameplayScreen(this);
		this.setScreen(mainMenuScreen);
	}

}
