package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.mygdx.game.screens.PlayScreen;

public class WATERFOWLLAKE extends Game {

	//not used.
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	//
	public static final float PPM = 100;
	public static int WIDTH, HEIGHT;

	public SpriteBatch batch;

	@Override
	public void create () {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		//setScreen(new ObjectLayer(this));
		setScreen(new PlayScreen(this));

	}

	@Override
	public void render () {
		super.render();
		// >>This delegates the render to the active screen.
	}

	@Override
	public void dispose () {
		super.dispose();
	}

	@Override
	public void resize(int width, int height){
		super.resize(width,height);
	}

	@Override
	public void pause () {
		super.pause();
	}

	@Override
	public void resume () {
		super.resume();
	}
}
