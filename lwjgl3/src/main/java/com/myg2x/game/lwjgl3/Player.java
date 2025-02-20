package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity implements IMovable {
    private float presstimeL = 0;
    private float presstimeR = 0;
    private float presstimeU = 0;
    private float presstimeD = 0;

    // Return value indicates if movement was blocked by collision
    public boolean wasBlocked = false;

    public Player(float x, float y, float s, Texture t) {
        super(x, y, s, t);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getPosX(), this.getPosY(), 0.5f, 0.5f);
    }


    public boolean checkBlocked() {
        return wasBlocked;
    }


    protected boolean tryMove(float newX, float newY, Entity collision) {
        boolean wouldCollide = checkCollision(newX, newY, collision);
        boolean shiftHeld = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT);

        // Update wasBlocked if there's a collision and shift isn't held
        wasBlocked = wouldCollide && !shiftHeld;

        // Allow movement if no collision or shift is held
        return wouldCollide && !shiftHeld;
    }

    private void handleMovement(float dx, float dy, float tileSize, float offset, int gridWidth, int gridHeight,
                                ArrayList<Entity> colliders, float pressTime, Runnable updatePressTime) {
        if (pressTime == 0.0f || pressTime >= 0.5f) {

            float newX = this.getPosX() + dx;
            float newY = this.getPosY() + dy;

            boolean canMove = true;
            for (Entity e : colliders) {
                if (this != e && tryMove(newX, newY, e)) {
                    canMove = false;
                    break;
                }
            }

            if (canMove && newX >= offset && newX < (offset + gridWidth * tileSize) &&
                newY >= offset && newY < (offset + gridHeight * tileSize)) {
                this.setPosX(newX);
                this.setPosY(newY);
            }

        }

        updatePressTime.run();
    }

    public void move(float delta, float tileSize, float offset, int gridWidth, int gridHeight, ArrayList<Entity> colliders) {
        wasBlocked = false;

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            handleMovement(-tileSize, 0, tileSize, offset, gridWidth, gridHeight, colliders, presstimeL,
                    () -> {if(presstimeL > 0.5f) {
                    	presstimeL -= 0.1f;
                    	}
                    	presstimeL += delta; });

        } else {
            presstimeL = 0;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            handleMovement(tileSize, 0, tileSize, offset, gridWidth, gridHeight, colliders, presstimeR,
                    () -> {if(presstimeR > 0.5f) {
                    	presstimeR -= 0.1f;
                    	}
                    	presstimeR += delta; });

        } else {
            presstimeR = 0;
        }

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            handleMovement(0, tileSize, tileSize, offset, gridWidth, gridHeight, colliders, presstimeU,
                    () -> {if(presstimeU > 0.5f) {
                    	presstimeU -= 0.1f;
                    	}
                    	presstimeU += delta; });
        } else {
            presstimeU = 0;
        }

        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            handleMovement(0, -tileSize, tileSize, offset, gridWidth, gridHeight, colliders, presstimeD,
            		() -> {if(presstimeD > 0.5f) {
            			presstimeD -= 0.1f;
            			}
            			presstimeD += delta; });
        } else {
            presstimeD = 0;
        }



    }


}
