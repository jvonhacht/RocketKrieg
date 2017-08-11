package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Asset storage class for storing
 * all textures and animations
 * @author Johan von Hacht & David Johansson 
 * @version 1.3 (2017-05-17)
 */
public class AssetStorage {
    //menus
    public static final Sprite gameLogo = new Sprite(new Texture("images/menu/Rocket_Krieg_Logo.png"));
    public static final Sprite playButton = new Sprite(new Texture("images/menu/Play_Button.png"));
    public static final Sprite buttonHover = new Sprite(new Texture("images/menu/Button_Hover.png"));
    public static final Sprite settingsButton = new Sprite(new Texture("images/menu/settings/Settings_Button.png"));
    public static final Sprite settingsBox = new Sprite(new Texture("images/menu/settings/Settings_Box.png"));
    public static final Sprite componentsButton = new Sprite(new Texture("images/menu/components/Components_Button.png"));
    public static final Sprite componentsBox = new Sprite(new Texture("images/menu/components/Components_Box.png"));
    public static final Sprite background1 = new Sprite(new Texture("images/menu/Space_Background.jpg"));
    public static final Sprite background2 = new Sprite(new Texture("images/menu/Space_Background2.png"));
    public static final Sprite instructions = new Sprite(new Texture("images/menu/Instructions.png"));
    public static final Sprite replayButton = new Sprite(new Texture("images/menu/Replay_Button.png"));
    public static final Sprite exitButton = new Sprite(new Texture("images/menu/Exit_Button.png"));
    public static final Sprite gameOver = new Sprite(new Texture("images/menu/game_over/Game_Over.png"));
    public static final Sprite gamePaused = new Sprite(new Texture("images/menu/pause/Game_Paused.png"));
    public static final Sprite pauseFilter = new Sprite(new Texture("images/menu/pause/Red_Gradient_Filter.png"));

    //HUD
    public static final Sprite hudBar = new Sprite(new Texture("images/HUD/HUD_Bar.png"));

    //Component_Widget
    public static final Sprite compBackground = new Sprite(new Texture("images/menu/components/Component_Background.png"));
    public static final Sprite compShield = new Sprite(new Texture("images/menu/components/Component_Shield.png"));
    public static final Sprite compReload = new Sprite(new Texture("images/menu/components/Component_Reload.png"));
    public static final Sprite compSpeed = new Sprite(new Texture("images/menu/components/Component_Speed.png"));
    public static final Sprite compBoost = new Sprite(new Texture("images/menu/components/Component_Boost.png"));
    public static final Sprite plusDark = new Sprite(new Texture("images/menu/components/Plus_Dark.png"));
    public static final Sprite minusDark = new Sprite(new Texture("images/menu/components/Minus_Dark.png"));

    public static final Sprite compMk1 = new Sprite(new Texture("images/menu/components/Comp_Mk1.png"));
    public static final Sprite compMk2 = new Sprite(new Texture("images/menu/components/Comp_Mk2.png"));
    public static final Sprite compMk3 = new Sprite(new Texture("images/menu/components/Comp_Mk3.png"));
    public static final Sprite compMk4 = new Sprite(new Texture("images/menu/components/Comp_Mk4.png"));
    public static final Sprite compMk5 = new Sprite(new Texture("images/menu/components/Comp_Mk5.png"));

    //game entities
    public static final Sprite spaceship = new Sprite(new Texture(Gdx.files.internal("images/spaceship/Spaceship.png")));
    public static final Sprite missile = new Sprite(new Texture("images/spaceship/missile.png"));
    public static final Sprite asteroid = new Sprite(new Texture("images/asteroid/Asteroid.png"));
    public static final Sprite alienShip = new Sprite(new Texture("images/alien_ship/PurpleAlien.png"));
    public static final Sprite alienShipSpecial = new Sprite(new Texture("images/alien_ship/RedAlien.png"));
    public static final Sprite planet = new Sprite(new Texture("images/planet/Planet.png"));
    public static final Sprite planet1 = new Sprite(new Texture("images/planet/plutoPlanet.png"));
    public static final Sprite planet2 = new Sprite(new Texture("images/planet/greyPlanet.png"));
    public static final Sprite planet3 = new Sprite(new Texture("images/planet/jupiterPlanet.png"));
    public static final Sprite laser = new Sprite(new Texture("images/alien_ship/Laser.png"));
    public static final Texture hBar = new Texture("images/spaceship/healthbar.png");

