package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.Input;

public class AbstractEngine extends Game implements TimerObserver {

    public SpriteBatch batch;
    public ShapeRenderer shape;
    public BitmapFont font;
    public FitViewport viewport;

    private MainMenuScreen menuScene;
    private GridScreen gridScreen;
    private EquationScreen equationScreen;
    private KeyBindingScreen keyBindingScreen;
    private KeyBindingManager keyBindingManager;

    // Global countdown timer
    private CountdownTimer countdownTimer;

    private boolean isRebinding = false; // Track if the key binding screen is active

    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
        viewport = new FitViewport(8, 5);

        font.setUseIntegerPositions(false);

        // Initialize scenes
        keyBindingManager = new KeyBindingManager();
        keyBindingScreen = new KeyBindingScreen(this, keyBindingManager);
        menuScene = new MainMenuScreen(this);
        gridScreen = new GridScreen(this);
        equationScreen = new EquationScreen(this, "1");

        // Initialize global countdown timer
        countdownTimer = new CountdownTimer(300);
        countdownTimer.addObserver(this);

        SetMenuScreen();
    }

    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        countdownTimer.update(deltaTime);

       Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();

        // Render the timer on top
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        int minutes = countdownTimer.getRemainingTime() / 60;
        int seconds = countdownTimer.getRemainingTime() % 60;
        String timeText = String.format("Time Left: %02d:%02d", minutes, seconds);

        float x = viewport.getWorldWidth() - 1.5f;
        float y = viewport.getWorldHeight() - 0.5f;

        font.getData().setScale(0.01f);
        font.draw(batch, timeText, x, y);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (getScreen() == keyBindingScreen) {
                SetMenuScreen();
                isRebinding = false;
            } else if (getScreen() == gridScreen) {
                SetKeyBindingScreen();
                isRebinding = true;
            }
        }
    }

    public void SetMenuScreen() {
        this.setScreen(menuScene);
    }

    public void SetGridScreen() {
        this.setScreen(gridScreen);
        countdownTimer.start();
    }

    public void SetEquationScreenWithValue(String value) {
        if (equationScreen != null) {
            equationScreen.dispose();
        }
        equationScreen = new EquationScreen(this, value);
        this.setScreen(equationScreen);
    }

    public void SetKeyBindingScreen() {
        this.setScreen(keyBindingScreen);
    }

    public void dispose() {
        super.dispose();
        batch.dispose();
        shape.dispose();
        font.dispose();
    }

    @Override
    public void onTimerUpdate(int timeLeft) {
        // Optionally, perform actions on each timer update.
    }

    @Override
    public void onTimerFinish() {
        System.out.println("Time's up! Ending game...");
        // Insert game-over logic here (for example, transition to a Game Over screen).
    }
}
