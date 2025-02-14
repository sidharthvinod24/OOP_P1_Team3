package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class CircleObject extends PhysicsObject {
	
	CircleObject(World world, float radius, float x, float y, float d, float f, float r)
	{
		super(world, radius, x, y, d, f, r);
	}
}
