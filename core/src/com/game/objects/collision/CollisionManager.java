package com.game.objects.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.game.RocketKrieg;
import com.game.objects.*;
import java.util.ArrayList;

/**
 * CollisionManager class handling the
 * collisions of entities.
 * @author Johan von Hacht
 * @version 1.0 (2017-05-08)
 * Created by Johan on 06/05/2017.
 */
public class CollisionManager {
    private boolean playerDead = false;

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
                    //Fruktansvärd if sats men funkar..
                    if(((ent1 instanceof PlayerSpaceShip && ent2 instanceof Missile) || (ent1 instanceof Missile && ent2 instanceof PlayerSpaceShip) || (ent1 instanceof Missile && ent2 instanceof Missile))) {
                        //missile should not blow up ship or each other, do nothing.
                    } else if(ent1 instanceof PlayerSpaceShip && !(ent2 instanceof ScorePoint) || (ent2 instanceof PlayerSpaceShip && !(ent1 instanceof ScorePoint))) {
                        entities.remove(j);
                        entities.remove(i);
                        collisionEvent(ent1,ent2, entities);
                        RocketKrieg.playerDead();
                    } else if(ent1 instanceof Planet) {
                        //do not explode planets.
                        entities.remove(j);
                        entities.add(new CollisionEvent(ent2.getPosition().x,ent2.getPosition().y));
                    } else if(ent2 instanceof Planet) {
                        //do not explode planets.
                        entities.remove(i);
                        entities.add(new CollisionEvent(ent1.getPosition().x,ent1.getPosition().y));
                    } else if (ent1 instanceof PlayerSpaceShip && ent2 instanceof ScorePoint) {
                        //if player collides with scorepoint, give point do no collision.
                        RocketKrieg.inscreaseScore(1);
                        entities.remove(j);
                    } else if (ent1 instanceof ScorePoint && ent2 instanceof PlayerSpaceShip) {
                        //if player collides with scorepoint, give point do no collision.
                        RocketKrieg.inscreaseScore(1);
                        entities.remove(i);
                    } else if(ent1 instanceof  ScorePoint || ent2 instanceof ScorePoint) {
                        //do nothing, entities do not collide with points.
                    } else if ((ent1 instanceof  Missile && (ent2 instanceof AlienShip || ent2 instanceof AlienShipSpecial)) || (ent2 instanceof Missile && (ent1 instanceof AlienShip || ent1 instanceof AlienShipSpecial))) {
                        //give point and do collision if player missile hits alien.
                        entities.remove(j);
                        entities.remove(i);
                        RocketKrieg.inscreaseScore(1);
                        collisionEvent(ent1, ent2, entities);
                    } else if((ent1 instanceof  Laser && (ent2 instanceof AlienShip || ent2 instanceof AlienShipSpecial)) || (ent2 instanceof Laser && (ent1 instanceof AlienShip || ent1 instanceof AlienShipSpecial))) {
                        //do not let alien laser blow up alien.
                    } else {
                        //all other collisions.
                        entities.remove(j);
                        entities.remove(i);
                        collisionEvent(ent1,ent2, entities);
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
    public void collisionEvent(Entity ent1, Entity ent2, ArrayList<Entity> entities) {
        Vector2 pos1 = ent1.getPosition();
        Vector2 pos2 = ent2.getPosition();
        float x = (pos1.x + pos2.x) / 2;
        float y = (pos1.y + pos2.y) / 2;
        //magic number 50 to offset explosion into the correct pos
        entities.add(new CollisionEvent(x-50,y-50));
    }
}
