package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class MathOperatorObject extends TextureObject{
    private TextureRegion number;
    private String value;

    public MathOperatorObject(float x, float y, float s, TextureRegion[] numberRegion) {
        super(x, y, s, null);
        int numIndex = MathUtils.random(0, numberRegion.length - 1);
        this.number = numberRegion[numIndex];


        if(numIndex < 9) {
            this.value = String.valueOf(numIndex+1);
        }
        else{
            String [] operators = {"+", "-", "*", "/"};
            this.value = operators[numIndex - 9];
        }


    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(number, getPosX(), getPosY(), 50f, 50f);
    }


    public String getValue() {
        return value;
    }
    
 // In MathOperatorObject.java
    public TextureRegion getNumber() {
        return number;
    }

}