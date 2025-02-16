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


public class GridScreen extends Scene{
	private Random rand;
	private FitViewport viewport; //World viewport

	//Sprite batch and shape renderer
	private SpriteBatch batch;
	private ShapeRenderer shape;

	//EntityManager for entityList
	private EntityManager entityManager;

	//Textures
	private Texture bucketImage;
	private Texture dropImage;

	private Player player;
	private TextureObject enemy;


	private Grid grid;

    // Audio Manager
    private AudioManager audioManager;

	public GridScreen(final AbstractEngine game)
	{
		entityManager = new EntityManager();


        // Audio Manager
        audioManager = new AudioManager();
        audioManager.addAudio("backgroundMusic","16. Thorn in You (Calm_Roar) _【Fire Emblem Fates OST_ Map Themes Mixed】 【HQ 1080p】.mp3", true);
        audioManager.addAudio("collision","collision.mp3", false);
        audioManager.playMusic("backgroundMusic",true,0.3f);


		// grid initialization
		grid = new Grid();


		rand = new Random();

		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		viewport = new FitViewport(8, 5);

		dropImage = new Texture(Gdx.files.internal("Square.png"));

		bucketImage = new Texture(Gdx.files.internal("Circle.png"));

		player = new Player(grid.getOffset(), grid.getOffset(), 4.f, bucketImage);
		enemy = new TextureObject(grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset(),
									grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset(), rand.nextFloat()*2.f, dropImage);

		entityManager.addEntity(player);

		entityManager.addEntity(enemy);

	}


	public void resize(int width, int height)
	{
		viewport.update(width, height, true);
	}

	//Functions as update as well
	@Override
	public void render(float delta)
	{
		draw();
		input();
		logic();
	}

	//Any drawing / rendering put into here
	public void draw()
	{
		//Background
		ScreenUtils.clear(0, 0, 0.2f, 1);

		//Viewport stuff
		viewport.apply();
		batch.setProjectionMatrix(viewport.getCamera().combined);

		//SpriteBatch drawing
		batch.begin();
			//Render entities
			entityManager.render(batch);
		batch.end();

		shape.setProjectionMatrix(viewport.getCamera().combined);



		//Draw rect / hit box outline of entities
		shape.begin(ShapeType.Line);
			shape.setColor(Color.RED);
			shape.rect(player.getRect().getX(), player.getRect().getY(),
					player.getRect().getWidth(), player.getRect().getHeight());

			shape.rect(enemy.getRect().getX(), enemy.getRect().getY(),
					enemy.getRect().getWidth(), enemy.getRect().getHeight());


		shape.setColor(Color.WHITE);
		for (int row = 0; row < grid.getHeight() - 1; row++) {
		    for (int col = 0; col < grid.getWidth() - 1; col++) {

		        shape.rect(col * grid.getTileSize() + grid.getOffset(),
		        			row * grid.getTileSize() + grid.getOffset(), 1, 1);
		    }
		}
//			for(TextureObject droplet : dropletList)
//			{
//				shape.rect(droplet.getRect().getX(), droplet.getRect().getY(),
//						droplet.getRect().getWidth(), droplet.getRect().getHeight());
//			}

		shape.end();
	}

	//Program logic put here, sort of the update() function that will be called in render()
	public void logic()
	{
		//Debug portion
		//System.out.printf("Player position: %.2f, %.2f\n", player.getRect().x, player.getRect().y);
		//System.out.printf("EntityList Size: %d\n", entityManager.getListSize());
		//System.out.printf("List size %d\n", dropletList.length);

		float worldWidth = viewport.getWorldWidth();
	    float worldHeight = viewport.getWorldHeight();

		//Clamp player within viewport
	    player.setPosX(MathUtils.clamp(player.getPosX(), 0, worldWidth - 0.5f));
		player.setPosY(MathUtils.clamp(player.getPosY(), 0, worldHeight - 0.5f));

		//Update function, currently not implemented
		entityManager.update();


        if (player.wasBlocked) {
            // Play collision sound
            audioManager.playSoundEffect("collision", 0.3f);
        }

        if(enemy.getRect().overlaps(player.getRect())) {
            System.out.println("Collided");
            System.out.println("Player: " + player.getRect() + " Enemy: " + enemy.getRect());

            enemy.setPosX(grid.getTileSize() * rand.nextInt(grid.getWidth() - 1) + grid.getOffset());
            enemy.setPosY(grid.getTileSize() * rand.nextInt(grid.getHeight() - 1) + grid.getOffset());

            System.out.println("New enemy position: " + enemy.getPosX() + ", " + enemy.getPosY());
        }



        //Droplet falling logic
//		for(TextureObject droplet : dropletList)
//		{
//			droplet.setPosY(droplet.getPosY() - droplet.getSpeed() * Gdx.graphics.getDeltaTime());
//			if(droplet.getPosY() < 0)
//			{
//				droplet.setPosY(4f);
//			}
//
//			if(droplet.getRect().overlaps(player.getRect()))
//			{
//				System.out.println("Collided");
//				entityManager.removeEntity(droplet);
//				dropletList.removeValue(droplet, false);
//				droplet = null;
//
//			}
//		}

	}

	public void input()
	{

//		player.movement();
		player.gridmovement(grid.getTileSize(), grid.getOffset(), grid.getWidth(), grid.getHeight(), enemy);

		//Screen click logic
//		if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
//			 touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
//		     viewport.unproject(touchPos); // Convert the units to the world units of the viewport
//		     player.setPosX(touchPos.x);
//		     System.out.println(touchPos.x);
//		     player.setPosY(touchPos.y);
//	    }

	}


	@Override
	public void show() {
		// TODO Auto-generated method stub

	}




	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}


	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}


	@Override
	public void dispose() {
		batch.dispose();
		shape.dispose();
		dropImage.dispose();
        bucketImage.dispose();
        audioManager.dispose();


	}
}
