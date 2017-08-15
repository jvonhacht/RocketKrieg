package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.game.AssetStorage;
import com.game.GameEntry;

import static com.game.menus.Settings.sfxEnabled;

/**
 * Components widget class complementing
 * the components menu.
 * @author David Johansson
 * @version 1.0 (2017-08-10)
 */
public class ComponentWidget{
    //Sprites
    private Sprite componentsBox;
    private Sprite compBackgound;
    private Sprite compSymbol;
    private Sprite compBar;
    private Sprite plusDark;
    private Sprite minusDark;

    private float MARGIN;
    private float HEIGHT;
    private float type;
    private String compName;
    private String compInfo;
    private int currentMk;
    private float timeElapsed;

    //Fonts
    private BitmapFont molotFont;
    private BitmapFont smallFont;

    //Sound effects
    private Sound soundEffect1;
    private Sound soundEffect2;


    /**
     * Constructor for the ComponentWidget
     * @param type type of component
     * @param currentMk current component index
     * @param componentsBox componentBox sprite
     */
    public ComponentWidget(int type, int currentMk, Sprite componentsBox){
        this.type = type;
        this.componentsBox = componentsBox;
        this.currentMk = currentMk;
        compBackgound = AssetStorage.compBackground;
        plusDark = AssetStorage.plusDark;
        minusDark = AssetStorage.minusDark;

        switch(type){
            case 1:
                compSymbol = AssetStorage.compShield;
                compName = "SHIELD COMPONENT";
                compInfo = "The shield component grants the\nplayer extra hits from enemies\nbefore taking damage";
                break;
            case 2:
                compSymbol = AssetStorage.compReload;
                compName = "RELOAD COMPONENT";
                compInfo = "The reload component allows the\nplayer to shoot missiles faster";
                break;
            case 3:
                compSymbol = AssetStorage.compSpeed;
                compName = "SPEED COMPONENT";
                compInfo = "The speed component makes the\nship move faster";
                break;
            case 4:
                compSymbol = AssetStorage.compBoost;
                compName = "BOOST COMPONENT";
                compInfo = "The boost component allows the\nplayer to accelerate faster by\nholding down shift";
                break;
            case 5:
                compSymbol = AssetStorage.compTurning;
                compName = "TURNING COMPONENT";
                compInfo = "Upgrading this component makes\nthe ship turn faster";
        }

        if(type <= 4){
            MARGIN = (type - 1)*(compBackgound.getWidth()) + 23*type;
        }
        else{
            MARGIN = (type - 5)*(compBackgound.getWidth()) + (type - 4)*23;
        }

        if(type <= 4){
            HEIGHT = Gdx.graphics.getHeight()/2 + componentsBox.getHeight()/2 - compBackgound.getHeight() - 75;
        }
        else{
            HEIGHT = Gdx.graphics.getHeight()/2 + componentsBox.getHeight()/2 - 2*compBackgound.getHeight() - 98;
        }

        timeElapsed = 0;

        //Sound effects
        soundEffect1 = Gdx.audio.newSound(Gdx.files.internal("sounds/Play_Sound.mp3"));
        soundEffect2 = Gdx.audio.newSound(Gdx.files.internal("sounds/SettingsButtonPress.mp3"));

        initializeFonts();
    }

    /**
     * Render method for the
     * component widget
     * @param delta time since last frame
     */
    public void renderComponent(float delta){
        GameEntry.batch.draw(compBackgound, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN, HEIGHT);
        GameEntry.batch.draw(compSymbol, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 5, HEIGHT + compBackgound.getHeight() - compSymbol.getHeight() - 5);
        molotFont.draw(GameEntry.batch, compName, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 35, HEIGHT + compBackgound.getHeight() - 10);
        smallFont.draw(GameEntry.batch, compInfo, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 10, HEIGHT + compBackgound.getHeight() - 97);

        if(currentMk == 4){
            GameEntry.batch.draw(plusDark, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 216, HEIGHT + 69);
        }
        if(currentMk == 0){
            GameEntry.batch.draw(minusDark, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 11, HEIGHT + 68);
        }

        //Press minus
        if(type <= 4){
            if(buttonSquare(Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 10, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2 + 132, 23, 2) && currentMk > 0){
                currentMk -= 1;
            }
        }
        else{
            if(buttonSquare(Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 10, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2 + 307, 23, 2) && currentMk > 0){
                currentMk -= 1;
            }
        }

        //Press plus
        if(type <= 4){
            if(buttonSquare(Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 215, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2 + 132, 23, 2) && currentMk < 4){
                currentMk += 1;
            }
        }
        else{
            if(buttonSquare(Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 215, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2 + 307, 23, 2) && currentMk < 4){
                currentMk += 1;
            }
        }

        switch(currentMk){
            case 0:
                compBar = AssetStorage.compMk1;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 1:
                compBar = AssetStorage.compMk2;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 2:
                compBar = AssetStorage.compMk3;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 3:
                compBar = AssetStorage.compMk4;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 4:
                compBar = AssetStorage.compMk5;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
        }

        timeElapsed += delta;
    }

    /**
     * Returns true if the player presses intended region
     * inside the square button. Also plays sound effect
     * if its enabled
     * @param xMin minimum x value
     * @param yMin minimum y value
     * @param squareWidth square width
     * @param sfxType sound effect type
     */
    public boolean buttonSquare(float xMin, float yMin, float squareWidth, int sfxType){
        int xPos = Gdx.input.getX();
        int yPos = Gdx.input.getY();
        if (xPos >= xMin && xPos <= xMin + squareWidth){
            if(yPos >= yMin && yPos <= yMin + squareWidth){
                if (Gdx.input.isTouched() && timeElapsed > 0.2f){
                    timeElapsed = 0;
                    switch(sfxType){
                        case 1:
                            if(sfxEnabled){
                                soundEffect1.play(1.0f);
                            }
                            break;
                        case 2:
                            if(sfxEnabled){
                                soundEffect2.play(1.0f);
                            }
                            break;
                        case 3:
                            soundEffect2.play(1.0f);
                            break;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method for getting the index of
     * the active components.
     * @return active component index
     */
    public int getCurrentComp(){
        return currentMk;
    }


    /**
     * Method for initializing
     * the needed fonts
     */
    public void initializeFonts(){
        //Initialize molot font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Molot.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        molotFont = generator.generateFont(parameter);
        generator.dispose();

        //Initialize small font
        FreeTypeFontGenerator generator2 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Tw_Cen_MT_Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 15;
        parameter2.shadowColor = Color.BLACK;
        //parameter2.shadowOffsetX = 1;
        //parameter2.shadowOffsetY = 1;
        smallFont = generator2.generateFont(parameter2);
        generator2.dispose();
    }
}
