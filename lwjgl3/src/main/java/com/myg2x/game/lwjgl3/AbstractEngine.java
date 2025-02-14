package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class AbstractEngine extends Game{
	
	public SpriteBatch batch;
	public BitmapFont font;
	public FitViewport viewport;

	private MainMenuScreen menuScene;
	private GameScreen gameScene;
	private PhysicsScreen physicsScene;

	public void create() {
		batch = new SpriteBatch();
		// use libGDX's default font
		font = new BitmapFont();
		viewport = new FitViewport(8, 5);
		
		//font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height 
		font.setUseIntegerPositions(false);
		
		menuScene = new MainMenuScreen(this);
		gameScene = new GameScreen(this);
		physicsScene = new PhysicsScreen(this);
		
		SetMenuScreen();
	}

	public void render() {
		super.render(); // important!
	}
	
	private void SetMenuScreen()
	{
		this.setScreen(menuScene);
	}
	
	public void SetPhysicsScreen()
	{
		this.setScreen(physicsScene);
	}
	
	public void SetGameScreen()
	{
		this.setScreen(new GameScreen(this));
	}
	

	public void dispose() {
		super.dispose();
		batch.dispose();
		font.dispose();
	}
}
