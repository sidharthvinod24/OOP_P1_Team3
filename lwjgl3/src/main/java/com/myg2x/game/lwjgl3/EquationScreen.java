package com.myg2x.game.lwjgl3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class EquationScreen extends Scene {

    protected final AbstractEngine game;
    protected SpriteBatch batch;
    protected BitmapFont font;
    protected Random rand;

    protected Texture overlay;
    private Grid grid;
    private GlyphLayout glyphLayout = new GlyphLayout();

    protected String answer = "";
    protected String reply = "";
    protected String question = "";
    private String value;
    protected List<Object> equation;

    public EquationScreen(final AbstractEngine game, String value) {
        this.value = value;
        grid = new Grid();
        this.game = game;
        batch = new SpriteBatch();
        rand = new Random();

        font = new BitmapFont(Gdx.files.internal("Atalon.fnt"),
            Gdx.files.internal("Atalon.png"), false);
        font.getData().setScale(1.5f);
        overlay = new Texture(Gdx.files.internal("testborder.png"));

//        equation = RandomiseEqn(value);
//        question = (String) equation.get(0);
//        answer = Integer.toString((int) equation.get(1));
    }
    
    public void setValue(String value) {
        this.value = value;
        equation = RandomiseEqn(value);
        question = (String) equation.get(0);
        answer = Integer.toString((int) equation.get(1));
    }

    @Override
    public void render(float delta) {
        game.DrawGridScreen();
        logic(delta);

        game.viewport.apply();
        batch.begin();
        // Draw overlay and equation texts
        batch.draw(overlay, 175, 125, 450, 250);
        font.draw(batch, question, 225, 325);
        font.draw(batch, "ANS: ", 225, 237.5f);
        font.draw(batch, reply, 350, 237.5f);
        batch.end();
    }

    public void logic(float delta) {
        if (Gdx.input.isKeyPressed(Keys.Q)) {
            game.SetGridScreen();
        }

        // Process answer input using an InputAdapter
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                int number = -1;
                if (keycode >= Keys.NUM_0 && keycode <= Keys.NUM_9) {
                    number = keycode - Keys.NUM_0;
                } else if (keycode >= Keys.NUMPAD_0 && keycode <= Keys.NUMPAD_9) {
                    number = keycode - Keys.NUMPAD_0;
                }

                if (number != -1 && reply.length() < 4) {
                    reply += number;
                } else if (keycode == Keys.BACKSPACE) {
                    if (!reply.isEmpty()) {
                        reply = reply.substring(0, reply.length() - 1);
                    }
                } else if (keycode == Keys.ENTER) {
                    if (!reply.isEmpty()) {
                        if (Integer.parseInt(reply) == Integer.parseInt(answer)) {
                            System.out.println("CORRECT!!!");
                            // Add the pending math operator to the inventory and remove it from the grid
                            MathOperatorObject mop = game.getPendingMathOperator();
                            if (mop != null) {
                                game.getInventory().addItem(mop);
                                game.removeEntity(mop); // Remove the collected object from the grid
                                game.clearPendingMathOperator();
                            }
                        } else {
                            System.out.println("WRONG!!!");
                        }
                        game.SetGridScreen();
                        reply = "";
                    }
                }
                return false; // Allow further input processing
            }
        });
    }

    public List<Object> RandomiseEqn(String value) {
        String equation = "";
        char required;
        int randomizer;
        ArrayList<Character> operators = new ArrayList<>();
        operators.add('+');
        operators.add('-');
        operators.add('*');
        operators.add('/');

        System.out.println(value.toCharArray());

        for (char c : value.toCharArray()) {
            if (operators.contains(c)) {
                required = c;
                return MakeEqn(required, -1);
            }
        }

        int number = Integer.parseInt(value);
        randomizer = rand.nextInt(4);
        required = operators.get(randomizer);
        return MakeEqn(required, number);
    }

    public List<Object> MakeEqn(char required, int number) {
        String eqn = "";
        int ans = 0;
        int num1, num2;

        if (number == -1) { // No specific number provided
            num1 = rand.nextInt(20);
            num2 = rand.nextInt(20);
        } else { // Use the provided number
            num1 = number;
            num2 = rand.nextInt(20);
        }

        if (required == '+') {
            eqn = num1 + "" + required + num2;
            ans = num1 + num2;
        } else if (required == '-') {
            eqn = Math.max(num1, num2) + "" + required + Math.min(num1, num2);
            ans = Math.max(num1, num2) - Math.min(num1, num2);
        } else if (required == '*') {
            eqn = num1 + "×" + num2;
            ans = num1 * num2;
        } else if (required == '/') {
            num2 = num1 * (rand.nextInt(19) + 2);
            eqn = num2 + "÷" + num1;
            ans = num2 / num1;
        }

        eqn += "=?";
        System.out.println(eqn);
        System.out.println(ans);
        return Arrays.asList(eqn, ans);
    }

    @Override
    public void show() {
        this.game.font.getData().setScale(0.1f);
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        this.game.font.getData().setScale(0.01f);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        overlay.dispose();
    }
}


