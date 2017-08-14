package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.StringBuilder;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.RocketKrieg;
import com.game.objects.ship.shipComponent.ShipComponent;
import com.game.objects.ship.shipComponent.shieldComponent.*;
import com.game.objects.ship.shipComponent.reloadComponent.*;
import com.game.objects.ship.shipComponent.speedComponent.*;
import com.game.objects.ship.shipComponent.boostComponent.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.err;

/**
 * Components menu for Rocket Krieg program.
 * @author David Johansson
 * @version 1.1 (2017-08-11)
 */
public class ComponentsMenu extends Menu implements Screen {
    public static ShieldComponent activeShieldComponent;
    public static ShipComponent activeReloadComponent;
    public static ShipComponent activeSpeedComponent;
    public static BoostComponent activeBoostComponent;

    public static int shieldMk;
    public static int reloadMk;
    public static int speedMk;
    public static int boostMk;

    private Sprite componentsBox;
    private ComponentWidget shieldComp;
    private ComponentWidget reloadComp;
    private ComponentWidget speedComp;
    private ComponentWidget boostComp;

    /**
     * Constructor for Components screen.
     *
     * @param game SpriteBatch
     */
    public ComponentsMenu(final GameEntry game) {
        super(game);
        background = AssetStorage.background1;
        componentsBox = AssetStorage.componentsBox;
        shieldComp = new ComponentWidget(1, shieldMk, componentsBox);
        reloadComp = new ComponentWidget(2, reloadMk, componentsBox);
        speedComp = new ComponentWidget(3, speedMk, componentsBox);
        boostComp = new ComponentWidget(4, boostMk, componentsBox);
    }

    /**
     * Renders all the textures
     * and checks for player input.
     *
     * @param delta time since last frame
     */
    public void render(float delta) {
        super.render(delta);

        //Draw menu
        GameEntry.batch.draw(componentsBox, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2);

        //Render components
        shieldComp.renderComponent(delta);
        reloadComp.renderComponent(delta);
        speedComp.renderComponent(delta);
        boostComp.renderComponent(delta);

        //Press back button
        if(buttonRectangle((Gdx.graphics.getWidth()/2 + componentsBox.getWidth()/2) - 119, (Gdx.graphics.getHeight()/2 + componentsBox.getHeight()/2) - 32, 101, 19, 1)){
            updateComponents();
            saveComponents();
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
     * Method for updating the
     * components.
     */
    public void updateComponents(){
        updateShieldComp();
        updateReloadComp();
        updateSpeedComp();
        updateBoostComp();
    }

    /**
     * Update shield component
     */
    private void updateShieldComp(){
        switch(shieldComp.getCurrentComp()){
            case 0:
                activeShieldComponent = new ShieldComponentMk1();
                break;
            case 1:
                activeShieldComponent = new ShieldComponentMk2();
                break;
            case 2:
                activeShieldComponent = new ShieldComponentMk3();
                break;
            case 3:
                activeShieldComponent = new ShieldComponentMk4();
                break;
            case 4:
                activeShieldComponent = new ShieldComponentMk5();
                break;
        }
    }

    /**
     * Update reload component
     */
    private void updateReloadComp(){
        switch(reloadComp.getCurrentComp()){
            case 0:
                activeReloadComponent = new ReloadComponentMk1();
                break;
            case 1:
                activeReloadComponent = new ReloadComponentMk2();
                break;
            case 2:
                activeReloadComponent = new ReloadComponentMk3();
                break;
            case 3:
                activeReloadComponent = new ReloadComponentMk4();
                break;
            case 4:
                activeReloadComponent = new ReloadComponentMk5();
                break;
        }
    }

    /**
     * Update speed component
     */
    private void updateSpeedComp(){
        switch(speedComp.getCurrentComp()){
            case 0:
                activeSpeedComponent = new SpeedComponentMk1();
                break;
            case 1:
                activeSpeedComponent = new SpeedComponentMk2();
                break;
            case 2:
                activeSpeedComponent = new SpeedComponentMk3();
                break;
            case 3:
                activeSpeedComponent = new SpeedComponentMk4();
                break;
            case 4:
                activeSpeedComponent = new SpeedComponentMk5();
                break;
        }
    }

    /**
     * Update boost component
     */
    private void updateBoostComp(){
        switch(boostComp.getCurrentComp()){
            case 0:
                activeBoostComponent = new BoostComponentMk1();
                break;
            case 1:
                activeBoostComponent = new BoostComponentMk2();
                break;
            case 2:
                activeBoostComponent = new BoostComponentMk3();
                break;
            case 3:
                activeBoostComponent = new BoostComponentMk4();
                break;
            case 4:
                activeBoostComponent = new BoostComponentMk5();
                break;
        }
    }

    /**
     * Method for saving components.
     */
    public void saveComponents() {
        //save components build
        try {
            File gameChunks = new File("componentsData.rk");
            FileOutputStream fos = new FileOutputStream(gameChunks);
            PrintWriter pw = new PrintWriter(fos);
            StringBuilder sb = new StringBuilder();
            sb.append(shieldComp.getCurrentComp()); sb.append("&");
            sb.append(reloadComp.getCurrentComp()); sb.append("&");
            sb.append(speedComp.getCurrentComp());  sb.append("&");
            sb.append(boostComp.getCurrentComp());
            String toPrint = sb.toString();
            toPrint = Base64Coder.encodeString(toPrint);
            pw.println(toPrint);

            pw.flush();
            pw.close();
            fos.close();
        } catch(Exception e) {
            err.println(e);
        }
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

