package com.game.objects.ship.shipComponent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.RocketKrieg;
import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.collision.CollisionEvent;
import com.game.worldGeneration.ChunkManager;

/**
 *  Missile entity class.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-05-08)
 */
public class Missile extends GameEntity implements Entity {
    private final float SPEED_MULTIPLIER = 40000f;
    private final float MAX_DISTANCE = 1200f;
    private Sprite missile;

    /**
     * Constructor for Missile entity.
     * @param position starting position
     * @param velocity starting velocity
     * @param acceleration acceleration
     * @param angle starting angle
     */
    public Missile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle, float angularVelocity) {
        sizeY = 3;
        sizeX = 9;
        this.position = new Vector2(position.x+5,position.y+35);
        this.velocity = new Vector2(velocity.x,velocity.y);
        this.acceleration = new Vector2(acceleration.x,acceleration.y);
        this.angle = angle;
        missile = AssetStorage.missile;
        missile.setRotation(90);
        missile.setSize(sizeX,sizeY);

        //setup hitbox
        Rectangle bounds = new Rectangle(position.x+sizeX,position.y+sizeY,sizeX,sizeY);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2,bounds.height/2);

        ID = 8;
    }

    /**
     * Render Missile.
     * @param batch
     */
    public void render(SpriteBatch batch) {
        missile.setRotation((float)Math.toDegrees(angle));
        super.render(batch, missile, Math.toDegrees(angle)-90);
    }

    /**
     * Update missile.
     */
    public void update(float delta) {
        move(delta);
        accel(delta);
        if(position.dst(RocketKrieg.getShip().getPosition())>MAX_DISTANCE) {
            ChunkManager.removeEntity(this);
            ChunkManager.addEntity(new CollisionEvent(position.x,position.y,30));
        }
    }

    /**
     * Accelerate forward. 
     */
    public void accel(float delta) {
        acceleration.add((float)Math.cos(angle)*SPEED_MULTIPLIER*delta,(float)Math.sin(angle)*SPEED_MULTIPLIER*delta);
    }
}
