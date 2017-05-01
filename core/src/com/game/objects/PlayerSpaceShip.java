package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Johan on 27/04/2017.
 */
public class PlayerSpaceShip extends GameEntity {
    //Multipliers
    private final float SPEED_MULTIPLIER = 5000F;
    private final float MAX_ANGULARVELOCITY = 10F;
    private final float TURNING_SPEED = 3F;
    //Textures
    private Sprite sprite, offLeft, offRight, on, onLeft, onRight;

    /**
     * Initialise player ship.
     */
    public PlayerSpaceShip() {
        super();
        //load images
        sprite = new Sprite(new Texture(Gdx.files.internal("images/spaceship/Spaceship1.png")));
        sprite.setSize(80,80);
        sprite.setOriginCenter();
    }

    /**
     * Render the players ship.
     */
    public void render(SpriteBatch batch) {
        Sprite img = sprite;
        /* <-- Commented until all textures are created. -->
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            img = on;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            img = offLeft;
            if(Gdx.input.isKeyPressed(Input.Keys.W)) {
                img = onLeft;
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            img = offRight;
            if(Gdx.input.isKeyPressed(Input.Keys.W)) {
                img = onRight;
            }
        }
        */
        super.render(batch,img);
    }

    /**
     * Update ship values based on input.
     * @param delta
     */
    public void update(float delta) {
        move(delta);
        //flight controls
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            accel(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            turnLeft(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            turnRight(delta);
        }
        //reset position to center of the screen.
        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            setPos(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        }
    }

    /**
     * Turn right by changing the spaceship angular velocity.
     */
    public void turnRight(float delta) {
        if(angularVelocity<MAX_ANGULARVELOCITY) {
            angularVelocity += TURNING_SPEED*delta;
        }
    }

    /**
     * Turn left by changing the spaceship angular velocity.
     */
    public void turnLeft(float delta) {
        if(angularVelocity>-MAX_ANGULARVELOCITY) {
            angularVelocity -= TURNING_SPEED*delta;
        }
    }

    /**
     * Accelerate forward.
     */
    public void accel(float delta) {
        acceleration.set((float)Math.cos(angle)*SPEED_MULTIPLIER*delta,(float)Math.sin(angle)*SPEED_MULTIPLIER*delta);
    }
}
