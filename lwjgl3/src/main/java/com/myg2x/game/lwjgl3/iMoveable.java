package com.myg2x.game.lwjgl3;

public interface IMovable {
    void move(float deltaTime, float tileSize, float offset, int gridWidth, int gridHeight);
}