    //tiles
    public static final Sprite tile1 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile.png")));
    public static final Sprite tile2 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/greyBorderTile.png")));

    //animations
    public static Animation<TextureRegion> flameAnimation;
    public static Animation<TextureRegion> explosionAnimation;
    public static Animation<TextureRegion> redLightAnimation;
    public static Animation<TextureRegion> atmosphereAnimation;
    public static Animation<TextureRegion> sparkleAnimation;
    public static Animation<TextureRegion> shieldAnimation;

    //extra
    public static final Sprite debug = new Sprite(new Texture("images/worldGeneration/debugHitBox.png"));
    public static final Sprite debris = new Sprite(new Texture("images/collision/debris.png"));
    public static final Sprite singleSparkle = new Sprite(new Texture("images/worldGeneration/singleSparkle.png"));
    public static final Sprite checkmark = new Sprite(new Texture("images/menu/Checkmark.png"));

    /**
     * Constructor creating animations
     */
    public AssetStorage() {
        //explosion animation
        TextureRegion[] explosionFrames = new TextureRegion[16];
        TextureRegion[][] tmpFrames1 = TextureRegion.split(new Texture(Gdx.files.internal("images/collision/explosion.png")),128,128);

        int index1 = 0;
        for (int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++) {
                explosionFrames[index1++] = tmpFrames1[j][i];
            }
        }
        explosionAnimation = new Animation<TextureRegion>(1f/20f,explosionFrames);

        //rocket flame animation
        TextureRegion[] flameFrames = new TextureRegion[9];
        TextureRegion[][] tmpFrames2 = TextureRegion.split(new Texture(Gdx.files.internal("images/spaceship/flame.png")), 90, 90);
        int index2 = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                flameFrames[index2++] = tmpFrames2[j][i];
            }
        }
        flameAnimation = new Animation<TextureRegion>(1f/20f,flameFrames);

        //sparkle animation
        TextureRegion[] sparkleFrames = new TextureRegion[30];
        TextureRegion[][] tmpFrames3 = TextureRegion.split(new Texture(Gdx.files.internal("images/worldGeneration/sparkle.png")), 47, 47);
        int index3 = 0;
        for (int i = 0; i < 5; i++){
            for(int j = 0; j < 6; j++) {
                sparkleFrames[index3++] = tmpFrames3[j][i];
            }
        }
        sparkleAnimation = new Animation<TextureRegion>(1f/10f,sparkleFrames);

        //blinking red light animation
        TextureRegion[] redFrames = new TextureRegion[9];
        TextureRegion[][] tmpFrames4 = TextureRegion.split(new Texture(Gdx.files.internal("images/alien_ship/BlinkingRedLight.png")), 90, 90);
        int index4 = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                redFrames[index4++] = tmpFrames4[j][i];
            }
        }
        redLightAnimation = new Animation<TextureRegion>(1f/10f,redFrames);

        //atmosphere animation
        TextureRegion[] atmoFrames = new TextureRegion[9];
        TextureRegion[][] tmpFrames5 = TextureRegion.split(new Texture(Gdx.files.internal("images/planet/Atmosphere.png")), 81, 81);
        int index5 = 0;
        for (int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++) {
                atmoFrames[index5++] = tmpFrames5[i][j];
            }
        }
        atmosphereAnimation = new Animation<TextureRegion>(1f/3f, atmoFrames);

        //shield animation
        TextureRegion[] shieldFrames = new TextureRegion[4];
        TextureRegion[][] tmpFrames6 = TextureRegion.split(new Texture(Gdx.files.internal("images/spaceship/Spaceship_Shield.png")), 340, 340);
        int index6 = 0;
        for (int i = 0; i < 2; i++){
            for(int j = 0; j < 2; j++) {
                shieldFrames[index6++] = tmpFrames6[i][j];
            }
        }
        shieldAnimation = new Animation<TextureRegion>(1f/3f, shieldFrames);
    }
}
