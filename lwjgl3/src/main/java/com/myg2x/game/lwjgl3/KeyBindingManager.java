package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import java.util.Map;
import java.util.HashMap;


public class KeyBindingManager {
	// Key bindings hash map
    private Map<String, Integer> keyBindings;
    
    public KeyBindingManager() {
        keyBindings = new HashMap<>();
        loadDefaultKeyBindings();
    }
    
    private void loadDefaultKeyBindings() {	
        keyBindings.put("LEFT", Keys.LEFT);
        keyBindings.put("RIGHT", Keys.RIGHT);
        keyBindings.put("UP", Keys.UP);
        keyBindings.put("DOWN", Keys.DOWN);
    }
    
    public void rebindKey(String action, int newKey) {
        keyBindings.put(action, newKey);
    }

    public int getKeyBinding(String action) {
        return keyBindings.getOrDefault(action, Keys.UNKNOWN);
    }
}
