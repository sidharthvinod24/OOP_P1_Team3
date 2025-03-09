package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class EquationScreen extends Scene {

	private final AbstractEngine game;
	private SpriteBatch batch;
    private BitmapFont font;
    private Random rand;
    
    private Texture overlay;
    private Grid grid;
    private GlyphLayout glyphLayout = new GlyphLayout();
    
    private String answer = "";
    private String reply = "";
    private String question = "";
    private List<Object> equation;
    
    public EquationScreen(final AbstractEngine game) {
    	grid = new Grid();
        this.game = game;
        batch = new SpriteBatch();
        
        rand = new Random();
        
        font = new BitmapFont(Gdx.files.internal("chalk.fnt"),
                Gdx.files.internal("chalk.png"), false);
        
        font.getData().setScale(1.5f);
        overlay = new Texture(Gdx.files.internal("testborder.png"));
        
        equation = RandomiseEqn();
        question = (String) equation.get(0);
        answer = Integer.toString((int) equation.get(1));
        
        
    }
    

	@Override
	public void render(float delta) {
		logic(delta);



        batch.begin();
        //draw text. Remember that x and y are in meters
        batch.draw(overlay, 175, 125, 450, 250);
        font.draw(batch, question, 225, 325);
        font.draw(batch, "ANS: ", 225, 237.5f);
        font.draw(batch, reply, 350, 237.5f);
        batch.end();
		
	}
	
	public void logic(float delta) {
		//RandomiseEqn();
		if(Gdx.input.isKeyPressed(Keys.A)) {
			game.SetGridScreen();
		}
		
		
		// Processing answer
		Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // Check if the key is a number (0-9) or backspace
                if (keycode >= Input.Keys.NUM_0 && keycode <= Input.Keys.NUM_9 && reply.length() < 4) {
//                    System.out.println("Pressed: " + Input.Keys.toString(keycode));
                    reply += Input.Keys.toString(keycode);
                    return true; // Input handled
                }
                else if (keycode == Input.Keys.BACKSPACE) {
//                	System.out.println("Pressed: " + Input.Keys.toString(keycode));
                	if (reply.length() > 0) {
                		reply = reply.substring(0, reply.length() - 1);
                	}
                }
                
                else if (keycode == Input.Keys.ENTER) {
                	if(reply.length() > 0) {
            			if(Integer.parseInt(reply) == Integer.parseInt(answer)) {
            				System.out.println("CORRECT!!!");
            			}
            			else {
            				System.out.println("WRONG!!!");

            			}
            			game.SetGridScreen();
        				
        				equation = RandomiseEqn();
        		        question = (String) equation.get(0);
        		        answer = Integer.toString((int) equation.get(1));
        		        reply = "";
            			
            			
            		}
                }
                return false; // Pass input to other handlers
            }
        });
		
		
	}
	
	public List<Object> RandomiseEqn() {
		String equation = "";
		char required;
		int randomiser;
		//TEMPORARY FOR RANDOMISING TESTING
		ArrayList<Character> operators = new ArrayList<Character>();
		operators.add('+');
		operators.add('-');
		operators.add('*');
		operators.add('/');
		
		randomiser = rand.nextInt(14);
		
		if (randomiser < 10) {
			required = (char) randomiser;

		}
		else {
			required = operators.get(randomiser-10);
		}
		
		if(rand.nextInt(2) == 1) {
			return MakeEqn('+');
		}
		return MakeEqn('-');
	}
	
	public List<Object> MakeEqn(char required) {
		String eqn = "";
		int ans = 0;
		
		if(required == '-' || required == '+') {
			int num1 = rand.nextInt(0, 99);
			int num2 = rand.nextInt(0, 99);
			
			if(required == '+') {
				eqn = num1 + "" + required + "" + num2; 
				ans = num1 + num2;
				
			}
			
			else if(required == '-') {
				eqn = Math.max(num1, num2) + "" + required + "" + Math.min(num1, num2);
				ans = Math.max(num1, num2) - Math.min(num1, num2);
			}
			
		}
		
		
		
		
		eqn += "=?";
		System.out.println(eqn);
		System.out.println(ans);
		return Arrays.asList(eqn, ans);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		this.game.font.getData().setScale(0.1f);
	}
	
	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		this.game.font.getData().setScale(0.01f);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
