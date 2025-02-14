package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class PhysicsScreen extends Scene{

	private Box2DDebugRenderer debugRenderer;
	
	private SpriteBatch batch;
	
	private World world;
	
	private float accumulator;
	
	private FitViewport viewport;
	
	private Texture bucket;
	private Player player;

	private BodyDef groundBodyDef;
	
	private Body groundBody;
	private PolygonShape groundBox;
	
	private PhysicsObject circleObject;
	private PhysicsObject playerObject;
	
	PhysicsScreen(final AbstractEngine game)
	{
		debugRenderer = new Box2DDebugRenderer();
		
		batch = new SpriteBatch();
		
		//Creates new world with y gravity of -10 (Like the real world)
		world = new World(new Vector2(0, -10), false);
		
	
		//Set viewport
		viewport = new FitViewport(9,5);
		//Camera Zoom
		//((OrthographicCamera) viewport.getCamera()).zoom = 1.2f;

		bucket = new Texture(Gdx.files.internal("bucket.png"));
		player = new Player(0.f, 2.f, 2.f, bucket);
		
		circleObject = new CircleObject(world, 0.5f, 0.f, 0.f, 1.f, 0.1f, 0.9f);
		
		playerObject = new PhysicsObject(world, 0.4f , 0.4f, player.getPosX(), player.getPosY());
		//0.4f
		//Create ground
		groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, -2.f));
		groundBody = world.createBody(groundBodyDef);
		
		groundBox = new PolygonShape();
		groundBox.setAsBox(640, 0.5f);
		groundBody.createFixture(groundBox, 10.0f);
		
		
		groundBox.dispose();
		accumulator = 0;
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		ScreenUtils.clear(0.f,0.f,0.3f,0.f);
		viewport.apply();
		
		batch.setProjectionMatrix(viewport.getCamera().combined);
		debugRenderer.render(world, viewport.getCamera().combined);
		
		batch.begin();
			player.draw(batch);
		batch.end();
		
		
		player.movement();
		playerObject.setPosition(player.getPosX(), player.getPosY());
		
		//circleObject.getBody().applyForceToCenter(new Vector2(0, 11), true);
		
		if(Gdx.input.isKeyJustPressed(Keys.E)) 
			circleObject.getBody().applyLinearImpulse(new Vector2(0, 20), circleObject.getBody().getPosition(), true);
		//Step the physics engine
		world.step(1/60.f, 6, 2);
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		viewport.update(width, height);
		
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
		// TODO Auto-generated method stub
		
	}


	
	
}
