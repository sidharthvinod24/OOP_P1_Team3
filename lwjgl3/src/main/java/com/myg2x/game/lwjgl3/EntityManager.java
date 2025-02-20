package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
    private final ArrayList<Entity> entityList;

    public EntityManager() {
        entityList = new ArrayList<>();
    }

    // Update all entities
    public void update(float deltaTime, float tileSize, float offset, int gridWidth, int gridHeight) {
        try {
            for (Entity e : entityList) {
                if (e == null) continue;
                e.update();
                if (e instanceof TextureObject) {
                    ((TextureObject) e).move(deltaTime, tileSize, offset, gridWidth, gridHeight, this.getEntityList());
                }
            }
        } catch (Exception ex) {
            System.err.println("Error during entity update: " + ex.getMessage());
        }
    }

    // Render entities using SpriteBatch
    public void render(SpriteBatch batch) {
        try {
            for (Entity e : entityList) {
                if (e != null) {
                    e.draw(batch);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error during entity batch rendering: " + ex.getMessage());
        }
    }

    // Render entities using ShapeRenderer
    public void render(ShapeRenderer shape) {
        try {
            for (Entity e : entityList) {
                if (e != null) {
                    e.draw(shape);
                }
            }
        } catch (Exception ex) {
            System.err.println("Error during entity shape rendering: " + ex.getMessage());
        }
    }

    // Add entity to list
    public void addEntity(Entity e) {
        if (e == null) {
            System.err.println("Attempted to add null entity.");
            return;
        }
        entityList.add(e);
    }

    // Remove entity from list
    public void removeEntity(Entity e) {
        entityList.remove(e);
    }

    public int getListSize() {
        return entityList.size();
    }

    public ArrayList<Entity> getEntityList() {
        return entityList;
    }
}
