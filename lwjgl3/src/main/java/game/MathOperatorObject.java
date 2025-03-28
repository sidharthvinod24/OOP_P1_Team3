package game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.myg2x.game.lwjgl3.TextureObject;

public class MathOperatorObject extends TextureObject{
    private TextureRegion number;
    private String value;
    private int count;

    public MathOperatorObject(float x, float y, float s, TextureRegion[] numberRegion) {
        super(x, y, s, null);
        int numIndex;
        if(MathUtils.random(0,1) == 0) { //Equal chance for operators and digits to appear
        	numIndex = MathUtils.random(0,8);
        }
        else {
        	numIndex = MathUtils.random(9,12);
        }

        this.number = numberRegion[numIndex];


        if(numIndex < 9) {
            this.value = String.valueOf(numIndex+1);
        }
        else{
            String [] operators = {"+", "-", "*", "/"};
            this.value = operators[numIndex - 9];
        }


    }
    
    public MathOperatorObject(float x, float y, float s, TextureRegion[] numberRegion, int forcednum) {
        super(x, y, s, null);
        int numIndex = forcednum;
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
    
    public int getCount() {
        return count;
    }

    public void decrementCount() {
        if (count > 0) {
            count--;
        }
    }

    public void incrementCount() {
        count++;
    }
}