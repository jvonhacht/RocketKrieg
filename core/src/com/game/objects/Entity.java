package com.game.objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * Entity interface.
 * @author Johan von Hacht
 * @version 1.0 (2017-05-02)
 */
public interface Entity {
    /**
     * Render Entity
     * @param batch
     */
    void render(SpriteBatch batch);

    /**
     * Move entity based on velocity etc.
     */
    void update();

    /**
     * Return position of entity.
     * @return position
     */
    Vector2 getPosition();

    /**
     * Return polygon of entity.
     * @return polygon pol
     */
    Polygon getHitBox();
}
