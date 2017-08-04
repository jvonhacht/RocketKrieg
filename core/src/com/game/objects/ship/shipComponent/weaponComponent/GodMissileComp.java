package com.game.objects.ship.shipComponent.weaponComponent;

import com.badlogic.gdx.math.Vector2;
import com.game.objects.ship.shipComponent.Missile;
import com.game.worldGeneration.ChunkManager;

/**
 * Created by JohanvonHacht on 2017-08-01.
 */
public class GodMissileComp extends WeaponComponent {
    public GodMissileComp() {
        name = "God missile upgrade";
    }

    /**
     * Temporary missile buff.
     * @param position
     * @param velocity
     * @param acceleration
     * @param angle
     * @param angularVelocity
     */
    public void fireMissile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle, float angularVelocity) {
        Missile missile = new Missile(position,velocity,acceleration,angle,angularVelocity);
        Missile missile1 = new Missile(position,velocity,acceleration,angle + (float)Math.toRadians(20),angularVelocity);
        Missile missile2 = new Missile(position,velocity,acceleration,angle + (float)Math.toRadians(-20),angularVelocity);
        Missile missile3 = new Missile(position,velocity,acceleration,angle + (float)Math.toRadians(40),angularVelocity);
        Missile missile4 = new Missile(position,velocity,acceleration,angle + (float)Math.toRadians(-40),angularVelocity);
        Missile missile5 = new Missile(position,velocity,acceleration,angle + (float)Math.toRadians(60),angularVelocity);
        Missile missile6 = new Missile(position,velocity,acceleration,angle + (float)Math.toRadians(-60),angularVelocity);
        ChunkManager.addEntity(missile);
        ChunkManager.addEntity(missile1);
        ChunkManager.addEntity(missile2);
        ChunkManager.addEntity(missile3);
        ChunkManager.addEntity(missile4);
        ChunkManager.addEntity(missile5);
        ChunkManager.addEntity(missile6);
    }
}
