package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;

public class GridScreen extends Scene{

	private final AbstractEngine game;

	private final Random rand;
	private final FitViewport viewport;
	private final SpriteBatch batch;
	private final ShapeRenderer shape;

	private final EntityManager entityManager;
	private final CollisionManager collisionManager;
	private final AudioManager audioManager;

	private Texture circleImage;
	private final Player player;
    private TextureAtlas mathAtlas;
	private final Grid grid;
	private KeyBindingManager keyBindingManager;


	public GridScreen(final AbstractEngine game) {
		this.game = game;
		entityManager = new EntityManager();
		collisionManager = new CollisionManager();

		audioManager = new AudioManager();

		keyBindingManager = new KeyBindingManager();
		//Add required audio files
		try {
			audioManager.addAudio("backgroundMusic", "backgroundMusic.mp3", true);
			audioManager.addAudio("collision", "collision.mp3", false);
			audioManager.addAudio("consumed", "consumed.mp3", false);
			audioManager.playMusic("backgroundMusic", true, 0.1f);
		} catch (Exception e) {
			System.err.println("Error initializing audio: " + e.getMessage());
		}


		// Grid initialization
		grid = new Grid();
		rand = new Random();

		//Rendering initialization
		batch = game.batch;
		shape = game.shape;
		viewport = game.viewport;

		try {
			circleImage = new Texture(Gdx.files.internal("Circle.png"));
		} catch (Exception e) {
			System.err.println("Error loading Circle.png: " + e.getMessage());
		}


		player = new Player(keyBindingManager, grid.getOffset(), grid.getOffset(), 4.f, circleImage);

		entityManager.addEntity(player);
		collisionManager.addEntity(player);


        loadMathSprites();
	}


    private void loadMathSprites(){
        FileHandle atlasFile = Gdx.files.internal("sprite.atlas");

        try{
            mathAtlas = new TextureAtlas(atlasFile);

            // Set the number from 1 to 9
            TextureRegion[] numberRegions = new TextureRegion[13];

            for (int i = 0; i < 9; i++) {
                numberRegions[i] = mathAtlas.findRegion(String.valueOf(i + 1));
            }

            String [] operators = {"plus", "minus", "multiplication", "divide"};
            for (int i = 9; i < 13; i++) {
                numberRegions[i] = mathAtlas.findRegion(operators[i-9]);
            }


            System.out.println(Arrays.toString(numberRegions));
            // Add 5 math entities
            for (int i = 0; i < 9; i++) {
                MathOperatorObject mathEntity = new MathOperatorObject(
                    grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset(),
                    grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset(),
                    rand.nextFloat() * 2.f,
                    numberRegions
                );
                entityManager.addEntity(mathEntity);
                collisionManager.addEntity(mathEntity);
            }

        } catch (Exception e){
            System.err.println("Error loading sprite.atlas: " + e.getMessage());
        }
    }

	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void render(float delta) {
	    draw();
	    input(delta);

	    logic(delta);


	}
	private void draw() {
		try {
			ScreenUtils.clear(0, 0, 0.2f, 1); // Clear screen with dark blue
			viewport.apply();
			batch.setProjectionMatrix(viewport.getCamera().combined);
			batch.begin();
			entityManager.render(batch); // ✅ Draw all entities
			batch.end();
			shape.setProjectionMatrix(viewport.getCamera().combined);
			shape.begin(ShapeRenderer.ShapeType.Line);
			shape.setColor(Color.WHITE);
			grid.draw(shape); // ✅ Draw the grid
			shape.end();
		} catch (Exception e) {
			System.err.println("Error in draw: " + e.getMessage());
		}
	}


	public void logic(float deltaTime) {


	    float worldWidth = viewport.getWorldWidth();
	    float worldHeight = viewport.getWorldHeight();

	    // Clamp player within the viewport
	    player.setPosX(MathUtils.clamp(player.getPosX(), 0, worldWidth - 0.5f));
	    player.setPosY(MathUtils.clamp(player.getPosY(), 0, worldHeight - 0.5f));

	    // Update only movable entities
	    entityManager.update(deltaTime, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight());

	    // Check for collisions
	    collisionManager.handleCollision(audioManager,player, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), game);
	}

	public void input(float deltaTime) {
		//Player movement
		try {
			player.move(deltaTime, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), (ArrayList<Entity>) entityManager.getEntityList());
		} catch (Exception e) {
			System.err.println("Error processing input: " + e.getMessage());
		}

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
		try {
			batch.dispose();
		} catch (Exception e) {
			System.err.println("Error disposing batch: " + e.getMessage());
		}
		try {
			shape.dispose();
		} catch (Exception e) {
			System.err.println("Error disposing shape: " + e.getMessage());
		}
		try {
			circleImage.dispose();
		} catch (Exception e) {
			System.err.println("Error disposing circleImage: " + e.getMessage());
		}
		try {
			audioManager.dispose();
		} catch (Exception e) {
			System.err.println("Error disposing audioManager: " + e.getMessage());
		}

        try {
            mathAtlas.dispose();
        } catch (Exception e) {
            System.err.println("Error disposing mathAtlas: " + e.getMessage());
        }
	}
}
