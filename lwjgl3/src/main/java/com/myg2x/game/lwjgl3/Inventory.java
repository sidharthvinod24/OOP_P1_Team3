package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Inventory {
    private ArrayList<MathOperatorObject> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem(MathOperatorObject item) {
        items.add(item);
    }

    public ArrayList<MathOperatorObject> getItems() {
        return items;
    }

    // This draw method renders the inventory bar at the bottom of the screen.
    public void draw(SpriteBatch batch) {
        int index = 0;
        // Adjust these values as needed for your layout
        for (MathOperatorObject item : items) {
            float x = 0.1f + index * 0.6f; // X-position for each item
            float y = 0.1f;                // Y-position (bottom of the screen)
            // Draw the operator's texture using its getter method
            batch.draw(item.getNumber(), x, y, 0.5f, 0.5f);
            index++;
        }
    }
}
