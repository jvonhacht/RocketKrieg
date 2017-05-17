package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Game entry class initializing the
 * SpriteBatch and starting screen.
 * @author David Johansson
 * @version 1.0 (2017-04-29)
 */
public class GameEntry extends Game {
    public static SpriteBatch batch;
    public static BitmapFont font;

    /**
     * Sets the initial screen.
     * Initializes SpriteBatch.
     * and main font
     */
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        this.setScreen(new MainMenu(this));
    }

    /**
     * Render method for
     * rendering the graphics.
     */
    public void render() {
        super.render();
    }

    /**
     * Dispose the SpriteBatch
     * and font.
     */
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
