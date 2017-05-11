package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Game over screen for Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-05-11)
 */
public class GameOver implements Screen {
    private final GameEntry game;
    private Sprite background;
    private Sprite replayButton;
    private Sprite exitButton;
    private Sound soundEffect;
    private Sprite gameOver;
    private int score;
    private BitmapFont font;
    private OrthographicCamera camera;

    public GameOver(final GameEntry game, int score) {
        this.game = game;
        this.score = score;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        background = AssetStorage.background2;
        replayButton = AssetStorage.replayButton;
        exitButton = AssetStorage.exitButton;
        gameOver = AssetStorage.gameOver;
        soundEffect = Gdx.audio.newSound(Gdx.files.internal("sounds/Play_Sound.mp3"));

        //Initialize font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TW_Cen_MT.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.position.x = Gdx.graphics.getWidth()/2;
        camera.position.y = Gdx.graphics.getHeight()/2;
        GameEntry.batch.setProjectionMatrix(camera.combined);
        camera.update();
        GameEntry.batch.begin();

        //Get button measurements
        float replayWidth = replayButton.getWidth();
        float replayHeight = replayButton.getHeight();
        float exitWidth = exitButton.getWidth();
        float exitHeight = exitButton.getHeight();

        //Draw game over screen
        GameEntry.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameEntry.batch.draw(gameOver, Gdx.graphics.getWidth()/2 - (gameOver.getWidth()/2), Gdx.graphics.getHeight()/2 - (gameOver.getHeight()/2) + 40);
        GameEntry.batch.draw(replayButton, Gdx.graphics.getWidth()/2 - (replayWidth / 2), Gdx.graphics.getHeight()/2 - (replayHeight / 2) - 120);
        GameEntry.batch.draw(exitButton, Gdx.graphics.getWidth()/2 - (exitWidth / 2), Gdx.graphics.getHeight()/2 - (exitHeight / 2) - 200);
        font.draw(GameEntry.batch, "" + score ,Gdx.graphics.getWidth()/2 + 40, Gdx.graphics.getHeight()/2 + 15);
        GameEntry.batch.end();

        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        //Pressing replay button
        if (xPos <= Gdx.graphics.getWidth()/2 + (replayWidth / 2) && xPos >= Gdx.graphics.getWidth()/2 - (replayWidth / 2)){
            if (yPos <= Gdx.graphics.getHeight()/2 + (replayHeight / 2) + 120 && yPos >= Gdx.graphics.getHeight()/2 - (replayHeight / 2) + 120){
                if (Gdx.input.isTouched()) {
                    soundEffect.play(1.0f);
                    game.setScreen(new RocketKrieg(game));
                    dispose();
                }
            }
        }

        //Pressing exit button
        if (xPos <= Gdx.graphics.getWidth()/2 + (exitWidth / 2) && xPos >= Gdx.graphics.getWidth()/2 - (exitWidth / 2)){
            if (yPos <= Gdx.graphics.getHeight()/2 + (exitHeight / 2) + 200 && yPos >= Gdx.graphics.getHeight()/2 - (exitHeight / 2) + 200){
                if (Gdx.input.isTouched()) {
                    soundEffect.play(1.0f);
                    Gdx.app.exit();
                }
            }
        }

        //Exit game by pressing esc
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    public void show() {

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