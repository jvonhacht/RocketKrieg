package com.game.objects.collision;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.Entity;

/**
 * Created by Johan on 08/05/2017.
 */
public class CollisionEvent implements Entity {
    private float angle;
    private Sprite sprite;
    private Vector2 position;
    private Polygon hitbox;
    private float elapsedTime;

    public CollisionEvent(float x, float y) {
        position = new Vector2(x,y);
        angle = MathUtils.random(360);
        sprite = AssetStorage.debris;
        hitbox = new Polygon(new float[]{0,0,0,0,0,0,0,0});
        //do animation of explosion.
        //elapsedTime += Gdx.graphics.getDeltaTime();
        //GameEntry.batch.draw(AssetStorage.explosionAnimation.getKeyFrame(elapsedTime,false),0,0);
    }

    @Override
    public void render(SpriteBatch batch) {
        sprite.setOriginCenter();
        sprite.setRotation(angle);
        sprite.setSize(120,120);
        sprite.setPosition(position.x,position.y);
        sprite.draw(batch);
    }

    public void update(float delta) {}

    public Vector2 getPosition() {
        return position;
    }

    public Polygon getHitBox() {
        return hitbox;
    }
}
