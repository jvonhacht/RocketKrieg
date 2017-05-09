package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Asset storage
 * @author Johan von Hacht & David Johansson 
 * @version 1.2 (2017-05-09)
 */
public class AssetStorage {
    //game entities
    public static final Sprite spaceship = new Sprite(new Texture(Gdx.files.internal("images/spaceship/Spaceship.png")));
    public static final Sprite missile = new Sprite(new Texture("images/spaceship/missile.png"));
    public static final Sprite asteroid = new Sprite(new Texture("images/asteroid/Asteroid.png"));
    public static final Sprite alienShip = new Sprite(new Texture("images/alien_ship/AlienShip.png"));

    //tiles
    public static final Sprite tile1 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile.png")));
    public static final Sprite tile2 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile1.png")));

    //extra
    public static final Sprite debug = new Sprite(new Texture("images/worldGeneration/debugHitBox.png"));
    public static final Sprite debris = new Sprite(new Texture("images/collision/debris.png"));
    public static Animation<TextureRegion> flameAnimation;
    public static Animation<TextureRegion> explosionAnimation;

    /**
     * Constructor creating animations
     */
    public AssetStorage() {
        //ini flame and explosion animation
        TextureRegion[] explosionFrames = new TextureRegion[16];
        TextureRegion[][] tmpFrames1 = TextureRegion.split(new Texture(Gdx.files.internal("images/collision/explosion.png")),128,128);

        int index1 = 0;
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                explosionFrames[index1++] = tmpFrames1[j][i];
            }
        }
        explosionAnimation = new Animation(1f/20f,explosionFrames);

        TextureRegion[] flameFrames = new TextureRegion[9];
        TextureRegion[][] tmpFrames2 = TextureRegion.split(new Texture(Gdx.files.internal("images/spaceship/flame.png")), 90, 90);
        int index2 = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                flameFrames[index2++] = tmpFrames2[j][i];
            }
        }
        flameAnimation = new Animation(1f/20f,flameFrames);
    }
}
