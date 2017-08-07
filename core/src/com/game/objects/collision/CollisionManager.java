package com.game.objects.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.game.RocketKrieg;
import com.game.objects.*;
import com.game.objects.alien.AlienShip;
import com.game.objects.alien.AlienShipSpecial;
import com.game.objects.alien.Laser;
import com.game.objects.ship.shipComponent.Missile;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;
import com.game.worldGeneration.ZoneManager;

import java.util.ArrayList;

/**
 * CollisionManager class handling the
 * collisions of entities.
 * @author Johan von Hacht
 * @version 1.0 (2017-05-08)
 * Created by Johan on 06/05/2017.
 */
public class CollisionManager {
    private final int LARGEEXPLOSION = 120;
    private final int PROJECTILEEXPLOSION = 30;
    private PlayerSpaceShip ship;
    public CollisionManager(PlayerSpaceShip ship) {
        this.ship = ship;
    }

    /**
     * Check if entities in an arraylist collides.
     * @param entities Entities to test for collision.
     */
    public void collides(ArrayList<Entity> entities) {
        //check all entities in array for collisions.
        for (int i=0; i<entities.size()-1; i++){
            for (int j=i+1; j<entities.size(); j++){
                Entity ent1 = entities.get(i);
                Entity ent2 = entities.get(j);
                if(Intersector.overlapConvexPolygons(ent1.getHitBox(),ent2.getHitBox())) {
                    //PROJECTILES <====================================>
                    if(((ent1 instanceof PlayerSpaceShip && ent2 instanceof Missile) || (ent1 instanceof Missile && ent2 instanceof PlayerSpaceShip) || (ent1 instanceof Missile && ent2 instanceof Missile))) {
                        //missile should not blow up ship or each other, do nothing.
                    } else if((ent1 instanceof Laser && (ent2 instanceof AlienShip || ent2 instanceof AlienShipSpecial)) || (ent2 instanceof Laser && (ent1 instanceof AlienShip || ent1 instanceof AlienShipSpecial))) {
                        //do not let alien laser blow up alien.
                    } else if(ent1 instanceof Missile && ent2 instanceof Laser || ent2 instanceof Missile && ent1 instanceof Laser) {
                        //Missile <==> Aliens
                    } else if((ent1 instanceof Missile && ent2 instanceof AlienShip) || (ent2 instanceof Missile && ent1 instanceof AlienShip)) {
                        if(ent1 instanceof Missile) {
                            missileImpact(ent2, ent1, entities);
                        } else {
                            missileImpact(ent1, ent2, entities);
                        }
                    } else if((ent1 instanceof Missile && ent2 instanceof AlienShipSpecial) || (ent2 instanceof Missile && ent1 instanceof AlienShipSpecial)) {
                        if(ent1 instanceof Missile) {
                            missileImpact(ent2, ent1, entities);
                        } else {
                            missileImpact(ent1, ent2, entities);
                        }
                    }
                        //SPACESHIP   <====================================>
                        //Spaceship <==> score points
                    else if(ent1 instanceof PlayerSpaceShip && ent2 instanceof ScorePoint) {
                        entities.remove(j);
                        RocketKrieg.inscreaseScore(1);
                        ZoneManager.addScorePointsCollected(1);
                    } else if(ent2 instanceof PlayerSpaceShip && ent1 instanceof ScorePoint) {
                        entities.remove(i);
                        RocketKrieg.inscreaseScore(1);
                        ZoneManager.addScorePointsCollected(1);
                        //MISC
                        //Scorepoint <==> other entities
                    } else if(ent1 instanceof  ScorePoint || ent2 instanceof ScorePoint) {
                        //do nothing, entities do not collide with points.
                    } else if(ent1 instanceof PlayerSpaceShip && ent2 instanceof Laser) {
                        PlayerSpaceShip ship = (PlayerSpaceShip) ent1;
                        ship.hit(5, false);
                        entities.remove(j);
                        collisionEvent(ent1,ent2, entities, PROJECTILEEXPLOSION);
                    } else if(ent2 instanceof PlayerSpaceShip && ent1 instanceof Laser) {
                        PlayerSpaceShip ship = (PlayerSpaceShip) ent2;
                        ship.hit(5, false);
                        entities.remove(i);
                        collisionEvent(ent1,ent2, entities, PROJECTILEEXPLOSION);
                        //Spaceship <==> planet
                    } else if(ent1 instanceof Planet && ent2 instanceof PlayerSpaceShip) {
                        PlayerSpaceShip ship = (PlayerSpaceShip) ent2;
                        ship.hit(1,true);
                        entities.remove(j);
                        entities.add(new CollisionEvent(ent2.getPosition().x-LARGEEXPLOSION/2, ent2.getPosition().y-LARGEEXPLOSION/2,LARGEEXPLOSION));
                    } else if(ent1 instanceof PlayerSpaceShip && ent2 instanceof Planet) {
                        PlayerSpaceShip ship = (PlayerSpaceShip) ent1;
                        ship.hit(1,true);
                        entities.remove(i);
                        entities.add(new CollisionEvent(ent1.getPosition().x-LARGEEXPLOSION/2, ent1.getPosition().y-LARGEEXPLOSION/2,LARGEEXPLOSION));
                        //Planet <==> Other entity
                    } else if((ent1 instanceof Planet && !(ent2 instanceof PlayerSpaceShip)) || (ent2 instanceof Planet && !(ent1 instanceof PlayerSpaceShip))) {
                        //do not explode planets.
                        if(ent1 instanceof Planet) {
                            entities.remove(j);
                            entities.add(new CollisionEvent(ent2.getPosition().x-LARGEEXPLOSION/2,ent2.getPosition().y-LARGEEXPLOSION/2,LARGEEXPLOSION));
                        } else {
                            entities.remove(i);
                            entities.add(new CollisionEvent(ent1.getPosition().x-LARGEEXPLOSION/2,ent1.getPosition().y-LARGEEXPLOSION/2,LARGEEXPLOSION));
                        }
                    } else if(ent1 instanceof PlayerSpaceShip || ent2 instanceof PlayerSpaceShip) {
                        if(ent1 instanceof  PlayerSpaceShip) {
                            PlayerSpaceShip ship = (PlayerSpaceShip)ent1;
                            ship.hit(1,false);
                            entities.remove(j);
                        } else {
                            PlayerSpaceShip ship = (PlayerSpaceShip)ent2;
                            ship.hit(1,false);
                            entities.remove(i);
                        }
                        collisionEvent(ent1,ent2, entities, LARGEEXPLOSION);
                    } else {
                        //all other collisions.
                        entities.remove(j);
                        entities.remove(i);
                        collisionEvent(ent1,ent2, entities, LARGEEXPLOSION);
                    }
                }
            }
        }
    }

    /**
     * When a collision occurs, draw explosion.
     * @param ent1 Entity 1 in collision event.
     * @param ent2 Entity 2 in collision event.
     */
    private void collisionEvent(Entity ent1, Entity ent2, ArrayList<Entity> entities, float size) {
        Vector2 pos1 = ent1.getPosition();
        Vector2 pos2 = ent2.getPosition();
        float x = (pos1.x + pos2.x) / 2;
        float y = (pos1.y + pos2.y) / 2;
        entities.add(new CollisionEvent(x-size/2,y-size/2, size));
    }

    /**
     *
     * @param ent1
     * @param ent2
     * @param entities
     */
    private void missileImpact(Entity ent1, Entity ent2, ArrayList<Entity> entities) {
        if(ent1.hit(ship.getWeaponComponent().getDamage())) {
            RocketKrieg.inscreaseScore(1);
            ZoneManager.addAlienKills(1);
            collisionEvent(ent1, ent2, entities, LARGEEXPLOSION);
            entities.remove(ent1);
            entities.remove(ent2);
        } else {
            collisionEvent(ent1,ent2,entities, LARGEEXPLOSION);
            entities.remove(ent2);
        }
    }
}
