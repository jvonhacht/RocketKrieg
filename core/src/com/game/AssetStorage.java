package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Johan on 03/05/2017.
 */
public class AssetStorage {
    public static final Sprite tile1 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile.png")));
    public static final Sprite tile2 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile1.png")));
    public static final Sprite asteroid = new Sprite(new Texture("images/asteroid/Asteroid.png"));
    public static final Sprite debug = new Sprite(new Texture("images/worldGeneration/debugHitBox.png"));
    public static final Sprite debris = new Sprite(new Texture("images/collision/debris.png"));
    public static Animation<TextureRegion> explosionAnimation;

    public AssetStorage() {
        //ini explosion animation
        TextureRegion[] animationFrames = new TextureRegion[16];
        TextureRegion[][] tmpFrames = TextureRegion.split(new Texture(Gdx.files.internal("images/collision/explosion.png")),512,512);
        int index = 0;
        for (int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++) {
                animationFrames[index++] = tmpFrames[j][i];
            }
        }

        explosionAnimation = new Animation(1f/4f,animationFrames);
    }
}
