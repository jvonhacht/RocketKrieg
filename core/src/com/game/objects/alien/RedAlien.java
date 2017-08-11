package com.game.objects.alien;

import com.badlogic.gdx.Gdx;
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
import com.game.worldGeneration.ZoneManager;

/**
 *  RedAlien entity class
 *  @author David Johansson 
 *  @version 1.0 (2017-05-09)
 */
public class RedAlien extends Alien implements Entity {
    private Animation<TextureRegion> animation;

    /**
     * Constructor of RedAlien entity.
     * @param x spawn coordinate
     * @param y spawn coordinate
     */
    public RedAlien(float x, float y){
        super();
        sizeX = 40;
        sizeY = 55;

        //set properties
        img = AssetStorage.alienShipSpecial;
        animation = AssetStorage.redLightAnimation;
        position.set(x,y);

        //setup hitbox
        Rectangle bounds =  new Rectangle(position.x, position.y, sizeX, sizeY);
        hitbox = new Polygon(new float[]{0, 0, bounds.width, 0, bounds.width, bounds.height, 0, bounds.height});
        hitbox.setOrigin(bounds.width/2, bounds.height/2);

        //get ship
        hitpoints = 5* ZoneManager.getZone();
        totalHealth = hitpoints;
        MOVING_SPEED = 120f;
        ACCELERATION = 180f;

        ID = 21;
    }

    /**
     * Method to render asteroid.
     * @param batch SpriteBatch batch.
     */
    public void render(SpriteBatch batch){
        super.render(GameEntry.batch);
        GameEntry.batch.draw(animation.getKeyFrame(timeElapsed, true), position.x + sizeX/2 - 10, position.y + sizeY/2 - 2, 20f, 20f);
    }

    /**
     * Update asteroid position.
     */
    public void update(float delta){
        super.update(delta);
        //get position of playersip
        Vector2 shipPosition = ship.position;
        float distance = shipPosition.dst(position);

        //move alien ship if near
        if(distance < 800 && !avoiding) {

            if (shipPosition.x > position.x) {
                moveRight(delta);
            }
            if (shipPosition.x < position.x) {
                moveLeft(delta);
            }
            if (shipPosition.y > position.y) {
                moveUp(delta);
            }
            if (shipPosition.y < position.y) {
                moveDown(delta);
            }
        }
    }
}
