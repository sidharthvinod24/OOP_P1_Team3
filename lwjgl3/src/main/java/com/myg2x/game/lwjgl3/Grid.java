package com.myg2x.game.lwjgl3;

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

}
