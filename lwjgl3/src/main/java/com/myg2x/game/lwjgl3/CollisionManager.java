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
    
    public void collisionSound(AudioManager audioManager, Player player) {
        if (audioManager == null || player == null) {
            System.err.println("AudioManager or Player is null in collisionSound.");
            return;
        }
        try {
            for (Entity entity : collisionList) {
                if (entity == null) continue;
                if (entity instanceof Player) {
                    // Check if the player is blocked (for collision sound)
                    if (((Player) entity).checkBlocked()) {
                        System.out.println("Player is blocked!");
                        audioManager.playSoundEffect("collision", 0.5f);
                    }
                }
                // Check if the entity overlaps with the player and play consumed sound if so
                else if (entity.getRect().overlaps(player.getRect())) {
                    System.out.println("Player consumed entity!");
                    audioManager.playSoundEffect("consumed", 0.2f);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in collisionSound: " + e.getMessage());
        }
    }
    
    public void collisionConsume(Player player, float tileSize, float offset, int gridWidth, int gridHeight) {
        if (player == null) {
            System.err.println("Player is null in collisionConsume.");
            return;
        }
        Random rand = new Random();
        try {
            for (Entity entity : collisionList) {
                if (entity == player) continue;
                if (entity.getRect().overlaps(player.getRect())) {
                    System.out.println("Collided with player!");
                    entity.setPosX(tileSize * rand.nextInt(gridWidth - 1) + offset);
                    entity.setPosY(tileSize * rand.nextInt(gridHeight - 1) + offset);
                }
            }
        } catch (Exception e) {
            System.err.println("Error in collisionConsume: " + e.getMessage());
        }
    }
}
