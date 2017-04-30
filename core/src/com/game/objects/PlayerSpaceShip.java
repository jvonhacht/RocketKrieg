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
public class PlayerSpaceShip {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private float angularVelocity;
    private float angle;
    //Multipliers
    private final float SPEED_MULTIPLIER = 5000F;
    private final float MAX_ANGULARVELOCITY = 10F;
    private final float TURNING_SPEED = 3F;
    //Textures
    private Sprite off, offLeft, offRight, on, onLeft, onRight;

    /**
     * Initialise player ship.
     */
    public PlayerSpaceShip() {
        //load images
        off = new Sprite(new Texture(Gdx.files.internal("images/spaceship/SpaceShipGit.png")));
        off.setSize(60,60);
        off.setOriginCenter();

        //set ship properties
        position = new Vector2(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        velocity = new Vector2();
        acceleration = new Vector2();
        angle = 0;
    }

    /**
     * Render the players ship.
     */
    public void render(SpriteBatch batch) {
        Sprite img = off;
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
        img.setRotation((float)Math.toDegrees(angle)-90);
        img.setPosition(position.x,position.y);
        img.draw(batch);
    }

    public void update(float delta) {
        move(delta);
        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            setPos(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

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
    }

    /**
     * Move the spaceship.
     * @param delta time since last frame
     */
    public void move(float delta) {
        velocity.add(acceleration.x*delta,acceleration.y*delta);
        position.add(velocity.x*delta,velocity.y*delta);
        acceleration.set(0,0);

        angle = (float)Math.toRadians((Math.toDegrees(angle) + angularVelocity) % 360);
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

    /**
     * Set the position of the ship. Resets acceleration, velocity and angular velocity.
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
     * Get position of spacecraft.
     * @return position Vector2
     */
    public Vector2 getPosition() {
        return position;
    }
}
