package com.game.worldGeneration;

import com.badlogic.gdx.math.Vector2;
import com.game.objects.Entity;

import java.util.ArrayList;

/**
 * Created by Johan on 27/04/2017.
 */
public class Chunk {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;
    private int x;
    private int y;

    protected Tile[][] tiles;

    public Chunk(int x, int y) {
        tiles = new Tile[WIDTH][HEIGHT];
        this.x = x;
        this.y = y;
        generateChunk();
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void generateChunk() {
        for (int i=0; i<tiles.length; i++) {
            for(int j=0; j<tiles.length; j++) {
                Tile tile = new Tile(x+i*Tile.TILE_SIZE,y+j*Tile.TILE_SIZE);
                tiles[i][j] = tile;
                Entity ent = tile.getEntity();

                Vector2 position = ent.getPosition();
                int anchorX =(int) (position.x - ( position.x<0 ? 512-1 : 0 )) / 512 * 512;
                int anchorY =(int) (position.y - ( position.y<0 ? 512-1 : 0 )) / 512 * 512;
                Pair pair = new Pair(anchorX,anchorY);
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