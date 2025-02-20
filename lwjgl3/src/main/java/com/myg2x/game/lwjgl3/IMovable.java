package com.myg2x.game.lwjgl3;

import java.util.ArrayList;

public interface IMovable {
    void move(float deltaTime, float tileSize, float offset, int gridWidth, int gridHeight, ArrayList<Entity> colliders);
}
