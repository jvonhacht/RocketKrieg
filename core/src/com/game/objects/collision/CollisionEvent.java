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

/**
 * CollisionEvent class handling
 * a collsion event
 * @author Johan von Hacht
 * @version 1.0 (2017-05-08)
 */
public class CollisionEvent implements Entity {
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
    public CollisionEvent(float x, float y) {
        position = new Vector2(x,y);
        angle = MathUtils.random(360);
        sprite = AssetStorage.debris;
        hitbox = new Polygon(new float[]{0,0,0,0,0,0,0,0});
        animation = AssetStorage.explosionAnimation;
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
        sprite.setRotation(angle);
        sprite.setSize(120,120);
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
    }

    /**
     * Getting the time elapsed.
     * @param delta time since last frame.
     */
    public void update(float delta) {
        timeElapsed += delta;
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
}
