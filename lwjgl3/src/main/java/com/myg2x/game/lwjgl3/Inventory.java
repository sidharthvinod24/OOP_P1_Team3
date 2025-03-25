package com.myg2x.game.lwjgl3;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Inventory {
    private ArrayList<MathOperatorObject> items;
    private BitmapFont font;
    private int[] itemcount = {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2};
    
    public Inventory() {

        items = new ArrayList<>();
        font = new BitmapFont();

    }

    public void addItem(MathOperatorObject item) {
    	
    	String itemValue = item.getValue();
        // Check if item is number 1-9
        if (itemValue.matches("[1-9]")) {
            int index = Integer.parseInt(itemValue) - 1;
            itemcount[index]++;
        } 
        // Check if item is operator + - * / 
        else if (itemValue.matches("[+\\-*/]")) {

            switch (itemValue) {
                case "+":
                    itemcount[9]++;
                    break;
                case "-":
                    itemcount[10]++;
                    break;
                case "*":
                    itemcount[11]++;
                    break;
                case "/":
                    itemcount[12]++;
                    break;
            }
        }
        
    }
    
	public void StartingInv(MathOperatorObject item) {
    	
		items.add(item);

    }

    public ArrayList<MathOperatorObject> getItems() {
        return items;
    }

    // Renders inventory at the bottom of the screen
    public void draw(SpriteBatch batch) {
        int index = 0;
        for (MathOperatorObject item : items) {
            float x = 0.1f + index * 50f; // X-position for each item
            float y = 0.1f;                // Y-position (bottom of the screen)
            
            // Draw the operator's texture using its getter method
            batch.draw(item.getNumber(), x, y, 50f, 50f);
            font.draw(batch, Integer.toString(itemcount[index]), 40.3f + index * 50f, 55.1f);
            index++;
        }
    }
    
    public int[] getItemcount() {
        return itemcount;
    }
}
