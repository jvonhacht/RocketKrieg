package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.ship.shipComponent.shieldComponent.*;
import com.game.objects.ship.shipComponent.reloadComponent.*;
import com.game.objects.ship.shipComponent.speedComponent.*;
import com.game.objects.ship.shipComponent.boostComponent.*;

/**
 * Components menu for Rocket Krieg program.
 * @author David Johansson
 * @version 1.1 (2017-08-11)
 */
public class ComponentsMenu extends Menu implements Screen {
    public static ShieldComponent activeShieldComponent = new ShieldComponentMk1();
    //public static ReloadComponent activeReloadComponent;
    //public static SpeedComponent activeSpeedComponent;
    public static BoostComponent activeBoostComponent = new BoostComponentMk1();

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
        shieldComp = new ComponentWidget(1, 1, componentsBox);
        reloadComp = new ComponentWidget(2, 1, componentsBox);
        speedComp = new ComponentWidget(3, 1, componentsBox);
        boostComp = new ComponentWidget(4, 1, componentsBox);
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

        //Get mouse coordinates
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();

        font.draw(GameEntry.batch, "" + xPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 20);
        font.draw(GameEntry.batch, "" + yPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 40);

        //Press back button
        if(buttonRectangle((Gdx.graphics.getWidth()/2 + componentsBox.getWidth()/2) - 119, (Gdx.graphics.getHeight()/2 + componentsBox.getHeight()/2) - 32, 101, 19, 1)){
            updateComponents();
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
    private void updateComponents(){
        updateShieldComp();
        //updateReloadComp();
        //updateSpeedComp();
        updateBoostComp();
    }

    /**
     * Update shield component
     */
    private void updateShieldComp(){
        switch(shieldComp.getCurrentComp()){
            case 1:
                activeShieldComponent = new ShieldComponentMk1();
                break;
            case 2:
                activeShieldComponent = new ShieldComponentMk2();
                break;
            case 3:
                activeShieldComponent = new ShieldComponentMk3();
                break;
            case 4:
                activeShieldComponent = new ShieldComponentMk4();
                break;
            case 5:
                activeShieldComponent = new ShieldComponentMk5();
                break;
        }
    }

    /**
     * Update boost component
     */
    private void updateBoostComp(){
        switch(shieldComp.getCurrentComp()){
            case 1:
                activeBoostComponent = new BoostComponentMk1();
                break;
            case 2:
                activeBoostComponent = new BoostComponentMk2();
                break;
            case 3:
                activeBoostComponent = new BoostComponentMk3();
                break;
            case 4:
                activeBoostComponent = new BoostComponentMk4();
                break;
            case 5:
                activeBoostComponent = new BoostComponentMk5();
                break;
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

