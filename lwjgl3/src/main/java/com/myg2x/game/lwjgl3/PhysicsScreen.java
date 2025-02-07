package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;



public class PhysicsScreen implements Screen{

	private Box2DDebugRenderer debugRenderer;
	
	private World world;
	
	private float accumulator;
	
	private FitViewport viewport;
	
	private BodyDef bodyDef;
	private BodyDef groundBodyDef;
	private Body body;
	private Body groundBody;
	private CircleShape circle;
	private PolygonShape groundBox;
	private FixtureDef fixtureDef;
	private Fixture fixture;
	
	PhysicsScreen(final AbstractEngine game)
	{
		debugRenderer = new Box2DDebugRenderer();
		
		//Creates new world with y gravity of -10 (Like the real world)
		world = new World(new Vector2(0, -10), true);
	
		//Set viewport
		viewport = new FitViewport(640, 480);
		//Camera Zoom
		//((OrthographicCamera) viewport.getCamera()).zoom = 1.2f;

		//Body definition
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(5,3);
		
		body = world.createBody(bodyDef);
		
		//"Body" in shape of circle
		circle = new CircleShape();
		circle.setRadius(40f);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 1.f; //Bounce / Elasticity
		
		fixture = body.createFixture(fixtureDef);
		
		//Create ground
		groundBodyDef = new BodyDef();
		groundBodyDef.position.set(new Vector2(0, -100));
		groundBody = world.createBody(groundBodyDef);
		
		groundBox = new PolygonShape();
		groundBox.setAsBox(640, 10.0f);
		groundBody.createFixture(groundBox, 0.0f);
		
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
		debugRenderer.render(world, viewport.getCamera().combined);
		System.out.println(viewport.getCamera().viewportWidth);
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
		circle.dispose();
		groundBox.dispose();
		
	}
	
	private void doPhysicsStep(float deltaTime) {
	    // fixed time step
	    // max frame time to avoid spiral of death (on slow devices)
//	    float frameTime = Math.min(deltaTime, 0.25f);
//	    accumulator += frameTime;
//	    while (accumulator >= Constants.TIME_STEP) {
//	        WorldManager.world.step(Constants.TIME_STEP, Constants.VELOCITY_ITERATIONS, Constants.POSITION_ITERATIONS);
//	        accumulator -= Constants.TIME_STEP;
//	    }
	}

	
	
}
