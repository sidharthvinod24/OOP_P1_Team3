package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
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
        audioManager = new AudioManager();
        try {
            //Add required audio files
            audioManager.addAudio("backgroundMusic", "flow-211881.mp3", true);
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
            squareImage = new Texture(Gdx.files.internal("Square.png"));
        } catch (Exception e) {
            System.err.println("Error loading Square.png: " + e.getMessage());
        }
        try {
            circleImage = new Texture(Gdx.files.internal("Circle.png"));
        } catch (Exception e) {
            System.err.println("Error loading Circle.png: " + e.getMessage());
        }
        
        player = new Player(grid.getOffset(), grid.getOffset(), 4.f, circleImage);

        entityManager.addEntity(player);
        collisionManager.addEntity(player);

        // Create 5 enemies/obstacles
        for (int i = 0; i < 5; i++) {
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
        input(delta);
        logic(delta);
    }
    
    private void draw() {
        try {
            ScreenUtils.clear(0, 0, 0.2f, 1); // Clear screen with dark blue
            viewport.apply();
            batch.setProjectionMatrix(game.viewport.getCamera().combined);
            batch.begin();
                entityManager.render(batch); // ✅ Draw all entities
            batch.end();
            shape.setProjectionMatrix(game.viewport.getCamera().combined);
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

        // Check for collisions using teammate's handleCollision method
        collisionManager.handleCollision(audioManager, player, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight());
    }

    public void input(float deltaTime) {
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
            squareImage.dispose();
        } catch (Exception e) {
            System.err.println("Error disposing squareImage: " + e.getMessage());
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
    }
}