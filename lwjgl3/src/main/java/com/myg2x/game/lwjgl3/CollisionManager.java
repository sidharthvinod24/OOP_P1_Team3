package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Random;

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
//            	System.out.println(player.checkBlocked());
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
    }
    
    public void collisionConsume(Player player, float tileSize, float offset, int gridWidth, int gridHeight) {
    	Random rand = new Random();
    	for (Entity entity : collisionList) {
	        if (entity == player) continue;
	        if (entity.getRect().overlaps(player.getRect())) {
	            System.out.println("Collided with player!");
	            entity.setPosX(tileSize * rand.nextInt(gridWidth - 1) + offset);
	            entity.setPosY(tileSize * rand.nextInt(gridHeight - 1) + offset);
	        }
	    }
    }
}
