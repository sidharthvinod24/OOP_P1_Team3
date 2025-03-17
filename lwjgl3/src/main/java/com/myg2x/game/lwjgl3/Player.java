package com.myg2x.game.lwjgl3;

<<<<<<< Updated upstream
import com.badlogic.gdx.math.Rectangle;
=======
>>>>>>> Stashed changes
import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
<<<<<<< Updated upstream
import com.badlogic.gdx.math.Intersector;
=======
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Input;
import com.myg2x.game.lwjgl3.Inventory;
>>>>>>> Stashed changes

public class Player extends Entity implements IMovable {
    private float presstimeL = 0;
    private float presstimeR = 0;
    private float presstimeU = 0;
    private float presstimeD = 0;

    // Return value indicates if movement was blocked by collision
    public boolean wasBlocked = false;
<<<<<<< Updated upstream

    public Player(float x, float y, float s, Texture t) {
        super(x, y, s, t);
=======
    
    private KeyBindingManager keyBindingManager;
    private Inventory inventory = new Inventory();// ✅ Added inventory system

    public Player(KeyBindingManager keyBindingManager ,float x, float y, float s, Texture t) {
        super(x, y, s, t);
        this.keyBindingManager = keyBindingManager;
        this.inventory = new Inventory();  // ✅ Ensure inventory is initialized
    }
    
    // ✅ Add a getter to access inventory from other classes
    public Inventory getInventory() {
        return inventory;
>>>>>>> Stashed changes
    }
    
    @Override
    public void move(float deltaTime, float tileSize, float offset, int gridWidth, int gridHeight) {
        // Keep existing player movement logic
        movement(tileSize, offset, gridWidth, gridHeight, new ArrayList<>());
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getPosX(), this.getPosY(), 0.5f, 0.5f);

        // ✅ Ensure inventory is drawn at the bottom
        inventory.render(batch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
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

    private boolean tryMove(float newX, float newY, Entity collision) {
        boolean wouldCollide = checkCollision(newX, newY, collision);
        boolean shiftHeld = Gdx.input.isKeyPressed(Keys.SHIFT_LEFT);

        // Update wasBlocked if there's a collision and shift isn't held
        wasBlocked = wouldCollide && !shiftHeld;

        // Allow movement if no collision or shift is held
        return !wouldCollide || shiftHeld;
    }

    private void handleMovement(float dx, float dy, float tileSize, float offset, int gridWidth, int gridHeight,
                                ArrayList<Entity> colliders, float pressTime, Runnable updatePressTime) {
<<<<<<< Updated upstream
        if (pressTime == 0.0f) {
=======
        if (pressTime == 0.0f || pressTime >= 0.5f) {
>>>>>>> Stashed changes
            float newX = this.getPosX() + dx;
            float newY = this.getPosY() + dy;

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
            }
        }
        updatePressTime.run();
    }

    public void movement(float tileSize, float offset, int gridWidth, int gridHeight, ArrayList<Entity> colliders) {
        wasBlocked = false;

        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            handleMovement(-tileSize, 0, tileSize, offset, gridWidth, gridHeight, colliders, presstimeL,
<<<<<<< Updated upstream
                    () -> presstimeL += Gdx.graphics.getDeltaTime());
=======
                    () -> { if (presstimeL > 0.5f) { presstimeL -= 0.1f; } presstimeL += delta; });
>>>>>>> Stashed changes
        } else {
            presstimeL = 0;
        }

        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            handleMovement(tileSize, 0, tileSize, offset, gridWidth, gridHeight, colliders, presstimeR,
<<<<<<< Updated upstream
                    () -> presstimeR += Gdx.graphics.getDeltaTime());
=======
                    () -> { if (presstimeR > 0.5f) { presstimeR -= 0.1f; } presstimeR += delta; });
>>>>>>> Stashed changes
        } else {
            presstimeR = 0;
        }

        if (Gdx.input.isKeyPressed(Keys.UP)) {
            handleMovement(0, tileSize, tileSize, offset, gridWidth, gridHeight, colliders, presstimeU,
<<<<<<< Updated upstream
                    () -> presstimeU += Gdx.graphics.getDeltaTime());
=======
                    () -> { if (presstimeU > 0.5f) { presstimeU -= 0.1f; } presstimeU += delta; });
>>>>>>> Stashed changes
        } else {
            presstimeU = 0;
        }

        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            handleMovement(0, -tileSize, tileSize, offset, gridWidth, gridHeight, colliders, presstimeD,
<<<<<<< Updated upstream
                    () -> presstimeD += Gdx.graphics.getDeltaTime());
        } else {
            presstimeD = 0;
        }
=======
                    () -> { if (presstimeD > 0.5f) { presstimeD -= 0.1f; } presstimeD += delta; });
        } else {
            presstimeD = 0;
        }
    }
    
    
    // ✅ Inventory functions
    public void pickUpItem(Item item) {
        inventory.addItem(item);
        System.out.println("Item added to inventory!");
>>>>>>> Stashed changes
    }

    public void dropItemToInventory(int index) {
        inventory.removeItem(index);
    }
    
    public void renderInventory(SpriteBatch batch) {
        float startX = 1f;  // Left side of screen
        float startY = 0.1f; // Bottom of screen, adjust if needed
        

        for (int i = 0; i < inventory.size(); i++) {
            batch.draw(inventory.get(i).getTexture(), startX + i * 0.6f, startY, 0.5f, 0.5f);
        }
        
        if (entity instanceof Item) {
            inventory.addItem((Item) entity);
        }

    }
    

}
