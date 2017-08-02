package com.game.worldGeneration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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
    private CollisionManager colHandler;
    private OrthographicCamera camera;

    /**
     * Constructor for ChunkManager.
     * @param ship PlayerSpaceShip
     */
    public ChunkManager(PlayerSpaceShip ship, OrthographicCamera camera) {
        batch = GameEntry.batch;
        this.camera = camera;
        chunks = new HashMap<Pair,Chunk>();
        hashGrid = new HashMap<Pair, ArrayList<Entity>>();
        colHandler = new CollisionManager();
        addEntity(ship);
    }

    /**
     * Method to render and update everything in the world.
     */
    public void render(boolean update, float delta) {
        Vector3 camPosition = camera.position;
        Vector2 position = new Vector2(camPosition.x,camPosition.y);

        //Up side
        Vector2 downSide = new Vector2();
        downSide.x = position.x;
        downSide.y = position.y + Gdx.graphics.getWidth()/2;

        //Down side
        Vector2 upSide = new Vector2();
        upSide.x = position.x;
        upSide.y = position.y - Gdx.graphics.getWidth()/2;

        //Right side
        Vector2 rightSide = new Vector2();
        rightSide.x = position.x + Gdx.graphics.getWidth()/2;
        rightSide.y = position.y;

        //Right corner up
        Vector2 rightSideUp = new Vector2();
        rightSideUp.x = position.x + Gdx.graphics.getWidth()/2;
        rightSideUp.y = position.y + Gdx.graphics.getHeight()/2;

        //Right corner down
        Vector2 rightSideDown = new Vector2();
        rightSideDown.x = position.x + Gdx.graphics.getWidth()/2;
        rightSideDown.y = position.y - Gdx.graphics.getHeight()/2;

        //Left side
        Vector2 leftSide = new Vector2();
        leftSide.x = position.x - Gdx.graphics.getWidth()/2;
        leftSide.y = position.y;

        //Left corner up
        Vector2 leftSideUp = new Vector2();
        leftSideUp.x = position.x - Gdx.graphics.getWidth()/2;
        leftSideUp.y = position.y + Gdx.graphics.getHeight()/2;

        //Left corner down
        Vector2 leftSideDown = new Vector2();
        leftSideDown.x = position.x - Gdx.graphics.getWidth()/2;
        leftSideDown.y = position.y - Gdx.graphics.getHeight()/2;

        int chunkSize = Chunk.WIDTH*Tile.TILE_SIZE;

        //anchor points is where chunk starts e.g. 0.0,
        Vector2 anchor = getAnchor(position, chunkSize);
        Vector2 anchor1 = getAnchor(upSide, chunkSize);
        Vector2 anchor2 = getAnchor(downSide, chunkSize);
        Vector2 anchor3 = getAnchor(rightSide, chunkSize);
        Vector2 anchor4 = getAnchor(rightSideUp, chunkSize);
        Vector2 anchor5 = getAnchor(rightSideDown, chunkSize);
        Vector2 anchor6 = getAnchor(leftSide, chunkSize);
        Vector2 anchor7 = getAnchor(leftSideUp, chunkSize);
        Vector2 anchor8 = getAnchor(leftSideDown, chunkSize);

        //add nearby neighbour chunks to render que.
        Set<Pair> anchors = new HashSet<Pair>();
        anchors.add(new Pair(anchor.x,anchor.y));
        anchors.add(new Pair(anchor1.x,anchor1.y));
        anchors.add(new Pair(anchor2.x,anchor2.y));
        anchors.add(new Pair(anchor3.x,anchor3.y));
        anchors.add(new Pair(anchor4.x,anchor4.y));
        anchors.add(new Pair(anchor5.x,anchor5.y));
        anchors.add(new Pair(anchor6.x,anchor6.y));
        anchors.add(new Pair(anchor7.x,anchor7.y));
        anchors.add(new Pair(anchor8.x,anchor8.y));

        //create a render que that renders at the end of the method.
        ArrayList<Entity> entitiesToRender = new ArrayList<Entity>();

        //add all chunks to hashmap and render.
        Iterator iterator = anchors.iterator();
        while(iterator.hasNext()) {
            Pair chunkPair = (Pair)iterator.next();
            if(chunks.containsKey(chunkPair)) {
                Tile[][] tiles = chunks.get(chunkPair).getTiles();
                //render background tiles in chunk.
                for (int k=0; k<tiles.length; k++) {
                    for (int l = 0; l<tiles.length; l++) {
                        Tile tile = tiles[k][l];
                        float tileX = tile.getX();
                        float tileY = tile.getY();
                        if(!update) {
                            batch.draw(tile.getImg(),tileX,tileY);
                        }

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
                Chunk chunk = new Chunk(chunkPair.getX(),chunkPair.getY());
                chunks.put(chunkPair,chunk);
            }
        }
        if(update) {
            //render and update the entity que.
            for (Entity ent:entitiesToRender) {
                ent.update(delta);
            }
        } else {
            //render and update the entity que.
            for (Entity ent:entitiesToRender) {
                if(!(ent instanceof PlayerSpaceShip)) {
                    ent.render(batch);
                }
            }
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

