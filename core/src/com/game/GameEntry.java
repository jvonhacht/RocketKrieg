package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

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
        //Initialize font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Tw_Cen_MT_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 15;
        font = generator.generateFont(parameter);
        generator.dispose();
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
