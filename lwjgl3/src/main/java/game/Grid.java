
package game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Grid {

	private int gridWidth;
	private int gridHeight;
	private float gridOffset;
	private float gridTileSize;


	public Grid() {
		gridWidth = 15; //15
		gridHeight = 9; //9
		gridOffset = 25f; //0.25
		gridTileSize = 50f; //0.5
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

	public void draw(ShapeRenderer shape) {
		try {
			for (int row = 0; row < gridHeight - 1; row++) {
				for (int col = 0; col < gridWidth - 1; col++) {
					shape.rect(col * gridTileSize + gridOffset,
							row * gridTileSize + gridOffset, 100, 100); //100 100
				}
			}
		} catch(Exception e) {
			System.err.println("Error in Grid.draw: " + e.getMessage());
		}
	}
}
