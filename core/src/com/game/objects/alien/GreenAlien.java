package com.game.objects.alien;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.Entity;
import com.game.worldGeneration.ZoneManager;

/**
 *  GreenAlien entity class.
 *  @author Johan von Hacht 
 *  @version 1.0 (2017-05-09)
 */
public class GreenAlien extends Alien implements Entity {
    private float teleportTimer;
    private float teleportationTimer;
    private boolean positionFound;
    private float teleportX;
    private float teleportY;
    private Sprite teleportIcon = AssetStorage.teleportIcon;
    /**
     * Constructor of GreenAlien entity.
     * @param x spawn coordinate
     * @param y spawn coordinate
     */
    public GreenAlien(float x, float y){
        super();
        sizeX = 40;
        sizeY = 55;

        //set properties
        img = AssetStorage.greenAlienShip;
        position.set(x,y);

        //setup hitbox
        Rectangle bounds =  new Rectangle(position.x, position.y, sizeX, sizeY);
        hitbox = new Polygon(new float[]{0, 0, bounds.width, 0, bounds.width, bounds.height, 0, bounds.height});
        hitbox.setOrigin(bounds.width/2, bounds.height/2);

        //get ship
        hitpoints = 10*ZoneManager.getZone();
        totalHealth = hitpoints;

        MOVING_SPEED = 120f;
        ACCELERATION = 180f;
        RELOAD_TIME = 1f;
        ID = 20;

        teleportTimer = 0;
        teleportTimer = 0;
        positionFound = false;
    }

    /**
     * Method to render asteroid.
     * @param batch SpriteBatch batch.
     */
    public void render(SpriteBatch batch){
        if(0<teleportationTimer && teleportationTimer<1) {
            teleportIcon.setOriginCenter();
            teleportIcon.setSize(50,50);
            teleportIcon.setPosition(teleportX,teleportY);
            teleportIcon.draw(GameEntry.batch);
        }
        super.render(GameEntry.batch);
    }

    /**
     * Update asteroid position.
     */
    public void update(float delta){
        super.update(delta);

        float distance = ship.position.dst(position);
        if(teleportTimer>7 && distance<800) {
            teleport(delta, distance);
        }
        //Slow down if too near
        if(distance < 400){
            brake(delta);
            if(timeElapsed > RELOAD_TIME) {
                float shipAngle = (float)Math.atan2(ship.position.y - position.y, ship.position.x - position.x);
                fireLaser(shipAngle);
                timeElapsed = 0;
            }
        }
        teleportTimer +=delta;
    }

    /**
     * Teleport ship.
     */
    private void teleport(float delta, float distance) {
        teleportationTimer += delta;
        angularVelocity += 10;
        if(!positionFound) {
            float angleToTeleport = (float)Math.toRadians(MathUtils.random(361));
            teleportX = (float)Math.cos(angleToTeleport)*distance + ship.position.x;
            teleportY = (float)Math.sin(angleToTeleport)*distance + ship.position.y;
            positionFound = true;
        }
        if(teleportationTimer>1) {
            position.set(teleportX, teleportY);
            teleportTimer = 0;
            teleportationTimer = 0;
            positionFound = false;
            angularVelocity = 0;
            angle = (float)Math.toRadians(90);
        }
    }
}
