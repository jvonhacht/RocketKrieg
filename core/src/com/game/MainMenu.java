package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import static com.game.Settings.sfxEnabled;

/**
 * The main menu of the Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-05-01)
 */
public class MainMenu implements Screen {
    private final GameEntry game;
    private OrthographicCamera camera;
    private Sprite gameLogo;
    private Sprite playButton;
    private Sprite settingsButton;
    private Sprite buttonHover;
    private Sprite background;
    private float timeElapsed;

    /**
     * Constructor for MainMenu screen.
     * @param game GameEntry with SpriteBatch
     */
    public MainMenu(final GameEntry game) {
        this.game = game;
        gameLogo = AssetStorage.gameLogo;
        playButton = AssetStorage.playButton;
        settingsButton = AssetStorage.settingsButton;
        buttonHover = AssetStorage.buttonHover;
        background = AssetStorage.background;
        timeElapsed = 10;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    /**
     * Renders all the textures
     * and checks for player input.
     * @param delta time since last frame
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        GameEntry.batch.begin();

        //Get measurements of textures
        float logoWidth = gameLogo.getWidth();
        float logoHeight = gameLogo.getHeight();
        float playWidth = playButton.getWidth();
        float playHeight = playButton.getHeight();

        //Draw menu
        GameEntry.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameEntry.batch.draw(gameLogo, Gdx.graphics.getWidth()/2 - (logoWidth / 2), Gdx.graphics.getHeight()/2 - (logoHeight / 2) + 50);
        GameEntry.batch.draw(playButton, Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) - 100);
        GameEntry.batch.draw(settingsButton, Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) - 175);

        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        Sound playSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Play_Sound.mp3"));

        //Pressing play button
        if (xPos <= Gdx.graphics.getWidth()/2 + (playWidth / 2) && xPos >= Gdx.graphics.getWidth()/2 - (playWidth / 2)){
            if (yPos <= Gdx.graphics.getHeight()/2 + (playHeight / 2) + 100 && yPos >= Gdx.graphics.getHeight()/2 - (playHeight / 2) + 100){
                GameEntry.batch.draw(buttonHover, Gdx.graphics.getWidth()/2 - (playWidth / 2) + 2, Gdx.graphics.getHeight()/2 - (playHeight / 2) - 95);
                if (Gdx.input.isTouched()){
                    if(sfxEnabled){
                        playSound.play(1.0f);
                    }
                    game.setScreen(new RocketKrieg(game));
                }
            }
        }

        //Pressing settings button
        if (xPos <= Gdx.graphics.getWidth()/2 + (playWidth / 2) && xPos >= Gdx.graphics.getWidth()/2 - (playWidth / 2)){
            if (yPos <= Gdx.graphics.getHeight()/2 + (playHeight / 2) + 175 && yPos >= Gdx.graphics.getHeight()/2 - (playHeight / 2) + 175){
                GameEntry.batch.draw(buttonHover, Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) - 171);
                if (Gdx.input.isTouched()){
                    if(sfxEnabled){
                        playSound.play(1.0f);
                    }
                    game.setScreen(new Settings(game));
                }
            }
        }

        //Exit game by pressing esc
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        timeElapsed += delta;
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