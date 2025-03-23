package com.myg2x.game.lwjgl3;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.Input;

public class AbstractEngine extends Game implements TimerObserver {

	public SpriteBatch batch;
	public ShapeRenderer shape;
	public BitmapFont font;
	public OrthographicCamera camera;
	public FitViewport viewport;

	private MainMenuScreen menuScene;
	private GridScreen gridScreen;
	private EquationScreen equationScreen;
	private KeyBindingScreen keyBindingScreen;
	private PauseScreen pauseScreen;
	
	private KeyBindingManager keyBindingManager;
    private FinalEquationScreen finalEquationScreen;
    // Global countdown timer
    private CountdownTimer countdownTimer;

    private boolean isRebinding = false; // Track if the key binding screen is active

    // Inventory system and pending math operator
    private Inventory inventory;
    private MathOperatorObject pendingMathOperator;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
        font = new BitmapFont();
        viewport = new FitViewport(800, 500);
        font.setUseIntegerPositions(false);

        // Initialize the inventory
        inventory = new Inventory();

        // Initialize scenes
        keyBindingManager = KeyBindingManager.getInstance();
        keyBindingScreen = new KeyBindingScreen(this, keyBindingManager);
        menuScene = new MainMenuScreen(this);
        gridScreen = new GridScreen(this);
        equationScreen = new EquationScreen(this, "1");
        finalEquationScreen = new FinalEquationScreen(this, "1");
        pauseScreen = new PauseScreen(this);

        // Initialize global countdown timer
        countdownTimer = new CountdownTimer(300);
        countdownTimer.addObserver(this);

        SetMenuScreen();
    }

    @Override
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
        float x = viewport.getWorldWidth() - 150f;
        float y = viewport.getWorldHeight() - 50f;
        font.getData().setScale(0.6f);
        font.draw(batch, timeText, x, y);
        batch.end();

//        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
//            if (getScreen() == keyBindingScreen) {
//                SetMenuScreen();
//                isRebinding = false;
//            } else if (getScreen() == gridScreen) {
//                SetKeyBindingScreen();
//                isRebinding = true;
//            }
//        }
    }

    public void SetMenuScreen() {
        this.setScreen(menuScene);
    }

    public void SetGridScreen() {
        this.setScreen(gridScreen);
        countdownTimer.start();
    }
    
    public void DrawGridScreen() {
        gridScreen.draw();
    }

    public void SetEquationScreenWithValue(String value) {
        equationScreen.setValue(value);
        this.setScreen(equationScreen);
    }

    public void SetKeyBindingScreen() {
        this.setScreen(keyBindingScreen);
    }
    
    public void setFinalEquationScreen() {
    	this.setScreen(finalEquationScreen);
    }
    
    public void setPauseScreen() {
    	this.setScreen(pauseScreen);
    }

    // Remove an entity from both the EntityManager and CollisionManager in GridScreen
    public void removeEntity(Entity e) {
        if (gridScreen != null) {
            gridScreen.removeEntity(e);
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setPendingMathOperator(MathOperatorObject mop) {
        pendingMathOperator = mop;
    }

    public MathOperatorObject getPendingMathOperator() {
        return pendingMathOperator;
    }

    public void clearPendingMathOperator() {
        pendingMathOperator = null;
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        shape.dispose();
        font.dispose();
    }

    @Override
    public void onTimerUpdate(int timeLeft) {
        // Optional: perform actions on each timer update.
    }

    @Override
    public void onTimerFinish() {
        System.out.println("Time's up! Ending game...");
        // Insert game-over logic here.
    }
}
