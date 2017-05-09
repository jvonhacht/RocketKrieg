package com.game.objects;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.RocketKrieg;

/**
 *  AlienShip class
 *  @author David Johansson 
 *  @version 1.0 (2017-05-09)
 */
public class AlienShip extends GameEntity implements Entity{
    private Sprite alienShip;
    private float sizeX;
    private float sizeY;
    private final float movingSpeed = 100F;
    private final float acceleration = 50F;

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
    }

    /**
     * Method to render asteroid.
     * @param batch SpriteBatch batch.
     */
    public void render(SpriteBatch batch){
        alienShip.setSize(sizeX,sizeY);
        alienShip.setOriginCenter();
        alienShip.setPosition(position.x, position.y);

        super.render(batch, alienShip);
    }

    /**
     * Update asteroid position.
     * @param delta time since last frame.
     */
    public void update(float delta){
        move(delta);

        //get position of playersip
        Vector2 shipPosition = RocketKrieg.getShipPosition();

        //move alien ship
        if(shipPosition.x > position.x){
            moveRight(delta);
        }
        if(shipPosition.x < position.x){
            moveLeft(delta);
        }
        if(shipPosition.y > position.y){
            moveUp(delta);
        }
        if(shipPosition.y < position.y){
            moveDown(delta);
        }
    }

    /**
     * Move right by changing the alien ship velocity.
     */
    public void moveRight(float delta) {
        if(velocity.x < movingSpeed) {
            velocity.x += acceleration * delta;
        }
    }

    /**
     * Move left by changing the alien ship velocity.
     */
    public void moveLeft(float delta) {
        if(velocity.x > -1*movingSpeed) {
            velocity.x -= acceleration * delta;
        }
    }

    /**
     * Move up by changing the alien ship velocity.
     */
    public void moveUp(float delta) {
        if(velocity.y < movingSpeed) {
            velocity.y += acceleration * delta;
        }
    }

    /**
     * Move down by changing the alien ship velocity.
     */
    public void moveDown(float delta) {
        if(velocity.y > -1*movingSpeed) {
            velocity.y -= acceleration * delta;
        }
    }
}
