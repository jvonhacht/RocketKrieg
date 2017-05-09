package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

/**
 * The main menu of the Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-05-01)
 */
public class MainMenu implements Screen {
    private final GameEntry game;
    private Texture gameLogo;
    private Texture playButton;
    private Texture background;
    private OrthographicCamera camera;

    public MainMenu(final GameEntry game) {
        this.game = game;
        gameLogo = new Texture("images/menu/Rocket_Krieg_Logo.png");
        playButton = new Texture("images/menu/Play_Button.png");
        background = new Texture("images/menu/Space_Background.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
    }

    public void show() {

    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        GameEntry.batch.begin();

        //Get measurements of textures
        int logoWidth = gameLogo.getWidth();
        int logoHeight = gameLogo.getHeight();
        int playWidth = playButton.getWidth();
        int playHeight = playButton.getHeight();

        //Draw menu
        GameEntry.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameEntry.batch.draw(gameLogo, Gdx.graphics.getWidth()/2 - (logoWidth / 2), Gdx.graphics.getHeight()/2 - (logoHeight / 2) + 50);
        GameEntry.batch.draw(playButton, Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) - 100);
        GameEntry.batch.end();

        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        Sound playSound = Gdx.audio.newSound(Gdx.files.internal("sounds/Play_Sound.mp3"));

        //Pressing play button
        if (xPos <= Gdx.graphics.getWidth()/2 + (playWidth / 2) && xPos >= Gdx.graphics.getWidth()/2 - (playWidth / 2)){
            if (yPos <= Gdx.graphics.getHeight()/2 + (playHeight / 2) + 100 && yPos >= Gdx.graphics.getHeight()/2 - (playHeight / 2) + 100){
                if (Gdx.input.isTouched()) {
                    playSound.play(1.0f);
                    game.setScreen(new RocketKrieg(game));
                    dispose();
                }
            }
        }
    }

    public void resize(int width, int height) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void hide() {

    }

    public void dispose() {

    }

}