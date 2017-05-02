package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Johan on 01/05/2017.
 */
public class GameEntity implements Entity {
    Vector2 position;
    Vector2 velocity;
    Vector2 acceleration;
    float angularVelocity;
    float angle;

    protected GameEntity() {
        //set ship properties
        position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        velocity = new Vector2();
        acceleration = new Vector2();
        angle = 0;
    }

    /**
     * Render the object.
     * @param batch SpriteBatch
     */
    public void render(SpriteBatch batch, Sprite sprite) {
        sprite.setRotation((float)Math.toDegrees(angle)-90);
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
    }

    /**
     * Method to move object.
     * @param delta time since last frame.
     */
    public void move(float delta) {
        velocity.add(acceleration.x*delta,acceleration.y*delta);
        position.add(velocity.x*delta,velocity.y*delta);
        acceleration.set(0,0);

        angle = (float)Math.toRadians((Math.toDegrees(angle) + angularVelocity) % 360);
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
}
