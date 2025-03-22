package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.myg2x.game.lwjgl3.KeyBindingScreen;
import com.myg2x.game.lwjgl3.KeyBindingManager;



public class AbstractEngine extends Game {
    public SpriteBatch batch;
    public ShapeRenderer shape;
    public BitmapFont font;
    public FitViewport viewport;

    private MainMenuScreen menuScene;
    private GridScreen gridScreen;
    private EquationScreen equationScreen;
    private KeyBindingScreen keyBindingScreen;
    private KeyBindingManager keyBindingManager;

    // NEW: Inventory system fields
    public Inventory inventory;
    public InventoryBar inventoryBar;

    private boolean isRebinding = false; // Track if the key binding screen is active

    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);
        font.setUseIntegerPositions(false);

        // Initialize scenes
        keyBindingManager = new KeyBindingManager();
        keyBindingScreen = new KeyBindingScreen(this, keyBindingManager);
        menuScene = new MainMenuScreen(this);
        gridScreen = new GridScreen(this);
        // The equation screen created dynamically 

        // Initialize inventory and inventory bar.
        inventory = new Inventory();
        // Position the inventory bar at the bottom left 
        inventoryBar = new InventoryBar(inventory, batch, 0.25f, 0.0f);

        SetMenuScreen();
    }

    public void render() {
        super.render();
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (getScreen() == keyBindingScreen) {
                SetMenuScreen();
                isRebinding = false;
            } else if(getScreen() == gridScreen){
                SetKeyBindingScreen();
                isRebinding = true;
            }
        }
    }

    public void SetMenuScreen() {
        this.setScreen(menuScene);
    }
    public void SetGridScreen() {
        this.setScreen(gridScreen);
    }
    
    // Method to switch to EquationScreen using a MathOperatorObject
    public void SetEquationScreenWithMathEntity(MathOperatorObject mathEntity) {
        this.setScreen(new EquationScreen(this, mathEntity));
    }
    
    public void SetKeyBindingScreen() {
        this.setScreen(keyBindingScreen);
    }

    public void dispose() {
        super.dispose();
        batch.dispose();
        shape.dispose();
        font.dispose();
    }
}
