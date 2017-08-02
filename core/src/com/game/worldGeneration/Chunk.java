package com.game.worldGeneration;

import com.badlogic.gdx.math.Vector2;
import com.game.objects.Entity;
import com.game.objects.GameEntity;

import java.util.ArrayList;

/**
 *  Chunk class.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-04-27)
 */
public class Chunk {
    private final int SAFEZONE = 1024;
    static final int WIDTH = 3;
    static final int HEIGHT = 3;
    private int x;
    private int y;
    private Tile[][] tiles;

    public Chunk(int x, int y) {
        tiles = new Tile[WIDTH][HEIGHT];
        this.x = x;
        this.y = y;
        generateChunk();

    }

    /**
     * Get tiles in chunk.
     * @return Tile[][] tiles.
     */
    public Tile[][] getTiles() {
        return tiles;
    }

    /**
     * Generate a new chunk and add tile entities to spatial hash.
     */
    public void generateChunk() {
        for (int i=0; i<tiles.length; i++) {
            for(int j=0; j<tiles.length; j++) {
                Tile tile = new Tile(x+i*Tile.TILE_SIZE,y+j*Tile.TILE_SIZE);
                tiles[i][j] = tile;
                ArrayList tileEntities = tile.getEntity();
                for (int k=0; k<tileEntities.size() ; k++) {
                    Entity ent = (Entity)tileEntities.get(k);
                    Vector2 position = ent.getPosition();
                    Vector2 anchor = ChunkManager.getAnchor(position, Tile.TILE_SIZE);
                    int anchorX = (int)anchor.x;
                    int anchorY = (int)anchor.y;
                    Pair pair = new Pair(anchorX,anchorY);
                    //don't spawn stuff where the player spawns.
                    if(!(Math.abs(anchorX)<SAFEZONE && Math.abs(anchorY)<SAFEZONE)) {
                        if(ChunkManager.hashGrid.containsKey(pair)) {
                            ChunkManager.hashGrid.get(pair).add(ent);
                        } else {
                            ChunkManager.hashGrid.put(pair, new ArrayList<Entity>());
                            ChunkManager.hashGrid.get(pair).add(ent);
                        }
                    }
                }
            }
        }
    }
}