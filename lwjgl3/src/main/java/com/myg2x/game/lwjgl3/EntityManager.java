 package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class EntityManager {
	
	//List of all entities
	private ArrayList<Entity> entityList;
	
	EntityManager()
	{
		entityList = new ArrayList<Entity>();
	}
	
	//Call all entities update() function
	public void update()
	{
		for(Entity e : entityList)
		{
			e.update();
		}
	}
	
	public void render(SpriteBatch batch)
	{
		for (Entity e : entityList)
		{
			e.draw(batch);
		}
	}
	public void render(ShapeRenderer shape)
	{
		for (Entity e: entityList)
		{
			e.draw(shape);
		}
	}
	//Add entity to list
	public void addEntity(Entity e)
	{
		entityList.add(e);
	}
	
	//Remove entity from list
	public void removeEntity(Entity e)
	{
		entityList.remove(e);
	}
	//Get list size
	public int getListSize()
	{
		return entityList.size();
	}
	

	
}
