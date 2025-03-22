package com.myg2x.game.lwjgl3;

import java.util.ArrayList;

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
    
    public void handleCollision(AudioManager audioManager, Player player, float tileSize, float offset, int gridWidth, int gridHeight, AbstractEngine game) {
       if (audioManager == null || player == null) {
            System.err.println("AudioManager or Player is null");
        }

        Random rand = new Random();
        try {
            for (Entity entity : collisionList) {
                if (entity instanceof Player) {
                    if (((Player) entity).checkBlocked()){
                        System.out.println("Player is blocked!");
                        audioManager.playSoundEffect("collision", 0.2f);
                    }
                } else {
                    assert player != null;
                    if (entity.getRect().overlaps(player.getRect())) {
                        MathOperatorObject mathEntity = (MathOperatorObject) entity;
                        System.out.println("Player consumed entity!");
                        System.out.println("Value: " + mathEntity.getValue());
                        audioManager.playSoundEffect("consumed", 0.2f);
                        entity.setPosX(tileSize * rand.nextInt(gridWidth - 1) + offset);
                        entity.setPosY(tileSize * rand.nextInt(gridHeight - 1) + offset);
                        // Switch to equation screen with the specific mathEntity
                        game.SetEquationScreenWithMathEntity(mathEntity);
                    }
                }
            }
        } catch (Exception e){
            System.err.println("Error in CollisionManager: " + e.getMessage());
           
        }
    }
}
