package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class KeyBindingScreen extends Scene {
	private final AbstractEngine game;
	private KeyBindingManager keyBindingManager;
	private String rebindKeyAction = null;
	private boolean awaitingNewKey = false; // Tracks if waiting for a new key
	
	private SpriteBatch batch;
    private BitmapFont font;
    
	public KeyBindingScreen(final AbstractEngine game, KeyBindingManager keyBindingManager) {
		this.game = game;
		this.keyBindingManager = keyBindingManager;
		
		batch = new SpriteBatch();
        font = new BitmapFont();
	}
	
	@Override
	public void show() {
		rebindKeyAction = null; // Reset rebinding state
		awaitingNewKey = false;
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Display instructions
        font.draw(batch, "KEY REBINDING MENU", 100, 400);
        font.draw(batch, "Press the key you want to bind for a specific action.", 100, 350);
        font.draw(batch, "Press ESC to return to the main menu without binding.", 100, 300);

     // Display current movement key bindings
        font.draw(batch, "Move Left: " + Input.Keys.toString(keyBindingManager.getKeyBinding("LEFT")), 100, 250);
        font.draw(batch, "Move Right: " + Input.Keys.toString(keyBindingManager.getKeyBinding("RIGHT")), 100, 225);
        font.draw(batch, "Move Up: " + Input.Keys.toString(keyBindingManager.getKeyBinding("UP")), 100, 200);
        font.draw(batch, "Move Down: " + Input.Keys.toString(keyBindingManager.getKeyBinding("DOWN")), 100, 175);

        // Show rebinding status
        if (rebindKeyAction != null) {
            if (awaitingNewKey) {
                font.draw(batch, "Press a new key for " + rebindKeyAction, 100, 150);
            } else {
                font.draw(batch, "Press a key to change " + rebindKeyAction, 100, 150);
            }
        }
        batch.end();
        
		// Esc pressed then exit to menu without binding a key
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
        	System.out.println("Returning to Main Menu...");
            game.SetMenuScreen();
            return;
        }
        
        updateKeyBinding();
	}

	
	
    public void setRebindAction(String action) {
        rebindKeyAction = action;
        System.out.println("Press a key to bind for " + action);
    }

    public void updateKeyBinding() {
    	// waiting for a new key to be assigned
        if (awaitingNewKey && rebindKeyAction != null) {
            for (int key = 0; key < 256; key++) {
                if (Gdx.input.isKeyJustPressed(key)) {
                    keyBindingManager.rebindKey(rebindKeyAction, key);
                    System.out.println(rebindKeyAction + " is now bound to: " + Input.Keys.toString(key));
                    rebindKeyAction = null;
                    awaitingNewKey = false; // Reset state
                    return;
                }
            }
        } 
     // selecting a key to rebind
        else if (rebindKeyAction == null) {
            for (String action : new String[]{"LEFT", "RIGHT", "UP", "DOWN"}) {
                int currentKey = keyBindingManager.getKeyBinding(action);
                if (Gdx.input.isKeyJustPressed(currentKey)) {
                    rebindKeyAction = action;
                    awaitingNewKey = true;
                    System.out.println("Rebinding: " + action + ". Press a new key.");
                    return;
                }
            }
        }
    }

    @Override
	public void resize(int width, int height) {
    	
    }

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
}
