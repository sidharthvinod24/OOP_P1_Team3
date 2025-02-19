package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
    private ArrayList<Entity> entityList;

    public EntityManager() {
        entityList = new ArrayList<>();
    }

    public void update(float deltaTime, float tileSize, float offset, int gridWidth, int gridHeight) {
        for (Entity e : entityList) {
            e.update();
            if (e instanceof IMovable) {
                ((IMovable) e).move(deltaTime, tileSize, offset, gridWidth, gridHeight);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Entity e : entityList) {
            e.draw(batch);
        }
    }

    public void render(ShapeRenderer shape) {
        for (Entity e : entityList) {
            e.draw(shape);
        }
    }

    public void addEntity(Entity e) {
        entityList.add(e);
    }

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
