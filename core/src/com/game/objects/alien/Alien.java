package com.game.objects.alien;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.RocketKrieg;
import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;

/**
 * Created by JohanvonHacht on 2017-08-09.
 */
public class Alien extends GameEntity implements Entity {
    protected PlayerSpaceShip ship;
    protected float MOVING_SPEED;
    protected float ACCELERATION;
    protected float RELOAD_TIME;
    protected Sprite img;

    public Alien() {
        super();
        ship = RocketKrieg.getShip();
    }

    /**
     * Render alien.
     * @param batch
     */
    public void render(SpriteBatch batch) {
        img.setSize(sizeX,sizeY);
        img.setRotation((float)Math.toDegrees(angle));
        super.render(batch, img, Math.toDegrees(angle));

        float width = (float)hitpoints / (float)totalHealth * 100;
        healthBar.draw(batch, position.x-sizeX,position.y+sizeY, width, 2f);
    }

    /**
     * Update alien.
     * @param delta
     */
    public void update(float delta) {
        move(delta);
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
