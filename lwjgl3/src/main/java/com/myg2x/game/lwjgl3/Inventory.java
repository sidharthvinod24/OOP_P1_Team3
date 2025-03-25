package com.myg2x.game.lwjgl3;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Inventory {
    private ArrayList<MathOperatorObject> items;
    private BitmapFont font;
   
    public Inventory() {
        items = new ArrayList<>();
        font = new BitmapFont();
    }

    public void addItem(MathOperatorObject item) {
    	// Check if an item with the same value already exists
        for (MathOperatorObject existingItem : items) {
            if (existingItem.getValue().equals(item.getValue())) {
                existingItem.incrementCount();
                return;
            }
        }
        items.add(item);
    }
    
	public void StartingInv(MathOperatorObject item) {
		for (MathOperatorObject existingItem : items) {
            if (existingItem.getValue().equals(item.getValue())) {
                existingItem.incrementCount();
                return;
            }
        }
        items.add(item);
        item.incrementCount();
        item.incrementCount();
    }

    public ArrayList<MathOperatorObject> getItems() {
        return items;
    }

    // Renders inventory at the bottom of the screen
    public void draw(SpriteBatch batch) {
        int index = 0;
        for (MathOperatorObject item : items) {
            float x = 0.1f + index * 50f; // X-position for each item
            float y = 0.1f;               // Y-position (bottom of the screen)
            
            // Draw the operator's texture using its getter method
            batch.draw(item.getNumber(), x, y, 50f, 50f);
            
            // Draw if itemcount > 0
            if (item.getCount() > 0) {
	            font.draw(batch, Integer.toString(item.getCount()), 40.3f + index * 50f, 55.1f);
            }
            index++;
        }
    }
}
