package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Random;

import game.MathOperatorObject;
import game.Player;

public class CollisionManager {

    private final ArrayList<Entity> collisionList;

    public CollisionManager() {
        collisionList = new ArrayList<>();
    }

    public void addEntity(Entity e) {
        if (e == null) {
            System.err.println("Attempted to add null entity to collision list.");
            return;
        }
        collisionList.add(e);
    }

    public void removeEntity(Entity e) {
        collisionList.remove(e);
    }

    public void handleCollision(AudioManager audioManager, Player player, float tileSize, float offset, int gridWidth, int gridHeight, AbstractEngine game) {
        if (audioManager == null || player == null) {
            System.err.println("AudioManager or Player is null");
        }
       
        Random rand = new Random();
        try {
            for (Entity entity : collisionList) {
                // Check if the entity is the player (for collision sound)
                if (entity instanceof Player) {
                    if (((Player) entity).checkBlocked()){
                        System.out.println("Player is blocked!");
                        assert audioManager != null;
                        audioManager.playSoundEffect("collision", 0.2f);
                    }
                }
                // Handle collision with a MathOperatorObject
                else {
                    assert player != null;
                    if (entity.getRect().overlaps(player.getRect())) {
                        MathOperatorObject mathEntity = (MathOperatorObject) entity;
                        System.out.println("Player collided with math operator: " + mathEntity.getValue());
                        assert audioManager != null;
                        audioManager.playSoundEffect("consumed", 0.2f);
                        
                        // Store the collided math operator as pending
                        game.setPendingMathOperator(mathEntity);
                        
                        // Switch to the EquationScreen with the operator's value
                        game.SetEquationScreenWithValue(mathEntity.getValue());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error in CollisionManager: " + e.getMessage());
        }
    }
}
