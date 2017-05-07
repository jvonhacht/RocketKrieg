package com.game.worldGeneration;

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
                tiles[i][j] = new Tile(x+i*Tile.TILE_SIZE,y+j*Tile.TILE_SIZE);
            }
        }
    }
}