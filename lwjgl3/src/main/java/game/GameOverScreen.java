package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.myg2x.game.lwjgl3.AbstractEngine;
import com.myg2x.game.lwjgl3.ButtonObject;
import com.myg2x.game.lwjgl3.Scene;
import com.myg2x.game.lwjgl3.TimerObserver;

public class GameOverScreen extends Scene implements TimerObserver{

	private final AbstractEngine game;
	
	private Texture board;
    private Stage stage;
    private Skin mySkin;
    private BitmapFont font;
    
    private ButtonObject returnToMenuButton;
    private boolean isWin;
	
	
	public GameOverScreen(final AbstractEngine game){
		this.game = game;
		this.font = new BitmapFont(Gdx.files.internal("black.fnt"),
	            Gdx.files.internal("black_0.png"), false);
	    this.game.font.getData().setScale(0.6f);
		
		mySkin = new Skin(Gdx.files.internal("skin/level-plane-ui.json"));
		
		stage = new Stage(game.viewport);
		
		board = new Texture(Gdx.files.internal("whiteboard_img2.png"));
		
		isWin = false;
		
		returnToMenuButton = new ButtonObject("Return to Menu", new Texture(Gdx.files.internal("post-it.png")), mySkin, 
				250, 70, 250, 150 , 0.15f);
		
		InputListener exitToMenu = new InputListener() {
			@Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
         	   game.SetMenuScreen();
               return true;
            }
		};

		returnToMenuButton.setListener(exitToMenu);
		
		stage.addActor(returnToMenuButton.getButton());
		
	}
	
	public void setState(boolean win)
	{
		this.isWin = win;
	}
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		game.DrawGridScreen();
		logic(delta);
		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
		
		game.batch.begin();
			game.batch.draw(board, 100, 50, 600, 400);
			font.draw(game.batch, "Game Over!", 320f, 350f);
			if(isWin)//If win, display winning text
			{
				font.draw(game.batch, "You won!!", 325f, 325f);
			}
			else //If lose, display losing text
			{
				font.draw(game.batch, "You lost!!", 320f, 325f);
			}
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
		board.dispose();
	}



	@Override
	public void onTimerUpdate(int remainingTime) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTimerFinish() {
		// TODO Auto-generated method stub
		//game.SetGameOverScreen();
	}

}
