package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class InventoryBar {
    private final Inventory inventory;
    private final SpriteBatch batch;
    private final float startX;
    private final float startY;
    private final float slotSize;    // size of each slot (in game units)
    private final float spacing;     // spacing between slots

    public InventoryBar(Inventory inventory, SpriteBatch batch, float startX, float startY) {
        this.inventory = inventory;
        this.batch = batch;
        this.startX = startX;
        this.startY = startY;
        this.slotSize = 0.5f;
        this.spacing = 0.1f;
    }

    public void render() {
        // Render each item in a horizontal row
        for (int i = 0; i < inventory.getItems().size(); i++) {
            MathOperatorObject item = inventory.getItems().get(i);
            TextureRegion region = item.getTextureRegion();
            float x = startX + i * (slotSize + spacing);
            float y = startY;
            batch.draw(region, x, y, slotSize, slotSize);
        }
    }
}
