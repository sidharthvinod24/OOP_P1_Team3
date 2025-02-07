package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class TextureObject extends Entity {

	TextureObject()
	{
		super();
	}
	
	TextureObject(float x, float y, float s, Texture t)
	{
		super(x,y,s,t);
	}
	
	public void draw(SpriteBatch batch)
	{
		 batch.draw(this.getTexture(), this.getPosX(), this.getPosY(), 0.5f, 0.5f);
	}
}
