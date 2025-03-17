package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items; // Stores the collected items

    public Inventory() {
        items = new ArrayList<>(); // Initialize inventory list
    }

    public void addItem(Item item) {
        if (item != null) {
            items.add(item);
        }
    }

    public int size() {
        return items.size();
    }

    public Item get(int index) {
        if (index >= 0 && index < items.size()) {
            return items.get(index);
        }
        return null;
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    // ✅ New render method to display inventory
    public void render(SpriteBatch batch, int screenWidth, int screenHeight) {
        float inventoryStartX = screenWidth / 4f;  // Center inventory horizontally
        float inventoryY = 10;  // Place inventory slightly above the bottom

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item != null) {
                batch.draw(item.getTexture(), inventoryStartX + (i * 50), inventoryY, 40, 40);
            }
        }
    }

}

