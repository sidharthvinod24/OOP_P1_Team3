package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class PauseScreen extends Scene{

	private final AbstractEngine game;
	
	private Texture background;
    private Stage stage;
    private Skin mySkin;
    
    private ButtonObject exitButton;
    private ButtonObject returnToMenuButton;
	
	
	public PauseScreen(final AbstractEngine game){
		this.game = game;
		
		mySkin = new Skin(Gdx.files.internal("skin/level-plane-ui.json"));
		
		stage = new Stage(game.viewport);
		
		
		background = new Texture(Gdx.files.internal("whiteboard_img2.png"));
		
		exitButton = new ButtonObject(new Texture(Gdx.files.internal("cross.png")), mySkin, 
				30, 30, 650, 395 , 0.20f);
		returnToMenuButton = new ButtonObject("Return to Menu", new Texture(Gdx.files.internal("post-it.png")), mySkin, 
				250, 70, 400, 200 , 0.20f);
		
		
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
               return true;
            }
		};
		
		exitButton.setListener(exitPause);
		returnToMenuButton.setListener(exitToMenu);
		
		stage.addActor(exitButton.getButton());
		stage.addActor(returnToMenuButton.getButton());
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		logic(delta);
		
		game.batch.begin();
			game.batch.draw(background, 100, 50, 600, 400);
		game.batch.end();
		
		stage.act();
		stage.draw();
		
	}
	
	public void logic(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.P)) {
            game.SetGridScreen();
            //dispose();
        }
		
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
