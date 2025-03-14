package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Input;

public class AbstractEngine extends Game{

	public SpriteBatch batch;
	public ShapeRenderer shape;
	public BitmapFont font;
	public FitViewport viewport;

	private MainMenuScreen menuScene;
	private GridScreen gridScreen;
	private EquationScreen equationScreen;
	private KeyBindingScreen keyBindingScreen;
	private KeyBindingManager keyBindingManager;

	private boolean isRebinding = false; // Track if the key binding screen is active

	public void create() {
		batch = new SpriteBatch();
		shape =  new ShapeRenderer();
		// use libGDX's default font
		font = new BitmapFont();
		viewport = new FitViewport(8, 5);

		//font has 15pt, but we need to scale it to our viewport by ratio of viewport height to screen height
		font.setUseIntegerPositions(false);

		//Initialize scenes
		keyBindingManager = new KeyBindingManager();
		keyBindingScreen = new KeyBindingScreen(this, keyBindingManager);
		menuScene = new MainMenuScreen(this);
		gridScreen = new GridScreen(this);
        equationScreen = new EquationScreen(this, "1");
		SetMenuScreen();
	}

	public void render() {
		super.render(); // important!

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {

            if (getScreen() == keyBindingScreen) {
                // If already in key binding menu, exit it without forcing a bind
                SetMenuScreen();
                isRebinding = false;

            } else if(getScreen() == gridScreen){
                // Open key binding menu
                SetKeyBindingScreen();
                isRebinding = true;
            }

        }
	}


	public void SetMenuScreen()
	{
		this.setScreen(menuScene);
	}
	public void SetGridScreen()
	{
		this.setScreen(gridScreen);
	}
//	public void SetEquationScreen()
//	{
//		this.setScreen(equationScreen);
//	}
	public void SetEquationScreenWithValue(String value){
        this.setScreen( new EquationScreen(this, value));
    }
	public void SetKeyBindingScreen()
	{
		this.setScreen(keyBindingScreen);
	}


	public void dispose() {
		super.dispose();
		batch.dispose();
		shape.dispose();
		font.dispose();
	}
}
