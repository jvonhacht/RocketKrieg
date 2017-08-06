package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.menus.MainMenu;
import com.game.menus.Menu;

/**
 * Settings screen for Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-07-30)
 */
public class Settings extends Menu implements Screen {
    private Sprite settingsBox;
    private Sprite checkmark;
    public static boolean musicEnabled = true;
    public static boolean sfxEnabled = true;
    private boolean vSyncEnabled = true;

    /**
     * Constructor for Settings screen.
     *
     * @param game SpriteBatch
     */
    public Settings(final GameEntry game) {
        super(game);
        background = AssetStorage.background1;
        settingsBox = AssetStorage.settingsBox;
        checkmark = AssetStorage.checkmark;
    }

    /**
     * Renders all the textures
     * and checks for player input.
     *
     * @param delta time since last frame
     */
    public void render(float delta) {
        super.render(delta);

        GameEntry.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameEntry.batch.draw(settingsBox, Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2, Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2);

        //Checkmarks
        if(vSyncEnabled){
            GameEntry.batch.draw(checkmark, (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 493, (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 489, 40, 40);
        }

        if(musicEnabled){
            GameEntry.batch.draw(checkmark, (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 493, (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 428, 40, 40);
        }

        if(sfxEnabled){
            GameEntry.batch.draw(checkmark, (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 493, (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 367, 40, 40);
        }

        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        //font.draw(GameEntry.batch, "" + xPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 20);
        //font.draw(GameEntry.batch, "" + yPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 40);

        //Enabling/disabling V-sync
        if(buttonSquare(((Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 495), ((Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 75), 45, 2)){
            if(vSyncEnabled){
                Gdx.graphics.setVSync(false);
                vSyncEnabled = false;
            }
            else{
                Gdx.graphics.setVSync(true);
                vSyncEnabled = true;
            }
        }

        //Enabling/disabling music
        if(buttonSquare(((Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 495), ((Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 136), 45, 2)){
            if(musicEnabled){
                musicEnabled = false;
            }
            else{
                musicEnabled = true;
            }
        }

        //Enabling/disabling sfx
        if(buttonSquare(((Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 495), ((Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 197), 45, 3)){
            if(sfxEnabled){
                sfxEnabled = false;
            }
            else{
                sfxEnabled = true;
            }
        }

        //Pressing back button
        if(buttonRectangle(((Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 305), ((Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 560), 225, 24, 1)){
            game.setScreen(new MainMenu(game));
        }

        //Exit game by pressing esc
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        timeElapsed += delta;
        GameEntry.batch.end();
    }

    /**
     * Called when GameOver becomes current screen.
     */
    public void show() {
    }

    /**
     * Method to resize screen.
     *
     * @param width  int
     * @param height int
     */
    public void resize(int width, int height) {
    }

    /**
     * Actions performed when game is paused.
     */
    public void pause() {
    }

    /**
     * Dispose the GameOver screen.
     */
    public void dispose() {
    }

    /**
     * Only called on android.
     */
    public void resume() {
    }

    public void hide() {
    }
}

