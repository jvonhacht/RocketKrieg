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
 *  ScorePoint entity class.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-05-09)
 */
public class ScorePoint extends GameEntity implements Entity {
    private Animation<TextureRegion> animation;

    /**
     * Constructor for ScorePoint entity.
     * @param x spawn coordinate
     * @param y spawn coordinate
     */
    public ScorePoint(float x, float y) {
        super();
        position.set(x,y);

        animation = AssetStorage.sparkleAnimation;
        //setup hitbox
        Rectangle bounds =  new Rectangle(position.x,position.y,40,40);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2, bounds.height/2);
        ID = 4;
    }

    /**
     * Render the ScorePoint
     * @param batch SpriteBatch
     */
    public void render(SpriteBatch batch) {
        GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y);
    }

    /**
     * Update the ScorePoint.
     */
    public void update(float delta) {
        move(delta);
    }
}
