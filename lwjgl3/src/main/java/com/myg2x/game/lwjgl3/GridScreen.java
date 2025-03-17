package com.myg2x.game.lwjgl3;
<<<<<<< Updated upstream

=======
import java.util.ArrayList;
>>>>>>> Stashed changes
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
   private Texture itemTexture;
   private Player player;
   private TextureObject enemy;
   private ArrayList<Item> items;
   private Grid grid;
   private KeyBindingManager keyBindingManager;
   private static final int INVENTORY_COLUMNS = 13;
   private float inventoryStartX;
   private float inventoryStartY;

<<<<<<< Updated upstream
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
		audioManager.addAudio("backgroundMusic", "flow-211881.mp3", true);
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
		for (int i = 0; i < 10; i++) {
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
	    logic(delta);
	}
	private void draw() {
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
	}


	public void logic(float deltaTime) {
	    float worldWidth = viewport.getWorldWidth();
	    float worldHeight = viewport.getWorldHeight();

	    // Clamp player within the viewport
	    player.setPosX(MathUtils.clamp(player.getPosX(), 0, worldWidth - 0.5f));
	    player.setPosY(MathUtils.clamp(player.getPosY(), 0, worldHeight - 0.5f));

	    // Update only movable entities
	    for (Entity entity : entityManager.getEntityList()) {
	        if (entity instanceof IMovable) {
	            ((IMovable) entity).move(deltaTime, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight());
	        }
	    }

	    entityManager.update(deltaTime, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight());

	    // Check for collisions
	    for (Entity entity : entityManager.getEntityList()) {
	        if (entity == player) continue;
	        if (entity.getRect().overlaps(player.getRect())) {
	            System.out.println("Collided with player!");
	            entity.setPosX(grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset());
	            entity.setPosY(grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset());
	        }
	    }

	    collisionManager.collisionSound(audioManager, player);
	}

	public void input() {
		//Player movement
		player.movement(grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), entityManager.getEntityList());
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
	
=======
   public GridScreen(final AbstractEngine game) {
       this.game = game;
       entityManager = new EntityManager();
       collisionManager = new CollisionManager();
       items = new ArrayList<>();
       audioManager = new AudioManager();
       keyBindingManager = new KeyBindingManager();
       
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
       viewport = game.viewport;
       
       try {
           squareImage = new Texture(Gdx.files.internal("Square.png"));
           circleImage = new Texture(Gdx.files.internal("Circle.png"));
           itemTexture = new Texture(Gdx.files.internal("item.png"));
       } catch (Exception e) {
           System.err.println("Error loading textures: " + e.getMessage());
       }
       
       player = new Player(keyBindingManager, grid.getOffset(), grid.getOffset(), 4.f, circleImage);
       entityManager.addEntity(player);
       collisionManager.addEntity(player);
       
       for (int i = 0; i < 5; i++) {
           enemy = new TextureObject(
                   grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset(),
                   grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset(),
                   rand.nextFloat() * 2.f, squareImage);
           entityManager.addEntity(enemy);
           collisionManager.addEntity(enemy);
       }
       
       for (int i = 0; i < 3; i++) {
           float x = grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset();
           float y = grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset();
           items.add(new Item(x, y, itemTexture));
       }
       
       inventoryStartX = grid.getOffset();
       inventoryStartY = grid.getOffset() - grid.getTileSize();
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
           ScreenUtils.clear(0, 0, 0.2f, 1);
           viewport.apply();
           batch.setProjectionMatrix(viewport.getCamera().combined);
           batch.begin();
           entityManager.render(batch);
           
           for (Item item : items) {
               batch.draw(item.getTexture(), item.getPosX(), item.getPosY(), grid.getTileSize(), grid.getTileSize());
           }
           
           for (int i = 0; i < player.getInventory().size(); i++) {
    Item item = player.getInventory().get(i);
    batch.draw(item.getTexture(), inventoryStartX + (i * grid.getTileSize()), inventoryStartY, grid.getTileSize(), grid.getTileSize());
>>>>>>> Stashed changes
}
           batch.end();
           
           shape.setProjectionMatrix(viewport.getCamera().combined);
           
           shape.begin(ShapeRenderer.ShapeType.Line);
           shape.setColor(Color.LIGHT_GRAY);
           grid.draw(shape);
           
           for (int col = 0; col < INVENTORY_COLUMNS; col++) {
    shape.rect(inventoryStartX + col * grid.getTileSize(), inventoryStartY, grid.getTileSize(), grid.getTileSize());
}
           
           shape.end();
       } catch (Exception e) {
           System.err.println("Error in draw: " + e.getMessage());
       }
   }
   
   public void logic(float deltaTime) {
       float worldWidth = viewport.getWorldWidth();
       float worldHeight = viewport.getWorldHeight();
       
       player.setPosX(MathUtils.clamp(player.getPosX(), 0, worldWidth - 0.5f));
       player.setPosY(MathUtils.clamp(player.getPosY(), 0, worldHeight - 0.5f));
       
       entityManager.update(deltaTime, grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight());
       
       ArrayList<Item> itemsToRemove = new ArrayList<>();
       for (Item item : items) {
           if (player.getRect().overlaps(item.getRect())) {
               player.pickUpItem(item);
               itemsToRemove.add(item);
               System.out.println("Picked up an item!");
           }
       }
       items.removeAll(itemsToRemove);
       
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
           shape.dispose();
           squareImage.dispose();
           circleImage.dispose();
           itemTexture.dispose();
           audioManager.dispose();
       } catch (Exception e) {
           System.err.println("Error disposing resources: " + e.getMessage());
       }
   }
}










