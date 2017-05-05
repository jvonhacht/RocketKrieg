package com.game.worldGeneration;

/**
 * Created by Johan on 04/05/2017.
 */
public class Pair {
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