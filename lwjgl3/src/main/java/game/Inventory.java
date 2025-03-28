package game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Inventory {
    private ArrayList<MathOperatorObject> items;
    private BitmapFont font;
    private Texture circle;
   
    public Inventory() {
        items = new ArrayList<>();
        font = new BitmapFont();
        circle = new Texture(Gdx.files.internal("red_circle.png"));
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
    }

    public ArrayList<MathOperatorObject> getItems() {
        return items;
    }
    public void clearInventory()
    {
    	items.clear();
    }

    // Renders inventory at the bottom of the screen
    public void draw(SpriteBatch batch) {
        int index = 0;
        for (MathOperatorObject item : items) {
            float x = 0.1f + index * 60f; // X-position for each item
            float y = 0.1f;               // Y-position (bottom of the screen)
            
            // Draw the operator's texture using its getter method
            batch.draw(item.getNumber(), x, y, 50f, 50f);
            
            // Draw if itemcount > 0
            if (item.getCount() > 0) {
            	batch.draw(circle, 35.5f + index * 60f, 41f, 15, 15);
	            font.draw(batch, Integer.toString(item.getCount()), 40.3f + index * 60f, 55.1f);
            }
            index++;
        }
    }
    
    public void InvClear() {
    	items.clear();
    }
}
