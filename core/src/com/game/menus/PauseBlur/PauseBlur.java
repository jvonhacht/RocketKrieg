package com.game.menus.PauseBlur;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

/**
 * Settings screen for Rocket Krieg program.
 * @author David Johansson
 * @version 1.0 (2017-07-30)
 */
public class PauseBlur{
    public static final int FBO_SIZE = 1024;

    Texture texture;
    SpriteBatch batch;
    ShaderProgram shader;
    FrameBuffer blurTargetA, blurTargetB;

    public void createBlur() {

        texture = new Texture("images/screenshots/screenshot.png");
        batch = new SpriteBatch();

        int VIRTUAL_WIDTH = Gdx.graphics.getWidth();
        int VIRTUAL_HEIGHT = Gdx.graphics.getHeight();

        //create the FBOs
        blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);
        blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, VIRTUAL_WIDTH, VIRTUAL_HEIGHT, false);

        //shader
        shader = new ShaderProgram(Gdx.files.internal("blur/blur.vert"), Gdx.files.internal("blur/blur.frag"));

        //Set up default uniforms
        shader.begin();
        shader.setUniformf("resolution", VIRTUAL_WIDTH);
        shader.end();
    }


    public void render() {
        batch.begin();
        blurTargetA.begin();
        Gdx.gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setShader(null);
        batch.draw(texture, 0.0f, 0.0f);
        batch.flush();
        blurTargetA.end();
        applyBlur(3.0f);

    }

    private void applyBlur(float blur) {
        //Horizontal blur
        blurTargetB.begin();
        batch.setShader(shader);
        shader.setUniformf("dir", 1.0f, 0.0f);
        shader.setUniformf("radius", blur);
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.draw(blurTargetA.getColorBufferTexture(),  0.0f, 0.0f);
        batch.flush();
        blurTargetB.end();

        //Vertical blur
        shader.setUniformf("dir", 0.0f, 1.0f);
        shader.setUniformf("radius", blur);
        batch.draw(blurTargetB.getColorBufferTexture(), 0.0f, 0.0f);
        batch.flush();
    }
}
