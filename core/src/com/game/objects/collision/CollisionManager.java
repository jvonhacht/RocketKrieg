package com.game.objects.collision;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.GameEntry;
import com.game.objects.Entity;
import com.game.objects.Missile;
import com.game.objects.PlayerSpaceShip;

import java.util.ArrayList;

/**
 * Created by Johan on 06/05/2017.
 */
public class CollisionManager {
    /**
     * Check if entities in an arraylist collides.
     * @param entities Entities to test for collision.
     */
    public void collides(ArrayList<Entity> entities) {
        //check all
        for (int i=0; i<entities.size()-1; i++){
            for (int j=i+1; j<entities.size(); j++){
                Entity ent1 = entities.get(i);
                Entity ent2 = entities.get(j);
                if(Intersector.overlapConvexPolygons(ent1.getHitBox(),ent2.getHitBox())) {
                    //missile should not blow up ship.
                    if(!((ent1 instanceof PlayerSpaceShip && ent2 instanceof Missile) || (ent1 instanceof Missile && ent2 instanceof PlayerSpaceShip) || (ent1 instanceof Missile && ent2 instanceof Missile))) {
                        collisionEvent(ent1,ent2, entities);
                        entities.remove(j);
                        entities.remove(i);
                    }
                }
            }
        }
    }

    /**
     * When a collision occurs do ... draw explosion.
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
