package com.game.objects;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.game.GameEntry;

import java.util.ArrayList;

/**
 * Created by Johan on 06/05/2017.
 */
public class CollisionHandler {
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
                    collisionEvent(ent1,ent2);
                    entities.remove(j);
                    entities.remove(i);
                }
            }
        }
    }

    /**
     * When a collision occurs do ... draw explosion.
     * @param ent1 Entity 1 in collision event.
     * @param ent2 Entity 2 in collision event.
     */
    public void collisionEvent(Entity ent1, Entity ent2) {
        Vector2 pos1 = ent1.getPosition();
        Vector2 pos2 = ent2.getPosition();
        AssetStorage.explosion.setPosition(pos1.x,pos2.y);
        AssetStorage.explosion.setSize(100,100);
        AssetStorage.explosion.draw(GameEntry.batch);
    }
}
