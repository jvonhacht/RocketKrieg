package com.game.worldGeneration;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.objects.EntityHandler;
import com.game.objects.PlayerSpaceShip;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Johan on 26/04/2017.
 */
public class ChunkManager {
    private PlayerSpaceShip pship;
    private SpriteBatch batch;
    private Map<Pair,Chunk> chunks;

    public ChunkManager(SpriteBatch batch) {
        this.batch = batch;
        pship = EntityHandler.ship;
        chunks = new HashMap<Pair,Chunk>();
    }

    public void render() {
        Vector2 position = pship.getPosition();
        int x = (int)position.x;
        int y = (int)position.y;
        int chunkSize = Chunk.WIDTH*Tile.TILE_SIZE;
        //anchor points is where chunk starts e.g. 0.0,
        int anchorX = (x - ( x<0 ? chunkSize-1 : 0 )) / chunkSize * chunkSize;
        int anchorY = (y - ( y<0 ? chunkSize-1 : 0 )) / chunkSize * chunkSize;
        //add nearby neighbour chunks to render que
        Pair[] renderCloseAnchor = new Pair[9];
        renderCloseAnchor[0] = new Pair(anchorX,anchorY);
        renderCloseAnchor[1] = new Pair(anchorX+chunkSize,anchorY);
        renderCloseAnchor[2] = new Pair(anchorX,anchorY+chunkSize);
        renderCloseAnchor[3] = new Pair(anchorX-chunkSize,anchorY);
        renderCloseAnchor[4] = new Pair(anchorX,anchorY-chunkSize);
        renderCloseAnchor[5] = new Pair(anchorX+chunkSize,anchorY+chunkSize);
        renderCloseAnchor[6] = new Pair(anchorX-chunkSize,anchorY-chunkSize);
        renderCloseAnchor[7] = new Pair(anchorX+chunkSize,anchorY-chunkSize);
        renderCloseAnchor[8] = new Pair(anchorX-chunkSize,anchorY+chunkSize);

        //add all chunks to hashmap and render.
        for (int i=0; i<renderCloseAnchor.length; i++) {
            Pair pair = renderCloseAnchor[i];
            if(chunks.containsKey(pair)) {
                Tile[][] tiles = chunks.get(pair).getTiles();
                //render tiles in chunk
                for (int k=0; k<tiles.length; k++) {
                    for (int l = 0; l<tiles.length; l++) {
                        Tile tile = tiles[k][l];
                        batch.draw(tile.getImg(),tile.getX(),tile.getY());
                    }
                }
            } else {
                chunks.put(pair,new Chunk(pair.getX(),pair.getY()));
            }
        }
    }
}

class Pair {
    private final int x;
    private final int y;

    public Pair(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair))
            return false;
        Pair p = (Pair)o;
        return (p.x == this.x && p.y == this.y);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return ("Point[" + x + ":" + y + "]");
    }

    public int getX() {return x;}
    public int getY() {return y;}
}