package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.RocketKrieg;

/**
 * Game over screen for Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-05-11)
 */
public class GameOver extends Menu implements Screen {
    private Sprite replayButton;
    private Sprite exitButton;
    private Sprite gameOver;
    private int score;
    private BitmapFont font;

    /**
     * Constructor for GameOver screen.
     * @param game SpriteBatch
     * @param score player score
     */
    public GameOver(final GameEntry game, int score) {
        super(game);
        this.score = score;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = AssetStorage.background2;
        replayButton = AssetStorage.replayButton;
        exitButton = AssetStorage.exitButton;
        gameOver = AssetStorage.gameOver;
        font = initializeFontTW(30, Color.WHITE);
    }

    /**
     * Renders all the textures
     * and checks for player input.
     * @param delta time since last frame
     */
    public void render(float delta) {
        super.render(delta);
        camera.position.x = Gdx.graphics.getWidth()/2;
        camera.position.y = Gdx.graphics.getHeight()/2;
        GameEntry.batch.setProjectionMatrix(camera.combined);

        //Get button measurements
        float replayWidth = replayButton.getWidth();
        float replayHeight = replayButton.getHeight();
        float exitWidth = exitButton.getWidth();
        float exitHeight = exitButton.getHeight();

        //Draw game over screen
        GameEntry.batch.draw(gameOver, Gdx.graphics.getWidth()/2 - (gameOver.getWidth()/2), Gdx.graphics.getHeight()/2 - (gameOver.getHeight()/2) + 40);
        GameEntry.batch.draw(replayButton, Gdx.graphics.getWidth()/2 - (replayWidth / 2), Gdx.graphics.getHeight()/2 - (replayHeight / 2) - 120);
        GameEntry.batch.draw(exitButton, Gdx.graphics.getWidth()/2 - (exitWidth / 2), Gdx.graphics.getHeight()/2 - (exitHeight / 2) - 200);
        font.draw(GameEntry.batch, "Your score: " + "" + score ,Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 20);

        //Pressing replay button
        if(buttonRectangle(Gdx.graphics.getWidth()/2 - (replayWidth / 2), Gdx.graphics.getHeight()/2 - (replayHeight / 2) + 120, replayWidth, replayHeight, 1)){
            game.setScreen(new RocketKrieg(game));
            dispose();
        }

        //Pressing exit button
        if(buttonRectangle(Gdx.graphics.getWidth()/2 - (exitWidth / 2), Gdx.graphics.getHeight()/2 - (exitHeight / 2) + 200, exitWidth, exitHeight, 1)){
            Gdx.app.exit();
        }

        //Exit game by pressing esc
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        GameEntry.batch.end();
    }

    /**
     * Called when GameOver becomes current screen.
     */
    public void show() {}

    /**
     * Method to resize screen.
     * @param width int
     * @param height int
     */
    public void resize(int width, int height) {}

    /**
     * Actions performed when game is paused.
     */
    public void pause() {}

    /**
     * Dispose the GameOver screen.
     */
    public void dispose() {}

    /**
     * Only called on android.
     */
    public void resume() {}
    public void hide() {}

}