package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;

public class PauseScreen extends Scene{

	private final AbstractEngine game;
	
	private Texture background;
    private Stage stage;
    private Skin mySkin;
    
    private ButtonObject exitButton;
    private ButtonObject returnToMenuButton;
    private ButtonObject settingsButton;
	
	
	public PauseScreen(final AbstractEngine game){
		this.game = game;
		
		mySkin = new Skin(Gdx.files.internal("skin/level-plane-ui.json"));
		
		stage = new Stage(game.viewport);
		
		background = new Texture(Gdx.files.internal("whiteboard_img2.png"));
		
		exitButton = new ButtonObject(new Texture(Gdx.files.internal("cross.png")), mySkin, 
				30, 30, 650, 395 , 0.20f);
		
		returnToMenuButton = new ButtonObject("Return to Menu", new Texture(Gdx.files.internal("post-it.png")), mySkin, 
				250, 70, 250, 150 , 0.15f);
		settingsButton = new ButtonObject("Settings", new Texture(Gdx.files.internal("post-it.png")), mySkin, 
				250, 70, 250, 250 , 0.15f);
		
		
		InputListener exitPause = new InputListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
         	   game.SetGridScreen();
               return true;
            }
		};
		
		InputListener exitToMenu = new InputListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				game.SetMenuScreen();
				game.getInventory().clearInventory();
				return true;
            }
		};
		
		InputListener openSettings = new InputListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
         	   game.SetKeyBindingScreen();
               return true;
            }
		};
		
		
		exitButton.setListener(exitPause);
		returnToMenuButton.setListener(exitToMenu);
		settingsButton.setListener(openSettings);
		
		stage.addActor(exitButton.getButton());
		stage.addActor(returnToMenuButton.getButton());
		stage.addActor(settingsButton.getButton());	
		
	}
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		logic(delta);
		ScreenUtils.clear(0, 0, 0, 1); // Clear with dark blue
		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		
		game.batch.begin();
			game.batch.draw(background, 100, 50, 600, 400);
		game.batch.end();
		
		stage.act();
		stage.draw();
		
	}
	
	public void logic(float delta) {
		Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		game.viewport.update(width, height, true);
		
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
