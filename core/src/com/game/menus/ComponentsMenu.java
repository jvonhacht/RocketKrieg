package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.menus.MainMenu;
import com.game.menus.Menu;

/**
 * Components menu for Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-08-05)
 */
public class ComponentsMenu extends Menu implements Screen {
    private Sprite componentsBox;

    /**
     * Constructor for Components screen.
     *
     * @param game SpriteBatch
     */
    public ComponentsMenu(final GameEntry game) {
        super(game);
        background = AssetStorage.background1;
        componentsBox = AssetStorage.componentsBox;
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

        if(buttonRectangle((Gdx.graphics.getWidth()/2 + componentsBox.getWidth()/2) - 119, (Gdx.graphics.getHeight()/2 + componentsBox.getHeight()/2) - 32, 101, 19, 1)){
            game.setScreen(new MainMenu(game));
        }

        //Get mouse coordinates
        //int xPos = Gdx.input.getX();
        //int yPos = Gdx.input.getY();

        //font.draw(GameEntry.batch, "" + xPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 20);
        //font.draw(GameEntry.batch, "" + yPos, Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 40);
        font.draw(GameEntry.batch, "work in progress", Gdx.graphics.getWidth()/2 - 80, Gdx.graphics.getHeight()/2 + 60);

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

