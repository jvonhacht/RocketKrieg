package com.game.worldGeneration;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.objects.*;

import java.util.Random;

/**
 * Tile creation class.
 * @author Johan von Hacht & David Johansson 
 * @version 1.1 (2017-05-09)
 */
public class Tile {
    private Sprite img;
    public static final int TILE_SIZE = 512;
    private Vector2 position;
    private Random rand = new Random();
    private Entity entity;

    /**
     * Constructor for Tile.
     * @param x tile spawn coordinate
     * @param y tile spawn coordinate
     */
    public Tile(int x, int y) {
        //generate random percent
        int percent = rand.nextInt(100);

        //choose random entity
        if(percent <= 75){
            entity = new Asteroid(x, y);
        }
        else if(percent <= 85){
            entity = new AlienShip(x, y);
        }
        else if(percent <= 90){
            entity = new AlienShipSpecial(x, y);
        }
        else if(percent <= 95){
            entity = new ScorePoint(x,y);
        }
        else{
            entity = new Planet(x, y);
        }

        //choose random tile texture.
        if(percent < 98) {
            img= AssetStorage.tile1;
        } else {
            img = AssetStorage.tile2;
        }

        //initialize starting position
        position = new Vector2(x,y);
    }
     /**
     * Get Sprite of tile.
     * @return Sprite img.
     */
    public Sprite getImg() {
        return img;
    }

    /**
     * Get x position of tile.
     * @return x coordinate
     */
    public float getX() {return position.x;}

    /**
     * Get y position of tile.
     * @return y coordinate
     */
    public float getY() {return position.y;}

    /**
     * Get tile entity.
     * @return entity
     */
    public Entity getEntity() {return entity;}
}