package com.game.worldGeneration;

/**
 * Created by Johan on 02/05/2017.
 */
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.objects.Asteroid;
import com.game.objects.Entity;

import java.util.Random;

/**
 * Created by Johan on 27/04/2017.
 */
public class Tile {
    private Sprite img;
    public static final int TILE_SIZE = 512;
    private Vector2 position;
    private Random rand = new Random();
    private Entity entity;

    public Tile(int x, int y) {
        entity = new Asteroid(x, y);
        position = new Vector2(x,y);

        //choose random tile texture.
        int number = rand.nextInt(100);
        if(number<99.8) {
            img= AssetStorage.tile1;
        } else {
            img = AssetStorage.tile2;
     }
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
     * @return
     */
    public float getX() {return position.x;}
    public float getY() {return position.y;}
    public Entity getEntity() {return entity;}
}