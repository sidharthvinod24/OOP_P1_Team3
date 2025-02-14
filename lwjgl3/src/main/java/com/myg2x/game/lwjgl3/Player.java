package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends Entity {
	
	private float presstimeL = 0;
	private float presstimeR = 0;
	private float presstimeU = 0;
	private float presstimeD = 0;
	
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
	
	public void gridmovement(float tileSize, float offset, int gridWidth, int gridHeight, TextureObject collision) {
		
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			if (presstimeL == 0.0000f) {
				if ((this.getPosX() - tileSize) >= offset) {
					if (this.getRect().contains(collision.getRect().getX() + 0.5f, collision.getRect().getY() + 0.25f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosX(this.getPosX() - tileSize);
					}

				}
			}
			presstimeL += Gdx.graphics.getDeltaTime();

			if (presstimeL > 0.5f) {
				presstimeL -= 0.1f;
				if ((this.getPosX() - tileSize) >= offset) {
					if (this.getRect().contains(collision.getRect().getX() + 0.5f, collision.getRect().getY() + 0.25f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosX(this.getPosX() - tileSize);
					}
				}
			}
		}
		else {
			presstimeL = 0;
		}
		
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			if (presstimeR == 0.0000f) {
				if ((this.getPosX() + tileSize) < (offset + gridWidth * tileSize)) {
					if (this.getRect().contains(collision.getRect().getX() - 0.25f, collision.getRect().getY() + 0.25f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosX(this.getPosX() + tileSize);
					}

				}
			}
			presstimeR += Gdx.graphics.getDeltaTime();

			if (presstimeR > 0.5f) {
				presstimeR -= 0.1f;
				if ((this.getPosX() + tileSize) < (offset + gridWidth * tileSize)) {
					if (this.getRect().contains(collision.getRect().getX() - 0.25f, collision.getRect().getY() + 0.25f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosX(this.getPosX() + tileSize);
					}
				}
			}
		}
		else {
			presstimeR = 0;
		}
		
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			if (presstimeU == 0.0000f) {
				if ((this.getPosY() + tileSize) < (offset + gridHeight * tileSize)) {
					if (this.getRect().contains(collision.getRect().getX() + 0.25f, collision.getRect().getY() - 0.25f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosY(this.getPosY() + tileSize);
					}

				}
			}
			presstimeU += Gdx.graphics.getDeltaTime();

			if (presstimeU > 0.5f) {
				presstimeU -= 0.1f;
				if ((this.getPosY() + tileSize) < (offset + gridHeight * tileSize)) {
					if (this.getRect().contains(collision.getRect().getX() + 0.25f, collision.getRect().getY() - 0.25f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosY(this.getPosY() + tileSize);
					}
				}
			}
		}
		else {
			presstimeU = 0;
		}
		
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			if (presstimeD == 0.0000f) {
				if ((this.getPosY() - tileSize) >= offset) {
					if (this.getRect().contains(collision.getRect().getX() + 0.25f, collision.getRect().getY() + 0.5f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosY(this.getPosY() - tileSize);
					}

				}
			}
			presstimeD += Gdx.graphics.getDeltaTime();

			if (presstimeD > 0.5f) {
				presstimeD -= 0.1f;
				if ((this.getPosY() - tileSize) >= offset) {
					if (this.getRect().contains(collision.getRect().getX() + 0.25f, collision.getRect().getY() + 0.5f) == false || Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
						this.setPosY(this.getPosY() - tileSize);
					}

				}
			}
		}
		else {
			presstimeD = 0;
		}

		
		
//		if(Gdx.input.isKeyJustPressed(Keys.LEFT))
//			if ((this.getPosX() - tileSize) >= offset) {
//				this.setPosX(this.getPosX() - tileSize);
//			}

			//this.getRect().setX(posX);
//		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) 
//			if ((this.getPosX() + tileSize) < (offset + gridWidth * tileSize)) {
//				this.setPosX(this.getPosX() + tileSize);
//			}

			//this.getRect().setX(posX);
//		if(Gdx.input.isKeyJustPressed(Keys.UP)) 
//			if ((this.getPosY() + tileSize) < (offset + gridHeight * tileSize)) {
//				this.setPosY(this.getPosY() + tileSize);
//			}

//		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) 
//			if ((this.getPosY() - tileSize) >= offset) {
//				this.setPosY(this.getPosY() - tileSize);
//			}

		
		
		return;
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