package com.myg2x.game.lwjgl3;

import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class GridScreen extends Scene {

	private Random rand;
	private FitViewport viewport;
	private SpriteBatch batch;
	private ShapeRenderer shape;
	private EntityManager entityManager;
	private CollisionManager collisionManager;

	private Texture bucketImage;
	private Texture dropImage;

	private Player player;
	private TextureObject enemy;

	private Grid grid;
	private AudioManager audioManager;

	public GridScreen(final AbstractEngine game) {
		entityManager = new EntityManager();
		collisionManager = new CollisionManager();

		// Audio Manager
		audioManager = new AudioManager();
		audioManager.addAudio("backgroundMusic", "16. Thorn in You (Calm_Roar) _【Fire Emblem Fates OST_ Map Themes Mixed】 【HQ 1080p】.mp3", true);
		audioManager.addAudio("collision", "collision.mp3", false);
		audioManager.addAudio("consumed", "consumed.mp3", false);
		audioManager.playMusic("backgroundMusic", true, 0.0f);

		// Grid initialization
		grid = new Grid();
		rand = new Random();

		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		viewport = new FitViewport(8, 5);

		dropImage = new Texture(Gdx.files.internal("Square.png"));
		bucketImage = new Texture(Gdx.files.internal("Circle.png"));

		player = new Player(grid.getOffset(), grid.getOffset(), 4.f, bucketImage);

		entityManager.addEntity(player);
		collisionManager.addEntity(player);

		// Create 5 enemies/obstacles
		for (int i = 0; i < 100; i++) {
			enemy = new TextureObject(
					grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset(),
					grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset(),
					rand.nextFloat() * 2.f, dropImage);
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
		batch.setProjectionMatrix(viewport.getCamera().combined);

		batch.begin();
		entityManager.render(batch);
		batch.end();

		shape.setProjectionMatrix(viewport.getCamera().combined);
		shape.begin(ShapeType.Line);
		shape.setColor(Color.RED);

		for (Entity entity : entityManager.getEntityList()) {
			shape.rect(entity.getRect().getX(), entity.getRect().getY(),
					entity.getRect().getWidth(), entity.getRect().getHeight());
		}

		shape.setColor(Color.WHITE);
		for (int row = 0; row < grid.getHeight() - 1; row++) {
			for (int col = 0; col < grid.getWidth() - 1; col++) {
				shape.rect(col * grid.getTileSize() + grid.getOffset(),
						row * grid.getTileSize() + grid.getOffset(), 1, 1);
			}
		}
		shape.end();
	}

	public void logic() {
		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();

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
		dropImage.dispose();
		bucketImage.dispose();
		audioManager.dispose();
	}
}
