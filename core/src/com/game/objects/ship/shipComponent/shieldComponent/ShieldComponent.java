package com.game.objects.ship.shipComponent.shieldComponent;

/**
 * Created by JohanvonHacht on 2017-08-02.
 */
public class ShieldComponent {
    protected String name;
    protected int stats;
    protected int unChangedStats;

    public ShieldComponent() {
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
    public int getCharges() {
        return stats;
    }

    /**
     * Reduce shield charge
     */
    public void reduceCharge() {
        if((stats)>0) {
            stats -= 1;
        } else {
            stats = 0;
        }
    }

    /**
     * Set charges on shield.
     * @param amount
     */
    public void setShieldCharge(int amount) {
        stats = amount;
    }

    /**
     * Reset shield charges to original state.
     */
    public void shieldReset() {
        stats = unChangedStats;
    }
}
