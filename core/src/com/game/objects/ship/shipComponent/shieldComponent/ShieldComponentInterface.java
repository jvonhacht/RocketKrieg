package com.game.objects.ship.shipComponent.shieldComponent;

/**
 * Created by JohanvonHacht on 2017-08-02.
 */
public interface ShieldComponentInterface {
    /**
     * Reduce shield charges.
     */
    void reduceCharge();

    /**
     * Get shield charges.
     * @return stats
     */
    int getCharges();

    /**
     * Set shield charge.
     */
    void setShieldCharge(int amount);
}
