package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.audio.Sound;
import static com.game.Settings.sfxEnabled;

/**
 * Components menu for Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-08-05)
 */
public class ComponentsMenu implements Screen {
    private final GameEntry game;
    private Sprite background;
    private Sprite componentsBox;
    private Sound soundEffect1;
    private Sound soundEffect2;
    private OrthographicCamera camera;
    private BitmapFont font;
    private float timeElapsed;

    /**
     * Constructor for Components screen.
     *
     * @param game SpriteBatch
     */
    public ComponentsMenu(final GameEntry game) {
        this.game = game;
        background = AssetStorage.background1;
        componentsBox = AssetStorage.componentsBox;
        timeElapsed = 0;
        soundEffect1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Play_Sound.mp3"));
        soundEffect2 = Gdx.audio.newSound(Gdx.files.internal("sounds/SettingsButtonPress.mp3"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Initialize font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Tw_Cen_MT_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    /**
     * Renders all the textures
     * and checks for player input.
     *
     * @param delta time since last frame
     */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        GameEntry.batch.begin();

        GameEntry.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameEntry.batch.draw(componentsBox, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2);


        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        //font.draw(GameEntry.batch, "" + xPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 20);
        //font.draw(GameEntry.batch, "" + yPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 40);


        //Exit game by pressing esc
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        timeElapsed += delta;
        GameEntry.batch.end();
    }

    /**
     * Returns true if the player presses intended region
     * inside the square button. Also plays sound effect
     * if its enabled
     * @param xMin minimum x value
     * @param yMin minimum y value
     * @param squareWidth square width
     * @param sfxType sound effect type
     */
    public boolean buttonSquare(float xMin, float yMin, float squareWidth, int sfxType){
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();
        if (xPos >= xMin && xPos <= xMin + squareWidth){
            if(yPos >= yMin && yPos <= yMin + squareWidth){
                if (Gdx.input.isTouched() && timeElapsed > 0.2f){
                    timeElapsed = 0;
                    switch(sfxType){
                        case 1:
                            if(sfxEnabled){
                                soundEffect1.play(1.0f);
                            }
                            break;
                        case 2:
                            if(sfxEnabled){
                                soundEffect2.play(1.0f);
                            }
                            break;
                        case 3:
                            soundEffect2.play(1.0f);
                            break;
                    }
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Returns true if the player presses intended region
     * inside the rectangle button. Also plays sound effect
     * if its enabled
     *
     * @param xMin minimum x value
     * @param yMin minimum y value
     * @param recWidth rectangle width
     * @param recHeight rectangle height
     * @param sfxType sound effect type
     */
    public boolean buttonRectangle(float xMin, float yMin, float recWidth, float recHeight, int sfxType){
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();
        if (xPos >= xMin && xPos <= xMin + recWidth){
            if(yPos >= yMin && yPos <= yMin + recHeight){
                if (Gdx.input.isTouched() && timeElapsed > 0.2f){
                    timeElapsed = 0;
                    switch(sfxType){
                        case 1:
                            if(sfxEnabled){
                                soundEffect1.play(1.0f);
                            }
                            break;
                        case 2:
                            if(sfxEnabled){
                                soundEffect2.play(1.0f);
                            }
                            break;
                        case 3:
                            soundEffect2.play(1.0f);
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
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
