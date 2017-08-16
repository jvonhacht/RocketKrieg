package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Base64Coder;
import com.game.*;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.game.menus.Settings.sfxEnabled;
import static java.lang.System.err;

/**
 * The main menu of the Rocket Krieg program.
 * @author David Johansson
 * @version 1.3 (2017-08-16)
 */
public class MainMenu extends Menu implements Screen {
    private static final int START_SCREEN = 1;
    private static final int MAIN_MENU = 2;

    private int state;
    private Sprite gameLogo;
    private Sprite buttonHover;
    private Sprite personalStats;
    private BitmapFont fontStart;
    private BitmapFont fontSmall;
    private BitmapFont fontStatsM;
    private BitmapFont fontStats;
    private BitmapFont fontMolot;
    private GlyphLayout glyphLayout;
    private String startingText;
    ComponentsMenu menu;

    //Personal stats
    private float totalTime;
    private int totalCurrency;
    private String timeString;

    /**
     * Constructor for MainMenu screen.
     * @param game GameEntry with SpriteBatch
     */
    public MainMenu(final GameEntry game, int state) {
        super(game);
        this.state = state;
        background = AssetStorage.background1;
        gameLogo = AssetStorage.gameLogo;
        buttonHover = AssetStorage.buttonHover;
        personalStats = AssetStorage.personalStats;
        fontStart = initializeFontTW(30, Color.WHITE);
        fontSmall = initializeFontTW(20, Color.ORANGE);
        fontStats = initializeFontTW(20, Color.WHITE);
        fontStatsM = initializeFontMolot(25, Color.WHITE);
        fontMolot = initializeFontMolot(50, Color.WHITE);
        startingText = "Press any key to continue";
        glyphLayout = new GlyphLayout();

        reloadComponents();
        //Initialize components menu to add reloaded components for ship.
        menu = new ComponentsMenu(game);
        menu.updateComponents();

        reloadPlayerStats();
        timeString = convertSeconds();
    }

