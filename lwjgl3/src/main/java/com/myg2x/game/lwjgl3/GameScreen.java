package com.myg2x.game.lwjgl3;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

public class GameScreen implements Screen {
	private Random rand;
	
	//World viewport
	private FitViewport viewport;
	//Touch pos for left click 
	private Vector2 touchPos;
	
	//Sprite batch and shape renderer
	private SpriteBatch batch;
	private ShapeRenderer shape;
	
	//EntityManager for entityList
	private EntityManager entityManager;
	
	//Textures
	private Texture CircleImage;
	private Texture SquareImage;
	
	private Player player;
	private TextureObject enemy;
	
	private Array<TextureObject> dropletList;
	
	
	public GameScreen(final AbstractEngine game)
	{
		entityManager = new EntityManager();
		
		rand = new Random();
		
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		viewport = new FitViewport(8, 5);
		
		touchPos = new Vector2();
		
		CircleImage = new Texture(Gdx.files.internal("Square.png"));
		
		SquareImage = new Texture(Gdx.files.internal("Circle.png"));
		
		player = new Player(0.f, 0.f, 4.f, CircleImage);
		enemy = new TextureObject(rand.nextFloat(8), 4, rand.nextFloat(2.f), SquareImage);
		
		entityManager.addEntity(player);
		
		entityManager.addEntity(enemy);
		
//		dropletList = new Array<TextureObject>();
//		
//		for(int i = 0; i < 1; i++)
//		{
//			TextureObject droplet = new TextureObject(rand.nextFloat(8), 4, rand.nextFloat(2.f), dropImage);
//			dropletList.add(droplet);
//			entityManager.addEntity(droplet);
//		}
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
		
		if(enemy.getRect().overlaps(player.getRect()))
		{
			System.out.println("Collided");
			enemy.setPosX(rand.nextFloat(7.5f));
			enemy.setPosY(rand.nextFloat(4));
			
		
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
		
		player.movement();
		
		//Screen click logic
		if (Gdx.input.isTouched()) { // If the user has clicked or tapped the screen
			 touchPos.set(Gdx.input.getX(), Gdx.input.getY()); // Get where the touch happened on screen
		     viewport.unproject(touchPos); // Convert the units to the world units of the viewport
		     player.setPosX(touchPos.x);
		     System.out.println(touchPos.x);
		     player.setPosY(touchPos.y);
	    }
		
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
		dispose();
		
	}
}
