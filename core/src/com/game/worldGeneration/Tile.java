package com.game.worldGeneration;

/**
 * Created by Johan on 02/05/2017.
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

/**
 * Created by Johan on 27/04/2017.
 */
public class Tile {
    private Sprite img;
    public static final int TILE_SIZE = 512;
    private int x;
    private int y;
    private Random rand = new Random();

    public Tile(int x, int y) {
        this.x=x;
        this.y=y;

        int number = rand.nextInt(100);
        if(number<80) {
            img=AssetStorage.tile1;
        } else if (number<90) {
            img=AssetStorage.tile2;
        } else {
            img=AssetStorage.tile3;
        }
    }

    public Sprite getImg() {
        return img;
    }

    public int getX() {return x;}
    public int getY() {return y;}
}