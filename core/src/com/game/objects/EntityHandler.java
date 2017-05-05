package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.worldGeneration.ChunkManager;
import com.game.worldGeneration.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Johan on 28/04/2017.
 */
public class EntityHandler {
    private SpriteBatch batch;
    public static PlayerSpaceShip ship;

    public EntityHandler(SpriteBatch batch) {
        this.batch = batch;
        ship = new PlayerSpaceShip();
    }

    /**
     * Render all entities in the world.
     */
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ship.update(delta);
        ship.render(batch);
    }

    /**
     * Get playership
     * @return PlayerSpaceShip ship
     */
    public PlayerSpaceShip getShip() {
        return ship;
    }
}