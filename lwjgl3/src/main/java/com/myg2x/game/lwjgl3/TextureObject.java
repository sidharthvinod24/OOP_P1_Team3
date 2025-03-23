package com.myg2x.game.lwjgl3;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class TextureObject extends Entity implements IMovable {
    private float moveTimer = 0; // Timer for movement intervals

    public TextureObject(float x, float y, float s, Texture t) {
        super(x, y, s, t);
    }

    @Override
    public void move(float deltaTime, float tileSize, float offset, int gridWidth, int gridHeight, ArrayList<Entity> colliders) {
        moveTimer += deltaTime;

        if (moveTimer >= MathUtils.random(1.0f, 3.0f)) { // Move every 1 second
            int direction = MathUtils.random(3); // 0 = UP, 1 = DOWN, 2 = LEFT, 3 = RIGHT
            float newX = getPosX();
            float newY = getPosY();

            boolean canMove = true;

            switch (direction) {
                case 0: newY += tileSize; break; // Move UP
                case 1: newY -= tileSize; break; // Move DOWN
                case 2: newX -= tileSize; break; // Move LEFT
                case 3: newX += tileSize; break; // Move RIGHT
            }

            // Ensure movement stays within grid boundaries an collision detection

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

            moveTimer = 0; // Reset timer
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getPosX(), this.getPosY(), 50f, 50f);
    }
}
