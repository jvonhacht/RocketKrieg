package com.game.objects;

import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;

/**
 * Created by Johan on 06/05/2017.
 */
public class CollisionHandler {
    public void collides(ArrayList<Entity> entities) {
        //check all
        for (int i=0; i<entities.size()-1; i++){
            for (int j=i+1; j<entities.size(); j++){
                Entity ent1 = entities.get(i);
                Entity ent2 = entities.get(j);
                if(Intersector.overlapConvexPolygons(ent1.getHitBox(),ent2.getHitBox())) {
                    ent1.collision();
                    ent2.collision();
                    entities.remove(j);
                    entities.remove(i);
                }
            }
        }
    }
}
