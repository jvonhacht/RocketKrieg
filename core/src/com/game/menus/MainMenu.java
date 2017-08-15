package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Base64Coder;
import com.game.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.err;

/**
 * The main menu of the Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-05-01)
 */
public class MainMenu extends Menu implements Screen {
    private static final int START_SCREEN = 1;
    private static final int MAIN_MENU = 2;

    private int state;
    private Sprite gameLogo;
    private Sprite playButton;
    private Sprite settingsButton;
    private Sprite componentsButton;
    private BitmapFont font;
    private GlyphLayout glyphLayout;
    private String startingText;
    ComponentsMenu menu;

    /**
     * Constructor for MainMenu screen.
     * @param game GameEntry with SpriteBatch
     */
    public MainMenu(final GameEntry game) {
        super(game);
        state = START_SCREEN;
        background = AssetStorage.background1;
        gameLogo = AssetStorage.gameLogo;
        playButton = AssetStorage.playButton;
        settingsButton = AssetStorage.settingsButton;
        componentsButton = AssetStorage.componentsButton;
        font = initializeFontTW(40);
        startingText = "PRESS ANY KEY TO CONTINUE";
        glyphLayout = new GlyphLayout();

        reloadComponents();
        //Initialise componentsmenu to add reloaded components to ship.
        menu = new ComponentsMenu(game);
        menu.updateComponents();
    }

    /**
     * Renders all the textures
     * and checks for player input.
     * @param delta time since last frame
     */
    public void render(float delta) {
        super.render(delta);

        switch(state){
            case 1:
                renderStart(delta);
                break;
            case 2:
                renderMenu(delta);
                break;
        }

        //Exit game by pressing esc
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    /**
     * Renders the start screen
     * @param delta time since last frame
     */
    private void renderStart(float delta){
        GameEntry.batch.draw(gameLogo, Gdx.graphics.getWidth()/3 - (gameLogo.getWidth() / 2), Gdx.graphics.getHeight()/2 - (gameLogo.getHeight() / 2) + 50);
        glyphLayout.setText(font, startingText);
        font.draw(GameEntry.batch, startingText ,Gdx.graphics.getWidth()/3 - glyphLayout.width/2, Gdx.graphics.getHeight()/2 - 40);

        if(Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched()){
            state = MAIN_MENU;
        }
        GameEntry.batch.end();
    }

    /**
     * Renders the main menu
     * @param delta time since last frame
     */
    private void renderMenu(float delta){
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
            game.setScreen(menu);
        }

        //Pressing settings button
        if(buttonRectangle(Gdx.graphics.getWidth()/2 - (playWidth / 2), Gdx.graphics.getHeight()/2 - (playHeight / 2) + 250, playWidth, playHeight, 1)){
            game.setScreen(new Settings(game));
        }

        GameEntry.batch.end();
    }

    /**
     * Method to reload components.
     */
    public void reloadComponents() {
        try{
            File toRead = new File("componentsData.rk");
            FileInputStream fis = new FileInputStream(toRead);
            Scanner sc = new Scanner(fis);
            String currentLine;
            if(sc.hasNextLine()){
                currentLine = sc.nextLine();
                byte[] byteLine = Base64Coder.decode(currentLine);
                currentLine = new String(byteLine,"UTF-8");
                String[] data = currentLine.split(Pattern.quote("&"));
                ComponentsMenu.shieldMk = Integer.parseInt(data[0]);
                ComponentsMenu.reloadMk = Integer.parseInt(data[1]);
                ComponentsMenu.speedMk = Integer.parseInt(data[2]);
                ComponentsMenu.boostMk = Integer.parseInt(data[3]);
                ComponentsMenu.turningMk = Integer.parseInt(data[4]);
            }
            fis.close();
        }catch(Exception e){
            err.println(e);
        }
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