package com.game.objects.alien;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.RocketKrieg;
import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;
import com.game.worldGeneration.ZoneManager;

/**
 *  PurpleAlien entity class.
 *  @author David Johansson 
 *  @version 1.0 (2017-05-09)
 */
public class PurpleAlien extends Alien implements Entity {
    /**
     * Constructor of PurpleAlien entity.
     * @param x spawn coordinate
     * @param y spawn coordinate
     */
    public PurpleAlien(float x, float y){
        super();
        sizeX = 40;
        sizeY = 55;

        //set properties
        img = AssetStorage.alienShip;
        position.set(x,y);

        //setup hitbox
        Rectangle bounds =  new Rectangle(position.x, position.y, sizeX, sizeY);
        hitbox = new Polygon(new float[]{0, 0, bounds.width, 0, bounds.width, bounds.height, 0, bounds.height});
        hitbox.setOrigin(bounds.width/2, bounds.height/2);

        //get ship
        hitpoints = 10*ZoneManager.getZone();
        totalHealth = hitpoints;

        MOVING_SPEED = 120f;
        ACCELERATION = 180f;
        RELOAD_TIME = 1f;

        ID = 20;
    }

    /**
     * Method to render asteroid.
     * @param batch SpriteBatch batch.
     */
    public void render(SpriteBatch batch){
        super.render(GameEntry.batch);
    }

    /**
     * Update asteroid position.
     */
    public void update(float delta){
        super.update(delta);

        float distance = ship.position.dst(position);
        //Slow down if too near
        if(distance < 400){
            brake(delta);
            if(timeElapsed > RELOAD_TIME) {
                float shipAngle = (float)Math.atan2(ship.position.y - position.y, ship.position.x - position.x);
                fireLaser(shipAngle);
                timeElapsed = 0;
            }
        }
    }
}
