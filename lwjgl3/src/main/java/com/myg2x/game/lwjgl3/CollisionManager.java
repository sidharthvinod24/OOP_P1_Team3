package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Random;

public class CollisionManager {

    private final ArrayList<Entity> collisionList;

    CollisionManager() {
        collisionList = new ArrayList<Entity>();
    }

    public void addEntity(Entity e) {
        collisionList.add(e);
    }

    public void removeEntity(Entity e) {
        collisionList.remove(e);
    }
    public void handleCollision(AudioManager audioManager, Player player, float tileSize, float offset, int gridWidth, int gridHeight) {
       if (audioManager == null || player == null) {
            System.err.println("AudioManager or Player is null");
        }

        Random rand = new Random();
        try{
            for (Entity entity : collisionList) {
                // Check if the player is blocked (for collision sound)
                if (entity instanceof Player) {
                    if (((Player) entity).checkBlocked()){
                        System.out.println("Player is blocked!");
                        // we need to make sure that the audioManager is not null before we call the playSoundEffect method.
                        assert audioManager != null;
                        audioManager.playSoundEffect("collision", 0.2f);
                    }
                }
                // Check if the entity overlaps with the player
                else {
                    // We need to make sure that the player is not null before we call the getRect method.
                    assert player != null;
                    if (entity.getRect().overlaps(player.getRect())) {
                        System.out.println("Player consumed entity!");

                        // we need to make sure that the audioManager is not null before we call the playSoundEffect method.
                        assert audioManager != null;
                        audioManager.playSoundEffect("consumed", 0.2f);
                        // Move the entity to a new position on the grid
                        entity.setPosX(tileSize * rand.nextInt(gridWidth - 1) + offset);
                        entity.setPosY(tileSize * rand.nextInt(gridHeight - 1) + offset);
                    }
                }
            }
        }
        catch (Exception e){
            System.err.println("Error in CollisionManager: " + e.getMessage());
        }

    }

}
