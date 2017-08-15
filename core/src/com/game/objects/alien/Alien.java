package com.game.objects.alien;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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
    float MOVING_SPEED;
    float ACCELERATION;
    float RELOAD_TIME;
    Sprite img;
    boolean avoiding;
    int sightDistance;
    float directionTimer;
    int direction;

    public Alien() {
        super();
        ship = RocketKrieg.getShip();
        avoiding = false;
        sightDistance = MathUtils.random(600,800);
        direction = 0;
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
        directionTimer += delta;
        //Check for nearby planets to avoid.
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
        //get position of playersip
        Vector2 shipPosition = ship.position;
        float distance = shipPosition.dst(position);

        //move alien ship if spaceship is near
        if(distance < sightDistance && !avoiding) {
            if (shipPosition.x > position.x) {
                moveRight(delta);
            }
            if (shipPosition.x < position.x) {
                moveLeft(delta);
            }
            if (shipPosition.y > position.y) {
                moveUp(delta);
            }
            if (shipPosition.y < position.y) {
                moveDown(delta);
            }
        } else if(!avoiding) {
            wander(delta);
        }
        move(delta);

        //get new wander direction.
        if(directionTimer>2) {
            direction = MathUtils.random(8);
            directionTimer = 0;
        }
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
     * Wander when a player is not around.
     */
    public void wander(float delta) {
        switch(direction) {
            case 0:
                moveLeft(delta);
                break;
            case 1:
                moveRight(delta);
                break;
            case 2:
                moveUp(delta);
                break;
            case 3:
                moveDown(delta);
                break;
            case 4:
                moveLeft(delta/2);
                moveUp(delta/2);
                break;
            case 5:
                moveRight(delta/2);
                moveUp(delta/2);
                break;
            case 6:
                moveLeft(delta/2);
                moveDown(delta/2);
                break;
            case 7:
                moveRight(delta/2);
                moveDown(delta/2);
                break;
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

    /**
     * Get alien hitpoints.
     * @return
     */
    public int getHitpoints() {
        return hitpoints;
    }
}
