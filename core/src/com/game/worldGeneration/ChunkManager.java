package com.game.worldGeneration;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.GameEntry;
import com.game.objects.*;

import java.util.*;

/**
 * Created by Johan on 26/04/2017.
 */
public class ChunkManager {
    private SpriteBatch batch;
    private Map<Pair,Chunk> chunks;
    static HashMap<Pair,ArrayList<Entity>> hashGrid;
    private Pair[] renderCloseAnchor;
    private CollisionHandler colHandler;
    private PlayerSpaceShip ship;

    public ChunkManager(PlayerSpaceShip ship) {
        batch = GameEntry.batch;
        this.ship = ship;
        chunks = new HashMap<Pair,Chunk>();
        renderCloseAnchor = new Pair[9];
        hashGrid = new HashMap<Pair, ArrayList<Entity>>();
        colHandler = new CollisionHandler();
        addEntity(ship);
    }

    /**
     * Method to render and update everything in the world.
     */
    public void render() {
        Vector2 position = ship.getPosition();
        int x = (int)position.x;
        int y = (int)position.y;
        int chunkSize = Chunk.WIDTH*Tile.TILE_SIZE;
        //anchor points is where chunk starts e.g. 0.0,
        int anchorX = (x - ( x<0 ? chunkSize-1 : 0 )) / chunkSize * chunkSize;
        int anchorY = (y - ( y<0 ? chunkSize-1 : 0 )) / chunkSize * chunkSize;
        //add nearby neighbour chunks to render que.
        renderCloseAnchor[0] = new Pair(anchorX,anchorY);
        renderCloseAnchor[1] = new Pair(anchorX+chunkSize,anchorY);
        renderCloseAnchor[2] = new Pair(anchorX,anchorY+chunkSize);
        renderCloseAnchor[3] = new Pair(anchorX-chunkSize,anchorY);
        renderCloseAnchor[4] = new Pair(anchorX,anchorY-chunkSize);
        renderCloseAnchor[5] = new Pair(anchorX+chunkSize,anchorY+chunkSize);
        renderCloseAnchor[6] = new Pair(anchorX-chunkSize,anchorY-chunkSize);
        renderCloseAnchor[7] = new Pair(anchorX+chunkSize,anchorY-chunkSize);
        renderCloseAnchor[8] = new Pair(anchorX-chunkSize,anchorY+chunkSize);
        //create a render que that renders at the end of the method.
        ArrayList<Entity> entitiesToRender = new ArrayList<Entity>();

        //add all chunks to hashmap and render.
        for (int i=0; i<renderCloseAnchor.length; i++) {
            Pair Chunkpair = renderCloseAnchor[i];
            if(chunks.containsKey(Chunkpair)) {
                Tile[][] tiles = chunks.get(Chunkpair).getTiles();
                //render background tiles in chunk.
                for (int k=0; k<tiles.length; k++) {
                    for (int l = 0; l<tiles.length; l++) {
                        Tile tile = tiles[k][l];
                        float tileX = tile.getX();
                        float tileY = tile.getY();
                        batch.draw(tile.getImg(),tileX,tileY);

                        //try collect entities in tile.
                        try {
                            //rehash, add to render que and do collisions for all entities.
                            Pair objectPair = new Pair(tileX, tileY);
                            ArrayList<Entity> entities = hashGrid.get(objectPair);
                            hashGrid.remove(objectPair);
                            colHandler.collides(entities);
                            for (Entity ent:entities) {
                                entitiesToRender.add(ent);
                                addEntity(ent);
                            }
                        } catch (NullPointerException e){}
                    }
                }
            } else {
                //no chunks, make new ones.
                Chunk chunk = new Chunk(Chunkpair.getX(),Chunkpair.getY());
                chunks.put(Chunkpair,chunk);
            }
        }
        //render and update the entity que.
        for (Entity ent:entitiesToRender) {
            //update ship separately to reduce stutter.
            if(!ent.equals(ship)) {
                ent.update(1f/60f);
            }
            ent.render(batch);
        }
    }

    /**
     * Add an entity to the spatial hashgrid.
     * @param ent Entity to be added.
     */
    public void addEntity(Entity ent) {
        Vector2 position = ent.getPosition();
        int anchorX =(int) (position.x - ( position.x<0 ? 512-1 : 0 )) / 512 * 512;
        int anchorY =(int) (position.y - ( position.y<0 ? 512-1 : 0 )) / 512 * 512;
        Pair pair = new Pair(anchorX,anchorY);
        if(hashGrid.containsKey(pair)) {
            hashGrid.get(pair).add(ent);
        } else {
            hashGrid.put(pair, new ArrayList<Entity>());
            hashGrid.get(pair).add(ent);
        }
    }
}

