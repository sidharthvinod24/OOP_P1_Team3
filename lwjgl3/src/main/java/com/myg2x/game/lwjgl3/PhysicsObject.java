package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PhysicsObject {

	private Fixture fixture;
	private FixtureDef fixtureDef;
	
	private BodyDef bodyDef;
	private Body body;
	
	public PhysicsObject()
	{
		fixture = null;
		body = null;
		fixtureDef = new FixtureDef();
		bodyDef = new BodyDef();	
	}
	
	//Create Circle Object
	public PhysicsObject(World world, float radius, float x, float y, float d, float f, float r)
	{
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x, y);
		body = world.createBody(bodyDef);
		
		CircleShape circle = new CircleShape();
		circle.setRadius(radius);
		
		fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = d;
		fixtureDef.friction = f;
		fixtureDef.restitution = r; //Bounce / Elasticity
		fixture = body.createFixture(fixtureDef);
		
		circle.dispose();
	}
	
	public PhysicsObject(World world, float hX, float hY, float x, float y)
	{
		bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x, y);
		body = world.createBody(bodyDef);
		
		PolygonShape box = new PolygonShape();
		box.setAsBox(hX/4/32, hY/4/32);
		body.createFixture(box, 1.f);
		
		box.dispose();
	}

	public void setPosition(float x, float y)
	{
		this.body.setTransform(x, y, 0);
	}
	public Body getBody()
	{
		return this.body;
	}
	
}
