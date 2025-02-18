package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public abstract class Entity implements iMoveable {
	
	private Texture tex;
	private Rectangle box;
	
	private float posX, posY;
	private float speed;
	
	Entity()
	{
		tex = null;
		box = null;
		posX = 0;
		posY = 0;
		speed = 0;
		
	}
	Entity(float x, float y, float s, Texture t)
	{
		posX = x;
		posY = y;
		speed = s;
		tex = t;
		
		box = new Rectangle();
		box.x = x;
		box.y = y;
		box.width = 0.5f;//t.getWidth();
		box.height = 0.5f;//t.getHeight();
	}
	
	public void update()
	{
		box.x = posX;
		box.y = posY;
	}
	
	//Get set Texture
	public Texture getTexture()
	{
		return tex;
	}
	void setTexture(Texture t)
	{
		tex = t;
	}	
	
	//Get set Rectangle
	public Rectangle getRect()
	{
		return box;
	}
	void setRect(Rectangle r)
	{
		box = r;
	}
	//Get set coordinates
	public float getPosX()
	{
		return posX;
	}
	public float getPosY()
	{
		return posY;
	}
	public void setPosX(float x)
	{
		posX = x;
	}
	public void setPosY(float y)
	{
		posY = y;
	}
	
	//Get set Speed
	public void setSpeed(float s)
	{
		speed = s;
	}
	public float getSpeed()
	{
		return speed;
	}
	
	public void draw(SpriteBatch batch)
	{
		//Empty
	}
	
	public void draw(ShapeRenderer shape)
	{
		//Empty
	}
	
	public void movement() 
	{
		//Empty
	}
} //Class end