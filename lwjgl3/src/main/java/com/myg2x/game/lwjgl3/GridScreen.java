package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

public class GridScreen extends Scene {

    private final AbstractEngine game;
    private final Random rand;
    private final SpriteBatch batch;
    private final ShapeRenderer shape;
    private final EntityManager entityManager;
    private final CollisionManager collisionManager;
    private final AudioManager audioManager;

    private Texture background;
    private Texture circleImage;
    private final Player player;
    private TextureAtlas mathAtlas;
    private final Grid grid;

    public GridScreen(final AbstractEngine game) {
        this.game = game;
        entityManager = new EntityManager();
        collisionManager = new CollisionManager();
        audioManager = new AudioManager();
        
        // Initialize audio files
        try {
            audioManager.addAudio("backgroundMusic", "backgroundMusic.mp3", true);
            audioManager.addAudio("collision", "collision.mp3", false);
            audioManager.addAudio("consumed", "consumed.mp3", false);
            audioManager.playMusic("backgroundMusic", true, 0.1f);
        } catch (Exception e) {
            System.err.println("Error initializing audio: " + e.getMessage());
        }

        grid = new Grid();
        rand = new Random();
        batch = game.batch;
        shape = game.shape;

        try {
            circleImage = new Texture(Gdx.files.internal("Circle.png"));
            background = new Texture(Gdx.files.internal("chalkboard_background.jpg"));
        } catch (Exception e) {
            System.err.println("Error loading textures: " + e.getMessage());
        }
        
       
        
        player = new Player(grid.getOffset(), grid.getOffset(), 4.f, circleImage);
        entityManager.addEntity(player);
        collisionManager.addEntity(player);

        loadMathSprites();
        StartingInventory();
    }

    private void loadMathSprites() {
        FileHandle atlasFile = Gdx.files.internal("sprite.atlas");
        try {
            mathAtlas = new TextureAtlas(atlasFile);
            // Prepare texture regions for numbers and operators
            TextureRegion[] numberRegions = new TextureRegion[13];
            for (int i = 0; i < 9; i++) {
                numberRegions[i] = mathAtlas.findRegion(String.valueOf(i + 1));
            }
            String[] operators = {"plus", "minus", "multiplication", "divide"};
            for (int i = 9; i < 13; i++) {
                numberRegions[i] = mathAtlas.findRegion(operators[i - 9]);
            }
            System.out.println(Arrays.toString(numberRegions));
            // Add math operator entities
            for (int i = 0; i < 9; i++) {
                MathOperatorObject mathEntity = new MathOperatorObject(
                    grid.getTileSize() * rand.nextInt(grid.getWidth()) + grid.getOffset(),
                    grid.getTileSize() * rand.nextInt(grid.getHeight()) + grid.getOffset(),
                    rand.nextFloat() * 2.f,
                    numberRegions
                );
                entityManager.addEntity(mathEntity);
                collisionManager.addEntity(mathEntity);
            }
        } catch (Exception e) {
            System.err.println("Error loading sprite.atlas: " + e.getMessage());
        }
    }
    
    private void StartingInventory() {
    	FileHandle atlasFile = Gdx.files.internal("sprite.atlas");
        try {
            mathAtlas = new TextureAtlas(atlasFile);
            // Prepare texture regions for numbers and operators
            TextureRegion[] numberRegions = new TextureRegion[13];
            for (int i = 0; i < 9; i++) {
                numberRegions[i] = mathAtlas.findRegion(String.valueOf(i + 1));
            }
            String[] operators = {"plus", "minus", "multiplication", "divide"};
            for (int i = 9; i < 13; i++) {
                numberRegions[i] = mathAtlas.findRegion(operators[i - 9]);
            }
            System.out.println(Arrays.toString(numberRegions));
            // Add math operator entities
            for (int i = 0; i < 13; i++) {
                MathOperatorObject mathEntity = new MathOperatorObject(
                    grid.getTileSize() * rand.nextInt(grid.getWidth()) + grid.getOffset(),
                    grid.getTileSize() * rand.nextInt(grid.getHeight()) + grid.getOffset(),
                    rand.nextFloat() * 2.f,
                    numberRegions,
                    i
                );
                game.getInventory().StartingInv(mathEntity);
            }
        } catch (Exception e) {
            System.err.println("Error loading sprite.atlas: " + e.getMessage());
        }
    }

    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {
        draw();
        input(delta);
        logic(delta);
    }

    public void draw() {
        try {
            //ScreenUtils.clear(0, 0, 0.2f, 1); // Clear with dark blue
            game.viewport.apply();
            batch.setProjectionMatrix(game.viewport.getCamera().combined);
            batch.begin();
            batch.draw(background, 0f, 0f, 800f, 500f);
            entityManager.render(batch); // Draw all entities
            batch.end();
            shape.setProjectionMatrix(game.viewport.getCamera().combined);
            shape.begin(ShapeRenderer.ShapeType	.Line);
            shape.setColor(Color.WHITE);
            grid.draw(shape); // Draw the grid
            shape.end();
            
            // Draw the inventory bar at the bottom
            batch.begin();
            	game.getInventory().draw(batch);
            batch.end();
        } catch (Exception e) {
            System.err.println("Error in draw: " + e.getMessage());
        }
    }

    public void logic(float deltaTime) {
    	
    	if(Gdx.input.isKeyPressed(Keys.ESCAPE))
    	{
    		game.setPauseScreen();
    	}
    	
    	if(Gdx.input.isKeyPressed(Keys.A)) {
    		game.setFinalEquationScreen();
    	}
    	
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        // Clamp player position within viewport
        player.setPosX(MathUtils.clamp(player.getPosX(), 0, worldWidth - 0.5f));
        player.setPosY(MathUtils.clamp(player.getPosY(), 0, worldHeight - 0.5f));

        entityManager.update(deltaTime, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight());
        collisionManager.handleCollision(audioManager, player, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), game);
    }

    public void input(float deltaTime) {
        try {
            player.move(deltaTime, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), (ArrayList<Entity>) entityManager.getEntityList());
        } catch (Exception e) {
            System.err.println("Error processing input: " + e.getMessage());
        }
    }

    @Override
    public void show() { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

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
        background.dispose();
    }
    
    // New method to remove an entity from both the EntityManager and CollisionManager
    public void removeEntity(Entity e) {
        entityManager.removeEntity(e);
        collisionManager.removeEntity(e);
    }
    
    public void addEntity() {
    	FileHandle atlasFile = Gdx.files.internal("sprite.atlas");
        try {
            mathAtlas = new TextureAtlas(atlasFile);
            // Prepare texture regions for numbers and operators
            TextureRegion[] numberRegions = new TextureRegion[13];
            for (int i = 0; i < 9; i++) {
                numberRegions[i] = mathAtlas.findRegion(String.valueOf(i + 1));
            }
            String[] operators = {"plus", "minus", "multiplication", "divide"};
            for (int i = 9; i < 13; i++) {
                numberRegions[i] = mathAtlas.findRegion(operators[i - 9]);
            }
            System.out.println(Arrays.toString(numberRegions));
            // Add math operator entities

            MathOperatorObject mathEntity = new MathOperatorObject(
                grid.getTileSize() * rand.nextInt(grid.getWidth()) + grid.getOffset(),
                grid.getTileSize() * rand.nextInt(grid.getHeight()) + grid.getOffset(),
                rand.nextFloat() * 2.f,
                numberRegions);

            entityManager.addEntity(mathEntity);
            collisionManager.addEntity(mathEntity);
            
        } catch (Exception e) {
            System.err.println("Error loading sprite.atlas: " + e.getMessage());
        }
    }
}

