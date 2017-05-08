package com.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;

/**
 * Created by Johan on 08/05/2017.
 */
public class Missile extends GameEntity implements Entity {
    private final float SPEED_MULTIPLIER = 15000;
    private Sprite missile;

    public Missile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle) {
        float height = 5;
        float width = 15;
        this.position = new Vector2(position.x+5,position.y+35);
        this.velocity = new Vector2(velocity.x,velocity.y);
        this.acceleration = new Vector2(acceleration.x,acceleration.y);
        this.angle = angle;
        missile = AssetStorage.missile;
        missile.setRotation(90);
        missile.setSize(width,height);

        //setup hitbox
        Rectangle bounds = new Rectangle(position.x+width,position.y+height,width,height);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2,bounds.height/2);
    }

    public void render(SpriteBatch batch) {
        missile.setOriginCenter();
        missile.setRotation((float)Math.toDegrees(angle)+180);
        missile.setPosition(position.x,position.y);

        super.render(batch, missile);
    }


    public void update(float delta) {
        move(delta);
        accel(delta);
    }

    /**
     * Accelerate forward.
     */
    public void accel(float delta) {
        acceleration.set((float)Math.cos(angle)*SPEED_MULTIPLIER*delta,(float)Math.sin(angle)*SPEED_MULTIPLIER*delta);
    }
}
