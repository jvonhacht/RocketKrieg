package com.game.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.ship.shipComponent.shieldComponent.ShieldComponent;

import static com.game.menus.Settings.sfxEnabled;

/**
 * Components widget class complementing
 * the components menu.
 * @author David Johansson
 * @version 1.0 (2017-08-10)
 */
public class ComponentsWidget{
    //Sprites
    private Sprite componentsBox;
    private Sprite compBackgound;
    private Sprite compSymbol;
    private Sprite compBar;

    //Active components
    public ShieldComponent activeShieldComponent;

    private float MARGIN;
    private float HEIGHT;
    private String compName;
    private BitmapFont molotFont;
    private int currentMk;
    private float timeElapsed;

    //Sound effects
    private Sound soundEffect1;
    private Sound soundEffect2;


    public ComponentsWidget(int type, int currentMk, Sprite componentsBox){
        this.componentsBox = componentsBox;
        this.currentMk = currentMk;
        compBackgound = AssetStorage.compBackground;
        switch(type){
            case 1:
                compSymbol = AssetStorage.compShield;
                compName = "SHIELD COMPONENT";
                break;
            case 2:
                compName = "RELOAD COMPONENT";
                break;
            case 3:
                compName = "SPEED COMPONENT";
                break;
            case 4:
                compName = "BOOST COMPONENT";
                break;
        }
        MARGIN = (type - 1)*(compBackgound.getWidth()) + 23*type;
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

        //Initialize font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Molot.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        molotFont = generator.generateFont(parameter);
        generator.dispose();
    }

    public void renderComponent(float delta){
        GameEntry.batch.draw(compBackgound, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN, HEIGHT);
        GameEntry.batch.draw(compSymbol, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 5, HEIGHT + compBackgound.getHeight() - compSymbol.getHeight() - 5);
        molotFont.draw(GameEntry.batch, compName, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 35, HEIGHT + compBackgound.getHeight() - 10);


        if(buttonSquare(Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 10, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2 + 132, 23, 2) && currentMk > 1){
            currentMk -= 1;
        }
        if(buttonSquare(Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 215, Gdx.graphics.getHeight()/2 - componentsBox.getHeight()/2 + 132, 23, 2) && currentMk < 5){
            currentMk += 1;
        }
        switch(currentMk){
            case 1:

                compBar = AssetStorage.compMk1;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 2:
                compBar = AssetStorage.compMk2;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 3:
                compBar = AssetStorage.compMk3;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 4:
                compBar = AssetStorage.compMk4;
                GameEntry.batch.draw(compBar, Gdx.graphics.getWidth()/2 - componentsBox.getWidth()/2 + MARGIN + 43, HEIGHT + 62);
                break;
            case 5:
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
}
