package com.myg2x.game.lwjgl3;

import java.util.Random;
import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class GridScreen extends Scene {

	private final AbstractEngine game;
	
	private Random rand;
	private FitViewport viewport;
	private SpriteBatch batch;
	private ShapeRenderer shape;
	
	private EntityManager entityManager;
	private CollisionManager collisionManager;
	private AudioManager audioManager;

	private Texture circleImage;
	private Texture squareImage;

	private Player player;
	private TextureObject enemy;

	private Grid grid;
	

	public GridScreen(final AbstractEngine game) {
		this.game = game;
		entityManager = new EntityManager();
		collisionManager = new CollisionManager();

		//Add required audio files
		audioManager = new AudioManager();
		audioManager.addAudio("backgroundMusic", "16. Thorn in You (Calm_Roar) _【Fire Emblem Fates OST_ Map Themes Mixed】 【HQ 1080p】.mp3", true);
		audioManager.addAudio("collision", "collision.mp3", false);
		audioManager.addAudio("consumed", "consumed.mp3", false);
		audioManager.playMusic("backgroundMusic", true, 0.0f);

		// Grid initialization
		grid = new Grid();
		rand = new Random();

		//Rendering initialization 
		batch = game.batch;
		shape = game.shape;
		viewport = game.viewport;

		squareImage = new Texture(Gdx.files.internal("Square.png"));
		circleImage = new Texture(Gdx.files.internal("Circle.png"));
		
		
		player = new Player(grid.getOffset(), grid.getOffset(), 4.f, circleImage);

		entityManager.addEntity(player);
		collisionManager.addEntity(player);

		// Create 5 enemies/obstacles
		for (int i = 0; i < 30; i++) {
			enemy = new TextureObject(
					grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset(),
					grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset(),
					rand.nextFloat() * 2.f, squareImage);
			entityManager.addEntity(enemy);
			collisionManager.addEntity(enemy);
		}
	}

	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render(float delta) {
		draw();
		input();
		logic();
	}

	public void draw() {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		viewport.apply();
		batch.setProjectionMatrix(game.viewport.getCamera().combined);

		//Draw entities
		batch.begin();
			entityManager.render(batch);
		batch.end();

		shape.setProjectionMatrix(viewport.getCamera().combined);
		
		//Draw shapes
		shape.begin(ShapeType.Line);
			//Hit-boxes
//			shape.setColor(Color.RED);
//			for (Entity entity : entityManager.getEntityList()) {
//				shape.rect(entity.getRect().getX(), entity.getRect().getY(),
//						entity.getRect().getWidth(), entity.getRect().getHeight());
//			}
			//Grids
			shape.setColor(Color.WHITE);
				grid.draw(shape);
			shape.end();
	}

	public void logic() {
		//Get viewport width and height
		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();

		//Clamp player position within the viewport
		player.setPosX(MathUtils.clamp(player.getPosX(), 0, worldWidth - 0.5f));
		player.setPosY(MathUtils.clamp(player.getPosY(), 0, worldHeight - 0.5f));

		entityManager.update();

		// Check for collision with enemies
		for (Entity entity : entityManager.getEntityList()) {
			if (entity == player) {
				continue;
			}
			if (entity.getRect().overlaps(player.getRect())) {
				System.out.println("Collided");
				entity.setPosX(grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset());
				entity.setPosY(grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset());
			}
		}

		// Check for collision sounds
		collisionManager.collisionSound(audioManager, player);
	}

	public void input() {
		//Player movement
		player.gridmovement(grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), entityManager.getEntityList());
	}

	@Override
	public void show() {}

	@Override
	public void pause() {}

	@Override
	public void resume() {}

	@Override
	public void hide() {}

	@Override
	public void dispose() {
		batch.dispose();
		shape.dispose();
		squareImage.dispose();
		circleImage.dispose();
		audioManager.dispose();
	}
}
