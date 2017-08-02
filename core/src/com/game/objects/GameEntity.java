package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.RocketKrieg;
import com.game.objects.alien.Laser;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.objects.ship.shipComponent.Missile;

/**
 * GameEntity superclass.
 * @author Johan von Hacht
 * @version 1.0 (2017-05-01)
 */
public class GameEntity{
    public Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected float angularVelocity;
    protected float angle;
    protected Polygon hitbox;
    protected float timeElapsed;

    /**
     * Constructor for GameEntity.
     */
    public GameEntity() {
        position = new Vector2();
        velocity = new Vector2();
        acceleration = new Vector2();
        angle = (float)Math.toRadians(90);
        timeElapsed = 0;
    }

    /**
     * Render the object.
     * @param batch SpriteBatch
     */
    public void render(SpriteBatch batch, Sprite sprite, double angle) {
        sprite.setOriginCenter();
        sprite.setRotation((float)angle-90f);
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
    }

    /**
     * Method to move object.
     */
    public void move(float delta) {
        velocity.add(acceleration.x*delta,acceleration.y*delta);
        if(this instanceof PlayerSpaceShip || this instanceof Missile || this instanceof Laser) {
            position.add(velocity.x/30,velocity.y/30);
        } else {
            position.add(velocity.x*delta,velocity.y*delta);
        }

        acceleration.set(0,0);

        angle = (float)Math.toRadians((Math.toDegrees(angle) + angularVelocity*delta) % 360);

        hitbox.setPosition(position.x,position.y);
        hitbox.setRotation((float)Math.toDegrees(angle)-90);

        timeElapsed += delta;
    }

    /**
     * Set the position of the object. Resets acceleration, velocity and angular velocity.
     * @param x position
     * @param y position
     */
    public void setPos(float x,float y) {
        position.set(x,y);
        velocity.set(0,0);
        acceleration.set(0,0);
        angularVelocity = 0;
        angle = (float)Math.toRadians(+90);
    }

    /**
     * Get position of object.
     * @return position Vector2
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Get entity polygon.
     * @return Polygon of entity.
     */
    public Polygon getHitBox() {
        return hitbox;
    }

    /**
     * Get velocity of object.
     * @return velocity Vector2
     */
    public Vector2 getVelocity() {
        return velocity;
    }
}
