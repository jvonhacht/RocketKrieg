package com.game.worldGeneration;

/**
 *  Pair class that prevents tiles from
 *  being created at the same position.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-05-04)
 */
public class Pair {
    private final int x;
    private final int y;

    /**
     * Constructor for Pair.
     * @param x coordinate
     * @param y coordinate
     */
    public Pair(final float x, final float y) {
        this.x = (int)x;
        this.y = (int)y;
    }

    /**
     * Overrides equals method in object.
     * Indicates if another object is equal
     * to this one.
     * @param o Object
     * @return boolean value
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair))
            return false;
        Pair p = (Pair)o;
        return (p.x == this.x && p.y == this.y);
    }

    /**
     * Overrides hashCode method in object.
     * Returns hashCode value for the object.
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        return result;
    }

    /**
     * Overrides toString method in object.
     * Returns string representation of object.
     * @return
     */
    @Override
    public String toString() {
        return (x + ":" + y);
    }

    /**
     * Get x position.
     * @return x coordinate
     */
    public int getX() {return x;}


    /**
     * Get y position.
     * @return y coordinate
     */
    public int getY() {return y;}
}