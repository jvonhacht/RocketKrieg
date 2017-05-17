package com.game.objects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.game.AssetStorage;
import com.game.GameEntry;

/**
 * Created by Johan on 09/05/2017.
 */
public class ScorePoint extends GameEntity implements Entity {
    private Animation<TextureRegion> animation;

    public ScorePoint(float x, float y) {
        super();
        position.set(x+MathUtils.random(0,500),y+MathUtils.random(0,500));

        animation = AssetStorage.sparkleAnimation;
        //setup hitbox
        Rectangle bounds =  new Rectangle(position.x,position.y,40,40);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2, bounds.height/2);
    }

    @Override
    public void render(SpriteBatch batch) {
        GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y);
    }

    @Override
    public void update(float delta) {
        move(delta);
    }
}
