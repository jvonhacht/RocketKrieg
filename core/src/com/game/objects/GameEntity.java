package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

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
    public void render(SpriteBatch batch, Sprite sprite) {
        sprite.setOriginCenter();
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
    }

    /**
     * Method to move object.
     */
    public void move() {
        velocity.add(acceleration.x*Gdx.graphics.getDeltaTime(),acceleration.y*Gdx.graphics.getDeltaTime());
        position.add(velocity.x/30,velocity.y/30);
        acceleration.set(0,0);

        angle = (float)Math.toRadians((Math.toDegrees(angle) + angularVelocity*Gdx.graphics.getDeltaTime()) % 360);

        hitbox.setPosition(position.x,position.y);
        hitbox.setRotation((float)Math.toDegrees(angle)-90);

        timeElapsed += Gdx.graphics.getDeltaTime();
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
