package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Random;

public class CollisionManager {

    private final ArrayList<Entity> collisionList;

    public CollisionManager() {
        collisionList = new ArrayList<>();
    }

    // Add entity to the collision list
    public void addEntity(Entity e) {
        if (e == null) {
            System.err.println("Attempted to add null entity to collision list.");
            return;
        }
        collisionList.add(e);
    }

    // Remove entity from the collision list
    public void removeEntity(Entity e) {
        collisionList.remove(e);
    }
    
    public void handleCollision(AudioManager audioManager, Player player, float tileSize, float offset, int gridWidth, int gridHeight) {
        Random rand = new Random();
        try {
            for (Entity entity : collisionList) {
                // Check if the player is blocked (for collision sound)
                if (entity instanceof Player) {
                    if (((Player) entity).checkBlocked()){
                        System.out.println("Player is blocked!");
                        audioManager.playSoundEffect("collision", 0.5f);
                    }
                }
                // Check if the entity overlaps with the player
                else if (entity.getRect().overlaps(player.getRect())) {
                    System.out.println("Player consumed entity!");
                    audioManager.playSoundEffect("consumed", 0.2f);
                    // Move the entity to a new position on the grid
                    entity.setPosX(tileSize * rand.nextInt(gridWidth - 1) + offset);
                    entity.setPosY(tileSize * rand.nextInt(gridHeight - 1) + offset);
                    entity.setSpeed(entity.getSpeed() * 2.0F); // Doubles speed each respawn
                }
            }
        } catch(Exception e) {
            System.err.println("Error in handleCollision: " + e.getMessage());
        }
    }
}