    /**
     * Renders all the textures
     * and checks for player input.
     * @param delta time since last frame
     */
    public void render(float delta) {
        super.render(delta);

        switch(state){
            case START_SCREEN:
                renderStart(delta);
                break;
            case MAIN_MENU:
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
        GameEntry.batch.draw(gameLogo, Gdx.graphics.getWidth()/2 - (gameLogo.getWidth() / 2), Gdx.graphics.getHeight()/2 - (gameLogo.getHeight() / 2) + 50);
        glyphLayout.setText(fontStart, startingText);
        fontStart.draw(GameEntry.batch, startingText ,Gdx.graphics.getWidth()/2 - glyphLayout.width/2, Gdx.graphics.getHeight()/2 - 45);

        if((Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched()) && !Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            timeElapsed = 0;
            state = MAIN_MENU;
            if(sfxEnabled){
                soundEffect1.play(1.0f);
            }
        }
        GameEntry.batch.end();
    }

    /**
     * Renders the main menu
     * @param delta time since last frame
     */
    private void renderMenu(float delta){
        //Render hover sprites
        renderHoverSprites(1);
        renderHoverSprites(2);
        renderHoverSprites(3);
        renderHoverSprites(4);

        //Render options
        fontSmall.draw(GameEntry.batch, "Main Menu", 60, Gdx.graphics.getHeight()/2 + 50);
        fontMolot.draw(GameEntry.batch, "PLAY", 60, Gdx.graphics.getHeight()/2);
        fontMolot.draw(GameEntry.batch, "COMPONENTS", 60, Gdx.graphics.getHeight()/2 - 60);
        fontMolot.draw(GameEntry.batch, "SETTINGS", 60, Gdx.graphics.getHeight()/2 - 120);
        fontMolot.draw(GameEntry.batch, "EXIT", 60, Gdx.graphics.getHeight()/2 - 180);

        renderStats();

        //missing hover animations
        //Pressing play button
        if(buttonRectangle(40, Gdx.graphics.getHeight()/2, 400, 60, 1)){
            game.setScreen(new RocketKrieg(game));
        }

        //Pressing components button
        if(buttonRectangle(40, Gdx.graphics.getHeight()/2 + 47.5f, 400, 60, 1)){
            game.setScreen(menu);
        }

        //Pressing settings button
        if(buttonRectangle(40, Gdx.graphics.getHeight()/2 + 107.5f, 400, 60, 1)){
            game.setScreen(new Settings(game));
        }

        //Pressing exit button
        if(buttonRectangle(40, Gdx.graphics.getHeight()/2 + 167.5f, 400, 60, 1)){
            Gdx.app.exit();
        }

        GameEntry.batch.end();
    }

    /**
     * Method for rendering the hover sprites
     * depending on mouse coordinates
     * @param type which button
     */
    private void renderHoverSprites(int type){
        //Get mouse coordinates
        float xPos = Gdx.input.getX();
        float yPos = Gdx.input.getY();
        if(xPos >= 40 && xPos <= 440){
            if(yPos <= Gdx.graphics.getHeight()/2 + 47.5f + (type - 1)*60 && yPos >= Gdx.graphics.getHeight()/2 - 12.5f + (type - 1)*60){
                GameEntry.batch.draw(buttonHover, 40, Gdx.graphics.getHeight()/2 - 47.5f - (type - 1)*60, 400, 60);
            }
        }
    }

    /**
     * Method for rendering player stats
     */
    private void renderStats(){
        GameEntry.batch.draw(personalStats, Gdx.graphics.getWidth() - 380f, Gdx.graphics.getHeight() - 169.7f, 350f, 139.7f);
        fontStatsM.draw(GameEntry.batch, "PLAYER STATS", Gdx.graphics.getWidth() - 370f, Gdx.graphics.getHeight() - 41f);
        fontStats.draw(GameEntry.batch, "Total time played:", Gdx.graphics.getWidth() - 370f, Gdx.graphics.getHeight() - 80f);
        glyphLayout.setText(fontStats, timeString);
        fontStats.draw(GameEntry.batch, timeString, Gdx.graphics.getWidth() - glyphLayout.width - 40, Gdx.graphics.getHeight() - 80f);
        fontStats.draw(GameEntry.batch, "Points collected:", Gdx.graphics.getWidth() - 370f, Gdx.graphics.getHeight() - 113f);
        glyphLayout.setText(fontStats, "" + totalCurrency);
        fontStats.draw(GameEntry.batch, "" + totalCurrency, Gdx.graphics.getWidth() - glyphLayout.width - 40, Gdx.graphics.getHeight() - 113f);
        fontStats.draw(GameEntry.batch, "Aliens eliminated:", Gdx.graphics.getWidth() - 370f, Gdx.graphics.getHeight() - 146f);
        glyphLayout.setText(fontStats, "N/A");
        fontStats.draw(GameEntry.batch, "N/A", Gdx.graphics.getWidth() - glyphLayout.width - 40, Gdx.graphics.getHeight() - 146f);
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
     * Method to reload player stats from file.
     */
    public void reloadPlayerStats() {
        try{
            File toRead = new File("playerData.rk");
            if(toRead.exists()) {
                FileInputStream fis = new FileInputStream(toRead);
                Scanner sc = new Scanner(fis);
                String currentLine;
                if(sc.hasNextLine()){
                    currentLine = sc.nextLine();
                    byte[] byteLine = Base64Coder.decode(currentLine);
                    currentLine = new String(byteLine,"UTF-8");
                    String[] data = currentLine.split(Pattern.quote("&"));
                    totalCurrency = Integer.parseInt(data[1]);
                    totalTime = Float.parseFloat(data[2]);
                }
                fis.close();
            }
        }catch(Exception e){
            err.println(e);
        }
    }

    /**
     * Convert total time played
     * in seconds to hours/minutes/seconds
     */
    private String convertSeconds(){
        int hours = (int) totalTime / 3600;
        int minutes = (int) (totalTime % 3600) / 60;
        int seconds = (int) totalTime % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
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