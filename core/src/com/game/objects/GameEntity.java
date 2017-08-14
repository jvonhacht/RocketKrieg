package com.game.objects;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.objects.alien.Laser;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.objects.ship.shipComponent.Missile;

/**
 * GameEntity superclass.
 * @author Johan von Hacht
 * @version 1.0 (2017-05-01)
 */
public class GameEntity{
    protected static int ID;
    protected int hitpoints;
    protected int totalHealth;
    protected NinePatch healthBar;
    public Vector2 position;
    protected Vector2 velocity;
    protected Vector2 acceleration;
    protected float angularVelocity;
    protected float angle;
    protected Polygon hitbox;
    protected float timeElapsed;
    protected float sizeX;
    protected float sizeY;

    /**
     * Constructor for GameEntity.
     */
    public GameEntity() {
        ID = 0;
        position = new Vector2();
        velocity = new Vector2();
        acceleration = new Vector2();
        angle = (float)Math.toRadians(90);
        timeElapsed = 0;
        sizeX = 0;
        sizeY = 0;
        hitpoints = 100;
        totalHealth = hitpoints;
        healthBar = new NinePatch(AssetStorage.hBar, 0, 0, 0, 0);
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

    /**
     * Get velocity of object.
     * @return
     */
    public Vector2 getAcceleration() {
        return acceleration;
    }

    /**
     * Get angular velocity of object.
     * @return
     */
    public float getAngularVelocity() {
        return angularVelocity;
    }

    /**
     * Get angle of object.
     * @return
     */
    public float getAngle() {
        return angle;
    }

    /**
     * Return entity id.
     * @return
     */
    public int getId() {
        return ID;
    }

    /**
     * Return size of object.
     * @return
     */
    public float getSizeX() { return sizeX; }
    public float getSizeY() { return sizeY; }

    /**
     * Set all properties for an entity
     */
    public void setProperties(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle,
                              float angularVelocity, float sizeX, float sizeY) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.angle = angle;
        this.angularVelocity = angularVelocity;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    /**
     * If entity hit.
     * @param amount
     */
    public boolean hit(double amount) {
        hitpoints -= amount;
        return !(hitpoints>0);
    }

    public void setVelocity(Vector2 vector) {
        this.velocity = vector;
    }
}
