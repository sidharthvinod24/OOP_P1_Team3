package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Grid {
	
	private int gridWidth;
	private int gridHeight;
	private float gridOffset;
	private float gridTileSize;
	private int[][] grid;
	
	
	Grid() {
		gridWidth = 15;
		gridHeight = 9;
		gridOffset = 0.25f;
		gridTileSize = 0.5f;
		grid = new int[gridWidth][gridHeight];
	}
	
	public int getWidth() {
		return gridWidth;
	}
	
	public int getHeight() {
		return gridHeight;
	}
	
	public float getOffset() {
		return gridOffset;
	}
	
	public float getTileSize() {
		return gridTileSize;
	}
	
	public void draw(ShapeRenderer shape){
		for (int row = 0; row < gridHeight - 1; row++) {
			for (int col = 0; col < gridWidth - 1; col++) {
				shape.rect(col * gridTileSize + gridOffset,
						row * gridTileSize + gridOffset, 1, 1);
			}
		}
	}
	
	
}
