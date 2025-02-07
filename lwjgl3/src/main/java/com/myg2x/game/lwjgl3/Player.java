package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {
	
	
	public Player(float x, float y, float s, Texture t)
	{
		super(x, y ,s, t);
	}
	
	//Standard keyboard WASD movement
	public void movement()
	{
		if(Gdx.input.isKeyPressed(Keys.LEFT)) 
			this.setPosX(this.getPosX() - this.getSpeed() * Gdx.graphics.getDeltaTime());
			//this.getRect().setX(posX);
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) 
			this.setPosX(this.getPosX() + this.getSpeed() * Gdx.graphics.getDeltaTime());
			//this.getRect().setX(posX);
		if(Gdx.input.isKeyPressed(Keys.UP)) 
			this.setPosY(this.getPosY() + this.getSpeed() * Gdx.graphics.getDeltaTime());
		if(Gdx.input.isKeyPressed(Keys.DOWN)) 
			this.setPosY(this.getPosY() - this.getSpeed() * Gdx.graphics.getDeltaTime());
	}
	
	//Draw player
	public void draw(SpriteBatch batch)
	{
//		batch.draw(this.getTexture(), posX, posY, 
//				this.getTexture().getWidth(), this.getTexture().getHeight());
		batch.draw(this.getTexture(), posX, posY, 
				0.5f, 0.5f);
	}
}