
package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.Gdx;


public class MainMenuScreen extends Scene {

    private final AbstractEngine game;
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
        
        startButton = new ButtonObject("Start", mySkin, 260, 65, 
        		250, 200 , 0.20f);
        exitButton = new ButtonObject("Exit", mySkin, 260, 65, 
        		250, 100 , 0.20f);
        
        InputListener toMenu = new InputListener(){
               @Override
               public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
            	   dispose();
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
        
        
        startButton.setListener(toMenu);
        exitButton.setListener(exitApp);
        
        stage.addActor(startButton.getButton());
        stage.addActor(exitButton.getButton());
        
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);
        Gdx.input.setInputProcessor(stage);
        
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
	        //draw text. Remember that x and y are in meters
	        font.draw(game.batch, "P1-Team 3 ", 325f, 450f);
	        font.draw(game.batch, "Welcome to our abstract engine!", 150f, 420f);
	        font.draw(game.batch, "Click to begin!", 275f, 390f);
	        //font.draw(game.batch, "Welcome!", 200f, 325f);
        game.batch.end();
        
        stage.act();
        stage.draw();
        
        //Change to different screen upon left click
//        if (Gdx.input.isTouched()) {
//            game.SetGridScreen();           
//            dispose();
//        }

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
