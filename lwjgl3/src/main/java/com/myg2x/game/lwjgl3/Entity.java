package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    private Texture tex;
    private final Rectangle box;
    private float posX, posY;
    private float speed;
    private boolean consumed = false;
    
    public Entity() {
        this(0, 0, 0, null);
    }
    
    public Entity(float x, float y, float speed, Texture t) {
        posX = x;
        posY = y;
        this.speed = speed;
        tex = t;
        box = new Rectangle(x, y, 0.5f, 0.5f);
    }
    
    public void update() {
        box.x = posX;
        box.y = posY;
    }
    
    public Texture getTex() {
        return tex;
    }
    
    void setTexture(Texture t) {
        tex = t;
    }
    
    public Rectangle getRect() {
        return box;
    }
    
    public void setRect(Rectangle r) {
        box.x = r.x;
        box.y = r.y;
        box.width = r.width;
        box.height = r.height;
    }
    
    public float getPosX() {
        return posX;
    }
    
    public float getPosY() {
        return posY;
    }
    
    public void setPosX(float x) {
        posX = x;
    }
    
    public void setPosY(float y) {
        posY = y;
    }
    
    public void setSpeed(float s) {
        speed = s;
    }
    
    public float getSpeed() {
        return speed;
    }
    
    public abstract void draw(SpriteBatch batch);
    
    public void draw(ShapeRenderer shape) {
    }
    
    public void movement() {
    }
    
    protected boolean checkCollision(float newX, float newY, Entity collision) {
        Rectangle futureRect = new Rectangle(newX, newY, box.width, box.height);
        return Intersector.overlaps(futureRect, collision.getRect());
    }
    
    protected boolean tryMove(float newX, float newY, Entity collision) {
        return !checkCollision(newX, newY, collision);
    }
    
    public boolean isConsumed() {
        return consumed;
    }
    
    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }
}