package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Johan on 28/04/2017.
 */
public class EntityHandler {
    private SpriteBatch batch;
    private ArrayList<Entity> entities;
    private PlayerSpaceShip ship;

    public EntityHandler(SpriteBatch batch) {
        this.batch = batch;
        entities = new ArrayList<Entity>();
        ship = new PlayerSpaceShip();
        //add 10 asteroids
        for (int i=0; i<10 ; i++) {
            entities.add(new Asteroid());
        }
    }

    /**
     * Render all entities in the world.
     */
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        ship.update(delta);
        ship.render(batch);

        //render and update all entities.
        for (int i=0; i<entities.size() ; i++) {
            Entity entity = entities.get(i);
            entity.update(delta);
            entity.render(batch);
        }
    }

    /**
     * Get playership
     * @return PlayerSpaceShip ship
     */
    public PlayerSpaceShip getShip() {
        return ship;
    }
}