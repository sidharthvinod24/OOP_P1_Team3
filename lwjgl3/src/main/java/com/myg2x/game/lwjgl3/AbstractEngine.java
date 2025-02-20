package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class AbstractEngine extends Game{

	public SpriteBatch batch;
	public ShapeRenderer shape;
	public BitmapFont font;
	public FitViewport viewport;

	private MainMenuScreen menuScene;
	private GridScreen gridScreen;

	public void create() {
		batch = new SpriteBatch();
		shape =  new ShapeRenderer();
		// use libGDX's default font
		font = new BitmapFont();
		viewport = new FitViewport(8, 5);
		//font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
		font.setUseIntegerPositions(false);
		//Initialize scenes
		menuScene = new MainMenuScreen(this);
		gridScreen = new GridScreen(this);
		SetMenuScreen();
	}

	public void render() {
		super.render(); // important!
	}


	private void SetMenuScreen()
	{
		this.setScreen(menuScene);
	}
	public void SetGridScreen()
	{
		this.setScreen(gridScreen);
	}



	public void dispose() {
		super.dispose();
		batch.dispose();
		shape.dispose();
		font.dispose();
	}
}
