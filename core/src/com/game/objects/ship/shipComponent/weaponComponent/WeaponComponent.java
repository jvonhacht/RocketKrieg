package com.game.objects.ship.shipComponent.weaponComponent;

import com.badlogic.gdx.math.Vector2;
import com.game.objects.ship.shipComponent.Missile;
import com.game.worldGeneration.ChunkManager;

/**
 * Created by JohanvonHacht on 2017-08-02.
 */
public class WeaponComponent {
    protected String name;
    protected int stats;
    protected double damage;
    protected double reloadTime;

    public WeaponComponent() {
        name = "";
    }

    /**
     * Get component name
     * @return name
     */
    public String getName() {
        return name;
    }

    public void fireMissile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle, float angularVelocity) {
        Missile missile = new Missile(position,velocity,acceleration,angle,angularVelocity);
        ChunkManager.addEntity(missile);
    }

    public double getDamage() {
        return damage;
    }

    public double getReloadTime() {
        return reloadTime;
    }
}
