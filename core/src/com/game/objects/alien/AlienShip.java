package com.game.objects.alien;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.RocketKrieg;
import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;

/**
 *  AlienShip entity class.
 *  @author David Johansson 
 *  @version 1.0 (2017-05-09)
 */
public class AlienShip extends GameEntity implements Entity {
    private Sprite alienShip;
    private float sizeX;
    private float sizeY;
    private PlayerSpaceShip ship;
    private final float MOVING_SPEED = 120f;
    private final float ACCELERATION = 240f;
    private final float RELOAD_TIME = 2f;


    /**
     * Constructor of AlienShip entity.
     * @param x spawn coordinate
     * @param y spawn coordinate
     */
    public AlienShip(float x, float y){
        super();
        sizeX = 40;
        sizeY = 55;

        //set properties
        alienShip = AssetStorage.alienShip;
        position.set(x+MathUtils.random(0,500),y+MathUtils.random(0,500));

        //setup hitbox
        Rectangle bounds =  new Rectangle(position.x, position.y, sizeX, sizeY);
        hitbox = new Polygon(new float[]{0, 0, bounds.width, 0, bounds.width, bounds.height, 0, bounds.height});
        hitbox.setOrigin(bounds.width/2, bounds.height/2);

        //get ship
        ship = RocketKrieg.getShip();

        ID = 5;
    }

    /**
     * Method to render asteroid.
     * @param batch SpriteBatch batch.
     */
    public void render(SpriteBatch batch){
        alienShip.setSize(sizeX,sizeY);
        alienShip.setRotation((float)Math.toDegrees(angle));
        super.render(batch, alienShip, Math.toDegrees(angle));
    }

    /**
     * Update asteroid position.
     */
    public void update(float delta){
        move(delta);

        //get position of playersip
        Vector2 shipPosition = ship.position;
        float distance = shipPosition.dst(position);

        //calculate angle
        float angle = (float) Math.atan2(shipPosition.y - position.y, shipPosition.x - position.x);

        //move alien ship if near
        if(distance < 800) {

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

        //Slow down if too near
        if(distance < 400){
            brake(delta);
            if(timeElapsed > RELOAD_TIME) {
                fireLaser(angle);
                timeElapsed = 0;
            }
        }
    }

    /**
     * Move right by changing the alien ship velocity.
     */
    public void moveRight(float delta) {
        if(velocity.x < MOVING_SPEED) {
            velocity.x += ACCELERATION*delta;
        }
    }

    /**
     * Move left by changing the alien ship velocity.
     */
    public void moveLeft(float delta) {
        if(velocity.x > -1*MOVING_SPEED) {
            velocity.x -= ACCELERATION*delta;
        }
    }

    /**
     * Move up by changing the alien ship velocity.
     */
    public void moveUp(float delta) {
        if(velocity.y < MOVING_SPEED) {
            velocity.y += ACCELERATION*delta;
        }
    }


    /**
     * Move down by changing the alien ship velocity.
     */
    public void moveDown(float delta) {
        if(velocity.y > -1*MOVING_SPEED) {
            velocity.y -= ACCELERATION*delta;
        }
    }

    /**
     * Slow down movement if too near.
     */
    public void brake(float delta) {
        if(velocity.x > 0) {
            velocity.x -= 2 * ACCELERATION*delta;
        }
        else{
            velocity.x += 2 * ACCELERATION*delta;
        }
        if(velocity.y > 0) {
            velocity.y -= 2 * ACCELERATION*delta;
        }
        else{
            velocity.y += 2 * ACCELERATION*delta;
        }
    }

    /**
     * Fire lasers.
     */
    public void fireLaser(float angle){
        ChunkManager.addEntity(new Laser(position, velocity, acceleration, angle));
    }
}
