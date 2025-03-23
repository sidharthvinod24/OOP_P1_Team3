package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ButtonObject {
	
	private Button button;
	
	ButtonObject(String text, Skin skin, float xSize, float ySize, float xPos, float yPos, float scaleBy)
	{
		button = new TextButton(text, skin);
		button.setSize(xSize, ySize);
        button.setPosition(xPos, yPos);
        button.setTransform(true);
        button.scaleBy(scaleBy);
	}
	
	ButtonObject(Texture tex, Skin skin, float xSize, float ySize, float xPos, float yPos, float scaleBy)
	{
		TextureRegionDrawable drawable = new TextureRegionDrawable(tex);
		button = new ImageButton(skin);
		button.getStyle().up = drawable;
		button.getStyle().down = drawable.tint(Color.LIGHT_GRAY);
		button.setSize(xSize, ySize);
        button.setPosition(xPos, yPos);
        button.setTransform(true);
        button.scaleBy(scaleBy);
       
	}
	ButtonObject(String text, Texture tex, Skin skin, float xSize, float ySize, float xPos, float yPos, float scaleBy)
	{
		TextureRegionDrawable drawable = new TextureRegionDrawable(tex);
		button = new ImageTextButton(text,skin);
		button.getStyle().up = drawable;
		button.getStyle().down = drawable.tint(Color.LIGHT_GRAY);
		button.setSize(xSize, ySize);
        button.setPosition(xPos, yPos);
        button.setTransform(true);
        button.scaleBy(scaleBy);
       
	}
	public void setListener(InputListener listener)
	{
		this.button.addListener(listener);
	}
	
	public Button getButton()
	{
		return this.button;
	}

}
