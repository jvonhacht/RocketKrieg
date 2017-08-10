package com.game.objects.alien;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.RocketKrieg;
import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.Planet;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;

import java.util.ArrayList;

/**
 * Created by JohanvonHacht on 2017-08-09.
 */
public class Alien extends GameEntity implements Entity {
    protected PlayerSpaceShip ship;
    protected float MOVING_SPEED;
    protected float ACCELERATION;
    protected float RELOAD_TIME;
    protected Sprite img;
    protected boolean avoiding;

    public Alien() {
        super();
        ship = RocketKrieg.getShip();
        avoiding = false;
    }

    /**
     * Render alien.
     * @param batch
     */
    public void render(SpriteBatch batch) {
        img.setSize(sizeX,sizeY);
        img.setRotation((float)Math.toDegrees(angle));
        super.render(batch, img, Math.toDegrees(angle));

        float width = (float)hitpoints / (float)totalHealth * 100;
        healthBar.draw(batch, position.x-sizeX,position.y+sizeY, width, 2f);
    }

    /**
     * Update alien.
     * @param delta
     */
    public void update(float delta) {
        ArrayList<Entity> entities = ChunkManager.getEntitiesInTile(position);
        Planet planet = null;
        if(entities!=null && entities.size()>0) {
            Entity ent = entities.get(0);
            if(ent instanceof Planet) {
                planet = (Planet)ent;
            }
        }
        if(planet!=null) {
            avoidPlanet(planet, delta);
        }
        move(delta);
    }

    /**
     * Avoid if there is any nearby planet.
     * @param planet
     */
    public void avoidPlanet(Planet planet, float delta) {
        Vector2 planetPosition = planet.getPosition();
        float size = planet.getSizeX();
        float distance = planetPosition.dst(position);

        //move alien ship if near
        if(distance < size+100) {
            avoiding = true;
            if (planetPosition.x > position.x) {
                moveLeft(delta);
            }
            if (planetPosition.x < position.x) {
                moveRight(delta);
            }
            if (planetPosition.y > position.y) {
                moveDown(delta);
            }
            if (planetPosition.y < position.y) {
                moveUp(delta);
            }
        } else {
            avoiding = false;
        }
    }

    /**
     * Move right by changing the alien ship velocity.
     */
    public void moveRight(float delta) {
        if(velocity.x < MOVING_SPEED) {
            velocity.x += ACCELERATION*delta;
        }
    }

    /**
     * Move left by changing the alien ship velocity.
     */
    public void moveLeft(float delta) {
        if(velocity.x > -1*MOVING_SPEED) {
            velocity.x -= ACCELERATION*delta;
        }
    }

    /**
     * Move up by changing the alien ship velocity.
     */
    public void moveUp(float delta) {
        if(velocity.y < MOVING_SPEED) {
            velocity.y += ACCELERATION*delta;
        }
    }


    /**
     * Move down by changing the alien ship velocity.
     */
    public void moveDown(float delta) {
        if(velocity.y > -1*MOVING_SPEED) {
            velocity.y -= ACCELERATION*delta;
        }
    }

    /**
     * Slow down movement if too near.
     */
    public void brake(float delta) {
        if(velocity.x > 0) {
            velocity.x -= 2 * ACCELERATION*delta;
        }
        else{
            velocity.x += 2 * ACCELERATION*delta;
        }
        if(velocity.y > 0) {
            velocity.y -= 2 * ACCELERATION*delta;
        }
        else{
            velocity.y += 2 * ACCELERATION*delta;
        }
    }

    /**
     * Fire lasers.
     */
    public void fireLaser(float angle){
        ChunkManager.addEntity(new Laser(position, velocity, acceleration, angle));
    }
}
