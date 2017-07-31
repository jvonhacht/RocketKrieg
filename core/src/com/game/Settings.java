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

/**
 * Settings screen for Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-07-30)
 */
public class Settings implements Screen {
    private final GameEntry game;
    private Sprite background;
    private Sprite settingsBox;
    private Sprite checkmark;
    private Sound soundEffect1;
    private Sound soundEffect2;
    private OrthographicCamera camera;
    private BitmapFont font;
    private float timeElapsed;
    public static boolean musicEnabled = true;
    public static boolean sfxEnabled = true;
    private boolean vSyncEnabled = true;
    private boolean fullScreenEnabled = true;

    /**
     * Constructor for Settings screen.
     *
     * @param game SpriteBatch
     */
    public Settings(final GameEntry game) {
        this.game = game;
        background = AssetStorage.background;
        settingsBox = AssetStorage.settingsBox;
        checkmark = AssetStorage.checkmark;
        timeElapsed = 0;
        soundEffect1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Play_Sound.mp3"));
        soundEffect2 = Gdx.audio.newSound(Gdx.files.internal("sounds/SettingsButtonPress.mp3"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Initialize font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/TW_Cen_MT_Bold.ttf"));
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

        //Draw menu
        GameEntry.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        GameEntry.batch.draw(settingsBox, Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2, Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2);

        //Checkmarks
        if(fullScreenEnabled){
            font.draw(GameEntry.batch, "Full Screen", Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2 + 383, Gdx.graphics.getHeight()/2 + settingsBox.getHeight()/2 - 81);
        }
        else{
            font.draw(GameEntry.batch, "Windowed", Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2 + 384, Gdx.graphics.getHeight()/2 + settingsBox.getHeight()/2 - 81);
        }

        if(vSyncEnabled){
            GameEntry.batch.draw(checkmark, (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 493, (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 428, 40, 40);
        }

        if(musicEnabled){
            GameEntry.batch.draw(checkmark, (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 493, (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 367, 40, 40);
        }

        if(sfxEnabled){
            GameEntry.batch.draw(checkmark, (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 493, (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 306, 40, 40);
        }

        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        //font.draw(GameEntry.batch, "" + xPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 20);
        //font.draw(GameEntry.batch, "" + yPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 40);
        font.draw(GameEntry.batch, "more options to be added. button hitboxes gets shifted upwards in windowed mode", Gdx.graphics.getWidth()/2 - 500, Gdx.graphics.getHeight()/2 - 150);

        //Full screen/windowed mode
        if (xPos >= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 375  && xPos <= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 530){
            if (yPos >= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 75 && yPos <= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 110){
                if (Gdx.input.isTouched() && timeElapsed > 0.2f){
                    timeElapsed = 0;
                    if(fullScreenEnabled){
                        Gdx.graphics.setWindowedMode(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
                        fullScreenEnabled = false;
                    }
                    else{
                        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                        fullScreenEnabled = true;
                    }
                    if(sfxEnabled){
                        soundEffect2.play(1.0f);
                    }
                }
            }
        }

        //Enabling/disabling V-sync
        if (xPos >= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 495  && xPos <= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 530){
            if (yPos >= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 136 && yPos <= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 171){
                if (Gdx.input.isTouched() && timeElapsed > 0.2f){
                    timeElapsed = 0;
                    if(vSyncEnabled){
                        Gdx.graphics.setVSync(false);
                        vSyncEnabled = false;
                    }
                    else{
                        Gdx.graphics.setVSync(true);
                        vSyncEnabled = true;
                    }
                    if(sfxEnabled){
                        soundEffect2.play(1.0f);
                    }
                }
            }
        }

        //Enabling/disabling music
        if (xPos >= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 495  && xPos <= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 530){
            if (yPos >= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 197 && yPos <= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 232){
                if (Gdx.input.isTouched() && timeElapsed > 0.2f){
                    timeElapsed = 0;
                    if(musicEnabled){
                        musicEnabled = false;
                    }
                    else{
                        musicEnabled = true;
                    }
                    if(sfxEnabled){
                        soundEffect2.play(1.0f);
                    }
                }
            }
        }

        //Enabling/disabling sfx
        if (xPos >= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 495  && xPos <= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 530){
            if (yPos >= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 258 && yPos <= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 293){
                if (Gdx.input.isTouched() && timeElapsed > 0.2f){
                    timeElapsed = 0;
                    if(sfxEnabled){
                        sfxEnabled = false;
                    }
                    else{
                        sfxEnabled = true;
                    }
                    if(sfxEnabled){
                        soundEffect2.play(1.0f);
                    }
                }
            }
        }

        //Pressing back button
        if (xPos >= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 305  && xPos <= (Gdx.graphics.getWidth()/2 - settingsBox.getWidth()/2) + 530){
            if (yPos >= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 560 && yPos <= (Gdx.graphics.getHeight()/2 - settingsBox.getHeight()/2) + 584){
                if (Gdx.input.isTouched()){
                    if(sfxEnabled){
                        soundEffect1.play(1.0f);
                    }
                    game.setScreen(new MainMenu(game));
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

