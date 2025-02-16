package com.myg2x.game.lwjgl3;

import java.util.ArrayList;

public class CollisionManager {

    private ArrayList<Entity> collisionList;

    CollisionManager() {
        collisionList = new ArrayList<Entity>();
    }

    public void addEntity(Entity e) {
        collisionList.add(e);
    }

    public void removeEntity(Entity e) {
        collisionList.remove(e);
    }

    public void collisionSound(AudioManager audioManager, Player player) {
        for (Entity entity : collisionList) {
            if (entity instanceof Player) {
                // Check if the player is blocked (for collision sound)
                if (((Player) entity).checkBlocked()) {
                    System.out.println("Player is blocked!");
                    audioManager.playSoundEffect("collision", 0.3f);
                }
            }

            // Check if the entity overlaps with the player and play consumed sound if so
            else if (entity.getRect().overlaps(player.getRect())) {
                System.out.println("Player consumed entity!");
                audioManager.playSoundEffect("consumed", 0.3f);
            }
        }
    }
}
