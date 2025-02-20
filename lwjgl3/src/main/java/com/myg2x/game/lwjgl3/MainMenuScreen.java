
package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.Gdx;


public class MainMenuScreen extends Scene {

    private final AbstractEngine game;
    private BitmapFont font;

    public MainMenuScreen(final AbstractEngine game) {
        this.game = game;
        this.font = new BitmapFont(Gdx.files.internal("white.fnt"),
            Gdx.files.internal("white_0.png"), false);
        //this.font.getData().set
        this.game.font.getData().setScale(0.01f);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        //draw text. Remember that x and y are in meters
        game.font.draw(game.batch, "P1-Team 3 ", 3f, 3.5f);
        game.font.draw(game.batch, "Welcome to our abstract engine!", 3f, 3.f);
        game.font.draw(game.batch, "Click anywhere to begin!", 3f, 2.5f);
        game.batch.end();

        //Change to different screen upon left click
        if (Gdx.input.isTouched()) {
            //new GameScreen(game) or PhysicsScreen(game) or GridScreen(game)
            game.SetGridScreen();
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

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

    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub

    }

}
