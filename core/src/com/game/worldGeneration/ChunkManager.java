package com.game.worldGeneration;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.GameEntry;
import com.game.objects.*;
import com.game.objects.collision.CollisionManager;
import com.game.objects.ship.PlayerSpaceShip;

import java.util.*;

/**
 *  ChunkManager class handling the
 *  procedural generation of the world.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-04-26)
 */
public class ChunkManager {
    private SpriteBatch batch;
    private Map<Pair,Chunk> chunks;
    static HashMap<Pair,ArrayList<Entity>> hashGrid;
    private Pair[] renderCloseAnchor;
    private CollisionManager colHandler;
    private PlayerSpaceShip ship;

    /**
     * Constructor for ChunkManager.
     * @param ship PlayerSpaceShip
     */
    public ChunkManager(PlayerSpaceShip ship) {
        batch = GameEntry.batch;
        this.ship = ship;
        chunks = new HashMap<Pair,Chunk>();
        renderCloseAnchor = new Pair[9];
        hashGrid = new HashMap<Pair, ArrayList<Entity>>();
        colHandler = new CollisionManager();
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
        Vector2 anchor = getAnchor(position, chunkSize);
        int anchorX = (int)anchor.x;
        int anchorY = (int)anchor.y;
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
                ent.update();
            }
            ent.render(batch);
        }
    }

    /**
     * Add an entity to the spatial hashgrid.
     * @param ent Entity to be added.
     */
    public static void addEntity(Entity ent) {
        Vector2 position = ent.getPosition();
        Vector2 anchor = getAnchor(position, Tile.TILE_SIZE);
        int anchorX = (int)anchor.x;
        int anchorY = (int)anchor.y;
        Pair pair = new Pair(anchorX,anchorY);
        if(hashGrid.containsKey(pair)) {
            hashGrid.get(pair).add(ent);
        } else {
            hashGrid.put(pair, new ArrayList<Entity>());
            hashGrid.get(pair).add(ent);
        }
    }

    /**
     * Remove an entity to the spatial hashgrid.
     * @param ent Entity to be removed.
     */
    public static void removeEntity(Entity ent) {
        Vector2 position = ent.getPosition();
        Vector2 anchor = getAnchor(position, Tile.TILE_SIZE);
        int anchorX = (int)anchor.x;
        int anchorY = (int)anchor.y;
        Pair pair = new Pair(anchorX,anchorY);
        if(hashGrid.containsKey(pair)) {
            ArrayList<Entity> gridTile = hashGrid.get(pair);
            gridTile.remove(ent);
        }
    }

    /**
     * Method to get anchor point from a position and size.
     * @param position of object.
     * @param size distance between anchor points.
     * @return Vector2 anchor.
     */
    public static Vector2 getAnchor(Vector2 position, int size) {
        Vector2 anchor = new Vector2();
        int anchorX;
        int anchorY;
        if(position.x<0) {
            anchorX = size - 1;
        } else {
            anchorX = 0;
        }
        if(position.y<0) {
            anchorY = size - 1;
        } else {
            anchorY = 0;
        }
        anchor.set((int)(position.x - anchorX) / size * size,(int)(position.y - anchorY) / size * size);
        return anchor;
    }
}

