package com.game.objects.ship.shipComponent;

import com.badlogic.gdx.math.Vector2;
import com.game.worldGeneration.ChunkManager;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public class DoubleMissileComp extends ShipComponent implements Component {
    public DoubleMissileComp() {
        //reload time
        stats[0] = 0.5f;
        name = "Double missile upgrade";
    }

    /**
     * Fire two missiles with 10 degree difference
     * @param position
     * @param velocity
     * @param acceleration
     * @param angle
     * @param angularVelocity
     * @param delta
     */
    public void fireMissile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle, float angularVelocity, float delta) {
        Missile missile = new Missile(position,velocity,acceleration,angle - (float)Math.toRadians(10),angularVelocity, delta);
        Missile missile1 = new Missile(position,velocity,acceleration,angle + (float)Math.toRadians(10),angularVelocity, delta);
        ChunkManager.addEntity(missile);
        ChunkManager.addEntity(missile1);
    }
}
