package com.game.objects.collision;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.Entity;
import com.game.worldGeneration.ChunkManager;

/**
 * CollisionEvent class handling
 * a collsion event
 * @author Johan von Hacht
 * @version 1.0 (2017-05-08)
 */
public class CollisionEvent implements Entity {
    private int ID;
    private float sizeX;
    private float sizeY;
    private float angle;
    private Sprite sprite;
    private Vector2 position;
    private Polygon hitbox;
    private float timeElapsed;
    private Animation<TextureRegion> animation;

    /**
     * Constructor for CollisionEvent class.
     * @param x coordinate for collision
     * @param y coordinate for collision
     */
    public CollisionEvent(float x, float y, float size) {
        position = new Vector2(x,y);
        angle = MathUtils.random(360);
        sprite = AssetStorage.debris;
        hitbox = new Polygon(new float[]{0,0,0,0,0,0,0,0});
        animation = AssetStorage.explosionAnimation;
        ID = 99;
        sizeX = size;
        sizeY = size;
    }

    /**
     * Renders the collision graphics.
     * @param batch SpriteBatch
     */
    public void render(SpriteBatch batch) {
        if (!animation.isAnimationFinished(timeElapsed)) {
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed), position.x, position.y);
        }

        //render debris.
        sprite.setOriginCenter();
        sprite.setCenterX(position.x+sizeX/2);
        sprite.setCenterY(position.y+sizeY/2);
        sprite.setRotation(angle);
        sprite.setSize(sizeX,sizeY);
        sprite.setPosition(position.x,position.y);
        sprite.setAlpha(1-(timeElapsed/10));
        sprite.draw(batch);
    }

    /**
     * Getting the time elapsed.
     */
    public void update(float delta) {
        timeElapsed += delta;
        if(timeElapsed>10) {
            ChunkManager.removeEntity(this);
        }
    }

    /**
     * Get position of collision
     * @return position
     */
    public Vector2 getPosition() {
        return position;
    }

    /**
     * Method for getting the hitbox
     * @return hitbox
     */
    public Polygon getHitBox() {
        return hitbox;
    }

    public int getId() {
        return ID;
    }

    public Vector2 getVelocity() {
        return new Vector2(0,0);
    }

    public Vector2 getAcceleration() {
        return new Vector2(0,0);
    }

    public float getAngularVelocity() {
        return 0;
    }

    public float getAngle() {
        return angle;
    }

    public float getSizeX() { return sizeX; }
    public float getSizeY() { return sizeY; }

    public boolean hit(double amount) { return false; }
}