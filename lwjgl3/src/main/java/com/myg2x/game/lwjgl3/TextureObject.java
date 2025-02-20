package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

public class TextureObject extends Entity implements IMovable {
    private float moveTimer = 0; // Timer for movement intervals

    public TextureObject(float x, float y, float s, Texture t) {
        super(x, y, s, t);
    }

    @Override
    public void move(float deltaTime, float tileSize, float offset, int gridWidth, int gridHeight, ArrayList<Entity> colliders) {
        moveTimer += deltaTime;

        if (moveTimer >= MathUtils.random(1.0f, 3.0f)) { // Move every 1 to 3 seconds
            // Create a list of possible directions: 0 = UP, 1 = DOWN, 2 = LEFT, 3 = RIGHT
            List<Integer> directions = new ArrayList<>();
            directions.add(0);
            directions.add(1);
            directions.add(2);
            directions.add(3);
            Collections.shuffle(directions); // Shuffle to avoid repeating the same direction
            
            boolean moved = false;
            for (int direction : directions) {
                float newX = getPosX();
                float newY = getPosY();
                
                switch (direction) {
                    case 0: newY += tileSize; break; // Move UP
                    case 1: newY -= tileSize; break; // Move DOWN
                    case 2: newX -= tileSize; break; // Move LEFT
                    case 3: newX += tileSize; break; // Move RIGHT
                }

                // Ensure movement stays within grid boundaries and perform collision detection
                boolean canMove = true;
                for (Entity e : colliders) {
                    if (this != e && !tryMove(newX, newY, e)) {
                        canMove = false;
                        break;
                    }
                }
                if (canMove && newX >= offset && newX < (offset + gridWidth * tileSize) &&
                        newY >= offset && newY < (offset + gridHeight * tileSize)) {
                    this.setPosX(newX);
                    this.setPosY(newY);
                    moved = true;
                    break;
                }
            }
            // Reset timer regardless of whether a move was made to avoid immediate retries
            moveTimer = 0;
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTex(), this.getPosX(), this.getPosY(), 0.5f, 0.5f);
    }
}