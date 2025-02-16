package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;

public class Player extends Entity {
    private float presstimeL = 0;
    private float presstimeR = 0;
    private float presstimeU = 0;
    private float presstimeD = 0;

    // Return value indicates if movement was blocked by collision
    public boolean wasBlocked = false;

    public Player(float x, float y, float s, Texture t) {
        super(x, y, s, t);
    }
    
    public boolean checkBlocked() {
    	return wasBlocked;
    }

    private boolean checkCollision(float newX, float newY, Entity collision) {
        Rectangle futureRect = new Rectangle(
            newX,
            newY,
            this.getRect().width,
            this.getRect().height
        );
        return Intersector.overlaps(futureRect, collision.getRect());
    }


    // Check if shift is held, and if so, allow movement regardless of collision
    // Else check if movement would collide with the given object and return the result
    private boolean tryMove(float newX, float newY, Entity collision) {
        boolean wouldCollide = checkCollision(newX, newY, collision);
        boolean shiftHeld = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT);

        // Update wasBlocked if there's a collision and shift isn't held
        wasBlocked = wouldCollide && !shiftHeld;

        // Allow movement if no collision or shift is held
        return !wouldCollide || shiftHeld;
    }

    public void gridmovement(float tileSize, float offset, int gridWidth, int gridHeight, ArrayList<Entity> colliders) {
        // Reset blocked state at start of movement check
        wasBlocked = false;

        // Left movement
        if(Gdx.input.isKeyPressed(Keys.LEFT)) {
            if (presstimeL == 0.0000f) {
                float newX = this.getPosX() - tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(newX, this.getPosY(), e)) {
                		break;

                    }
                }
                if (!wasBlocked && newX >= offset) {
                    this.setPosX(newX);
                }
                
                
            }
            presstimeL += Gdx.graphics.getDeltaTime();

            if (presstimeL > 0.5f) {
                presstimeL -= 0.1f;
                float newX = this.getPosX() - tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(newX, this.getPosY(), e)) {
                		break;

                    }
                }
                if (!wasBlocked && newX >= offset) {
                    this.setPosX(newX);
                }
            }
        } else {
            presstimeL = 0;
        }

        // Right movement
        if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
            if (presstimeR == 0.0000f) {
                float newX = this.getPosX() + tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(newX, this.getPosY(), e)) {
                		break;

                    }
                }
                if (!wasBlocked && newX < (offset + gridWidth * tileSize)) {
                    this.setPosX(newX);
                }
            }
            presstimeR += Gdx.graphics.getDeltaTime();

            if (presstimeR > 0.5f) {
                presstimeR -= 0.1f;
                float newX = this.getPosX() + tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(newX, this.getPosY(), e)) {
                		break;

                    }
                }
                if (!wasBlocked && newX < (offset + gridWidth * tileSize)) {
                    this.setPosX(newX);
                }
            }
        } else {
            presstimeR = 0;
        }

        // Up movement
        if(Gdx.input.isKeyPressed(Keys.UP)) {
            if (presstimeU == 0.0000f) {
                float newY = this.getPosY() + tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(this.getPosX(), newY, e)) {
                		break;

                    }
                }
                if (!wasBlocked && newY < (offset + gridHeight * tileSize)) {
                    this.setPosY(newY);
                }
            }
            presstimeU += Gdx.graphics.getDeltaTime();

            if (presstimeU > 0.5f) {
                presstimeU -= 0.1f;
                float newY = this.getPosY() + tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(this.getPosX(), newY, e)) {
                		break;

                    }
                }
                if (!wasBlocked && newY < (offset + gridHeight * tileSize)) {
                    this.setPosY(newY);
                }
            }
        } else {
            presstimeU = 0;
        }

        // Down movement
        if(Gdx.input.isKeyPressed(Keys.DOWN)) {
            if (presstimeD == 0.0000f) {
                float newY = this.getPosY() - tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(this.getPosX(), newY, e)) {
                		break;

                    }
                }
                if (!wasBlocked && newY >= offset) {
                    this.setPosY(newY);
                }
            }
            presstimeD += Gdx.graphics.getDeltaTime();

            if (presstimeD > 0.5f) {
                presstimeD -= 0.1f;
                float newY = this.getPosY() - tileSize;
                for (Entity e : colliders) {
                	if (this == e) {
                		continue;
                	}
                	else if (!tryMove(this.getPosX(), newY, e)) {
                		break;

                    }
                }
                if (!wasBlocked && newY >= offset) {
                    this.setPosY(newY);
                }
            }
        } else {
            presstimeD = 0;
        }
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getPosX(), this.getPosY(), 0.5f, 0.5f);
    }
}
