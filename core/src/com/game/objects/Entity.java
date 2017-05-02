package com.game.objects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Johan on 02/05/2017.
 */
public interface Entity {
    /**
     * Render Entity
     * @param batch
     * @param sprite to render.
     */
    void render(SpriteBatch batch, Sprite sprite);

    /**
     * Move entity based on velocity etc.
     * @param delta time since last frame.
     */
    void move(float delta);

    /**
     * Return position of entity.
     * @return
     */
    Vector2 getPosition();
}
