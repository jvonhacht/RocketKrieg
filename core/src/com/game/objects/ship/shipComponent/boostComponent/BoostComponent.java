package com.game.objects.ship.shipComponent.boostComponent;

/**
 * Created by JohanvonHacht on 2017-08-04.
 */
public class BoostComponent {
    protected String name;
    protected float stats;
    protected float maxCharge;
    protected float timer;

    public BoostComponent() {
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
    public float getCharge() {
        return stats;
    }

    /**
     * Use boost
     * @param delta
     */
    public void boost(float delta) {
        stats -= delta;
    }

    /**
     * Recharge boost charge over time.
     * @param delta
     */
    public void reChargeBoost(float delta) {
        if(stats < maxCharge) {
            stats += delta/8;
        }
    }

    /**
     * Recharge boost.
     */
    public void recharge() {
        stats = maxCharge;
    }
}
