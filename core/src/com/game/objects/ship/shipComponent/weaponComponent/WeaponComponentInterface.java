package com.game.objects.ship.shipComponent.weaponComponent;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by JohanvonHacht on 2017-08-02.
 */
public interface WeaponComponentInterface {
    /**
     * Reduce shield charges.
     */
    void fireMissile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle, float angularVelocity);
}
