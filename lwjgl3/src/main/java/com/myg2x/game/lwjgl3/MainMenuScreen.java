package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;


public class MainMenuScreen extends Scene {

    private final AbstractEngine game;
    private Texture background;
    private BitmapFont font;
    private Stage stage;
    private Skin mySkin;
    private ButtonObject startButton;
    private ButtonObject exitButton;
    
    public MainMenuScreen(final AbstractEngine game) {
    	
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("white.fnt"),
            Gdx.files.internal("white_0.png"), false);
        this.game.font.getData().setScale(0.6f);
      
        //Intialisation for stage and buttons
        stage = new Stage(game.viewport);
        mySkin = new Skin(Gdx.files.internal("skin/level-plane-ui.json"));
        background = new Texture(Gdx.files.internal("blackboard_background.png"));
        
        startButton = new ButtonObject("Start", mySkin, 260, 65, 
        		250, 220 , 0.20f);
        exitButton = new ButtonObject("Exit", mySkin, 260, 65, 
        		250, 120 , 0.20f);
        
        InputListener toGame = new InputListener(){
               @Override
               public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	   dispose();
            	   game.InstantiateScreens();
            	   game.SetGridScreen();
                   return true;
               }
        };
        
        InputListener exitApp = new InputListener(){
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
         	   dispose();
         	   Gdx.app.exit();
               return true;
            }
     };
        
        
        startButton.setListener(toGame);
        exitButton.setListener(exitApp);
        
        stage.addActor(startButton.getButton());
        stage.addActor(exitButton.getButton());
        
    }

    @Override
    public void render(float delta) {

        //ScreenUtils.clear(Color.BLACK);
        Gdx.input.setInputProcessor(stage);
        
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
	        //draw text. Remember that x and y are in meters
        	game.batch.draw(background, 0, 0, 800f, 500f);
        	//game.batch.draw
	        font.draw(game.batch, "P1-Team 3 ", 325f, 440f);
	        font.draw(game.batch, "Welcome to our game!", 225f, 410f);
	        font.draw(game.batch, "Click to begin!", 275f, 380f);
	        //font.draw(game.batch, "Welcome!", 200f, 325f);
        game.batch.end();
        
        stage.act();
        stage.draw();

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
