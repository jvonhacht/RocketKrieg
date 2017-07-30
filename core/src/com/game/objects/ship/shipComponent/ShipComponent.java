package com.game.objects.ship.shipComponent;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public class ShipComponent {
    protected String name;
    protected float[] stats;

    public ShipComponent() {
        name = "";
        /**
         * index 0 - reload time.
         * index 1 - speed multiplier.
         * index 2 - turning speed.
         */
        stats = new float[3];
    }

    /**
     * Get component name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Get component stats
     * @return stats
     */
    public float[] getStats() {
        return stats;
    }

    public void fireMissile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle, float angularVelocity, float delta) {
        //do nothing.
    }
}
