package com.game.objects.ship.shipComponent;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public class ShipComponent {
    protected String name;
    protected float stats;
    protected float unChangedStats;

    public ShipComponent() {
        name = "";
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
    public float getStats() {
        return stats;
    }

    /**
     * Set component stats.
     * @param stats
     */
    public void setStats(float stats) {
        this.stats = stats;
    }

    public float getUnchangedStats() {
        return unChangedStats;
    }
}
