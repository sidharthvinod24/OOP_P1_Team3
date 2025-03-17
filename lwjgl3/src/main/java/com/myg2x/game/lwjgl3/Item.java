package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Item extends Entity {

    public Item(float x, float y, Texture texture) {
        super(x, y, 0, texture);  
    }

    // Implement render() to allow GridScreen to call it
    public void render(SpriteBatch batch) {
        batch.draw(getTexture(), getPosX(), getPosY(), getRect().width, getRect().height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        render(batch);  
    }
}
