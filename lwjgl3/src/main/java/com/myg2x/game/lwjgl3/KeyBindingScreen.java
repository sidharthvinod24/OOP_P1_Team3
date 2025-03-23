	package com.myg2x.game.lwjgl3;
	
	import com.badlogic.gdx.Gdx;
	import com.badlogic.gdx.Input;
	import com.badlogic.gdx.graphics.GL20;
	import com.badlogic.gdx.graphics.Texture;
	import com.badlogic.gdx.graphics.g2d.BitmapFont;
	import com.badlogic.gdx.graphics.g2d.SpriteBatch;
	import com.badlogic.gdx.scenes.scene2d.InputEvent;
	import com.badlogic.gdx.scenes.scene2d.InputListener;
	import com.badlogic.gdx.scenes.scene2d.Stage;
	import com.badlogic.gdx.scenes.scene2d.ui.Skin;
	
	public class KeyBindingScreen extends Scene {
		private final AbstractEngine game;
		private KeyBindingManager keyBindingManager;
		private String rebindKeyAction = null;
		private boolean awaitingNewKey = false; // Tracks if waiting for a new key
		private String errorMessage = null;
		private float errorMessageTimer = 0;
		
		private Stage stage;
	    private Skin mySkin;
	
	    private ButtonObject exitButton;
	    
	    private BitmapFont font;
	    
	    private Texture background;
	    
		public KeyBindingScreen(final AbstractEngine game, KeyBindingManager keyBindingManager) {
			this.game = game;
			//this.keyBindingManager = keyBindingManager;
			this.background = new Texture(Gdx.files.internal("whiteboard_img2.png"));
			
			mySkin = new Skin(Gdx.files.internal("skin/level-plane-ui.json"));
			
			stage = new Stage(game.viewport);
			
	        font = new BitmapFont(Gdx.files.internal("black.fnt"),
	                Gdx.files.internal("black_0.png"), false);
	        font.getData().setScale(0.5f);
	        
	        exitButton = new ButtonObject("Confirm bindings", new Texture(Gdx.files.internal("post-it.png")), mySkin, 
					175, 35, 250, 100 , 0.15f);
	        
	        InputListener exitSettings = new InputListener() {
				@Override
	            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
	         	   	System.out.println("Rebinded LEFT to " + keyBindingManager.getKeyBinding("LEFT"));				
	         	   	game.setPauseScreen();
					return true;
	            }
			};
	        
			exitButton.setListener(exitSettings);
			
	        stage.addActor(exitButton.getButton());
		}
		
		@Override
		public void show() {
			rebindKeyAction = null; // Reset rebinding state
			awaitingNewKey = false;
			errorMessage = null;
			errorMessageTimer = 0;
		}
		
		@Override
		public void render(float delta) {
			//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			
			// Update error message timer if there's an error message
			if (errorMessage != null) {
				errorMessageTimer -= delta;
				if (errorMessageTimer <= 0) {
					errorMessage = null;
				}
			}
			
	        game.batch.begin();
	
		        game.batch.draw(background, 100, 50, 600, 400);
		
		        // Display instructions
		        font.draw(game.batch, "KEY REBINDING MENU", 120, 400);
		        font.draw(game.batch, "Press the key you want to bind for a specific action.", 120, 350);
		        font.draw(game.batch, "Press ESC to return to the main menu without binding.", 120, 300);
		
		        //Display current movement key bindings
		        font.draw(game.batch, "Move Left: " + Input.Keys.toString(KeyBindingManager.getInstance().getKeyBinding("LEFT")), 120, 250);
		        font.draw(game.batch, "Move Right: " + Input.Keys.toString(KeyBindingManager.getInstance().getKeyBinding("RIGHT")), 120, 225);
		        font.draw(game.batch, "Move Up: " + Input.Keys.toString(KeyBindingManager.getInstance().getKeyBinding("UP")), 120, 200);
		        font.draw(game.batch, "Move Down: " + Input.Keys.toString(KeyBindingManager.getInstance().getKeyBinding("DOWN")), 120, 175);
		        
		        // Display error message if exists
		        if (errorMessage != null) {
		            font.draw(game.batch, errorMessage, 120, 150);
		        }
		        // Show rebinding status
				else if (rebindKeyAction != null) {
		            if (awaitingNewKey) {
		            	font.draw(game.batch, "Press a new key for " + rebindKeyAction, 120, 150);
		            } else {
		            	font.draw(game.batch , "Press a key to change " + rebindKeyAction, 120, 150);
		            }
		        }
	        	
	        game.batch.end();
	        
	        stage.act();
			stage.draw();
	        
			// Esc pressed then exit to menu without binding a key
	//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
	//        	System.out.println("Returning to Main Menu...");
	//            game.SetGridScreen();
	//        }
	        
	        Gdx.input.setInputProcessor(stage);
	        updateKeyBinding();
		}
		
		
	    public void setRebindAction(String action) {
	        rebindKeyAction = action;
	        System.out.println("Press a key to bind for " + action);
	    }
	    
	    public void updateKeyBinding() {
	    	// Waiting for a new key to be assigned
	        if (awaitingNewKey && rebindKeyAction != null) {
	            for (int key = 0; key < 256; key++) {
	                if (Gdx.input.isKeyJustPressed(key)) {	            
	                	// Check if the key is Escape
	                    if (key == Input.Keys.ESCAPE) {
	                        errorMessage = "Cannot rebind to the ESC key";
	                        errorMessageTimer = 4.0f; // Display error for 4s
	                        System.out.println(errorMessage);
	                        return;
	                    }
	                   
	                	// Check for key binding conflict 
	                	String conflictingBinding = checkForKeyBindingConflict(key, rebindKeyAction);
	                	
	                	if (conflictingBinding != null) {
	                		// Key in use by another action, reject key rebinding
	                		errorMessage = "Key " + Input.Keys.toString(key) + " is already bound to " + conflictingBinding;
	                		errorMessageTimer = 4.0f; // Display error for 4s
	                		System.out.println(errorMessage);
	                	} else {
	                		// Key is free to use, rebind to new key
		                    KeyBindingManager.getInstance().rebindKey(rebindKeyAction, key);
		                    System.out.println(rebindKeyAction + " is now bound to: " + Input.Keys.toString(key));
		                    rebindKeyAction = null;
		                    awaitingNewKey = false; // Reset state
		                    return;
	                	}
	                }
	            }
	        }
	        
	        // Selecting a key to rebind
	        else if (rebindKeyAction == null) {
	            for (String action : new String[]{"LEFT", "RIGHT", "UP", "DOWN"}) {
	                int currentKey = KeyBindingManager.getInstance().getKeyBinding(action);
	                if (Gdx.input.isKeyJustPressed(currentKey)) {
	                    rebindKeyAction = action;
	                    awaitingNewKey = true;
	                    System.out.println("Rebinding: " + action + ". Press a new key.");
	                    return;
	                }
	            }
	        }
	    }
	    
	    private String checkForKeyBindingConflict(int keyCode, String currentAction) {
	    	String[] allActions = {"LEFT", "RIGHT", "UP", "DOWN"};
	        
	        for (String action : allActions) {
	            // Skip the current action we're trying to rebind
	            if (action.equals(currentAction)) {
	                continue;
	            }
	            
	            // Check if this key is already used by another action
	            if (KeyBindingManager.getInstance().getKeyBinding(action) == keyCode) {
	                return action;
	            }
	        }
	        // No conflict found
	        return null;
	    }
	
	    @Override
		public void resize(int width, int height) {
	    	game.viewport.update(width, height, true);
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
