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
    public static final Sprite alienShipSpecial = new Sprite(new Texture("images/alien_ship/AlienShipSpecial.png"));
    public static final Sprite planet = new Sprite(new Texture("images/planet/planet.png"));
    public static final Sprite planet1 = new Sprite(new Texture("images/planet/plutoPlanet.png"));
    public static final Sprite planet2 = new Sprite(new Texture("images/planet/greyPlanet.png"));
    public static final Sprite planet3 = new Sprite(new Texture("images/planet/jupiterPlanet.png"));
    public static final Sprite laser = new Sprite(new Texture("images/alien_ship/Laser.png"));

    //tiles
    public static final Sprite tile1 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile.png")));
    public static final Sprite tile2 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile1.png")));

    //animations
    public static Animation<TextureRegion> flameAnimation;
    public static Animation<TextureRegion> explosionAnimation;
    public static Animation<TextureRegion> redLightAnimation;
    public static Animation<TextureRegion> atmosphereAnimation;
    public static Animation<TextureRegion> sparkleAnimation;

    //extra
    public static final Sprite debug = new Sprite(new Texture("images/worldGeneration/debugHitBox.png"));
    public static final Sprite debris = new Sprite(new Texture("images/collision/debris.png"));
    public static final Sprite instructions = new Sprite(new Texture("images/menu/Instructions.png"));
    public static final Sprite background2 = new Sprite(new Texture("images/menu/Space_Background2.png"));
    public static final Sprite replayButton = new Sprite(new Texture("images/menu/Replay_Button.png"));
    public static final Sprite exitButton = new Sprite(new Texture("images/menu/Exit_Button.png"));
    public static final Sprite gameOver = new Sprite(new Texture("images/menu/Game_Over.png"));
    public static final Sprite gameOver2 = new Sprite(new Texture("images/menu/Game_Over2.png"));
    public static final Sprite singleSparkle = new Sprite(new Texture("images/worldGeneration/singleSparkle.png"));

    /**
     * Constructor creating animations
     */
    public AssetStorage() {
        //create animations
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

        TextureRegion[] sparkleFrames = new TextureRegion[30];
        TextureRegion[][] tmpFrames3 = TextureRegion.split(new Texture(Gdx.files.internal("images/worldGeneration/sparkle.png")), 47, 47);
        int index3 = 0;
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++) {
                sparkleFrames[index3++] = tmpFrames3[j][i];
            }
        }
        sparkleAnimation = new Animation(1f/10f,sparkleFrames);

        TextureRegion[] redFrames = new TextureRegion[9];
        TextureRegion[][] tmpFrames4 = TextureRegion.split(new Texture(Gdx.files.internal("images/alien_ship/BlinkingRedLight.png")), 90, 90);
        int index4 = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                redFrames[index4++] = tmpFrames4[j][i];
            }
        }
        redLightAnimation = new Animation(1f/10f,redFrames);

        TextureRegion[] atmoFrames = new TextureRegion[9];
        TextureRegion[][] tmpFrames5 = TextureRegion.split(new Texture(Gdx.files.internal("images/planet/Atmosphere.png")), 81, 81);
        int index5 = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                atmoFrames[index5++] = tmpFrames5[i][j];
            }
        }
        atmosphereAnimation = new Animation(1f/3f, atmoFrames);
    }
}
