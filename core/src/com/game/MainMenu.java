package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The main menu of the Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-05-01)
 */
public class MainMenu extends Menu implements Screen {
    private Sprite gameLogo;
    private Sprite playButton;
    private Sprite settingsButton;
    private Sprite componentsButton;
    private Sprite buttonHover;

    /**
     * Constructor for MainMenu screen.
     * @param game GameEntry with SpriteBatch
     */
    public MainMenu(final GameEntry game) {
        super(game);
        background = AssetStorage.background1;
        gameLogo = AssetStorage.gameLogo;
        playButton = AssetStorage.playButton;
        settingsButton = AssetStorage.settingsButton;
        componentsButton = AssetStorage.componentsButton;
        buttonHover = AssetStorage.buttonHover;
    }

    /**
     * Renders all the textures
     * and checks for player input.
     * @param delta time since last frame
     */
    public void render(float delta) {
        super.render(delta);

        //Get measurements of textures
        float logoWidth = gameLogo.getWidth();
        float logoHeight = gameLogo.getHeight();
        float playWidth = playButton.getWidth();
        float playHeight = playButton.getHeight();

        //Draw menu
        GameEntry.batch.draw(gameLogo, Gdx.graphics.getWidth()/2 - (logoWidth / 2), Gdx.graphics.getHeight()/2 - (logoHeight / 2) + 50);
        GameEntry.batch.draw(playButton, Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) - 100);
        GameEntry.batch.draw(componentsButton, Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) - 175);
        GameEntry.batch.draw(settingsButton, Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) - 250);

        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        Sound playSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Play_Sound.mp3"));

        //missing hover animations
        //Pressing play button
        if(buttonRectangle(Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) + 100, playWidth, playHeight, 1)){
            game.setScreen(new RocketKrieg(game));
        }

        //Pressing components button
        if(buttonRectangle(Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) + 175, playWidth, playHeight, 1)){
            game.setScreen(new ComponentsMenu(game));
        }

        //Pressing settings button
        if(buttonRectangle(Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) + 250, playWidth, playHeight, 1)){
            game.setScreen(new Settings(game));
        }

        //Exit game by pressing esc
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        GameEntry.batch.end();
    }

    /**
     * Called when MainMenu becomes current screen.
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
     * Dispose the MainMenu screen.
     */
    public void dispose() { GameEntry.batch.dispose();}

    /**
     * Only called on android.
     */
    public void resume() {}
    public void hide() {}

}