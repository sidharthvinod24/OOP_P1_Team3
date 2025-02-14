package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;


public class MainMenuScreen extends Scene {
	
	private final AbstractEngine game;
	private BitmapFont font;

	public MainMenuScreen(final AbstractEngine game) {
		this.game = game;
		this.font = new BitmapFont(Gdx.files.internal("white.fnt"), 
				Gdx.files.internal("white_0.png"), false);
		//this.font.getData().set
		this.game.font.getData().setScale(0.01f);
	}
	
	@Override
	public void render(float delta) {
		
		ScreenUtils.clear(Color.BLACK);
		
		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

		game.batch.begin();
			//draw text. Remember that x and y are in meters
			game.font.draw(game.batch, "Team 5 Week 6 Simulation ", 3.5f, 3.5f);
			game.font.draw(game.batch, "Welcome to Drop!!! ", 1, 1.5f);
			game.font.draw(game.batch, "Tap anywhere to begin!", 1, 1);
		game.batch.end();

		//Change to different screen upon left click
		if (Gdx.input.isTouched()) {
			game.SetPhysicsScreen();
			//game.SetGameScreen();
			dispose();
		}
		
	}
	
	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, true);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}