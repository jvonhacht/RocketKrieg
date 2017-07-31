package com.game.objects.ship.shipComponent.weaponComponent;

import com.badlogic.gdx.math.Vector2;
import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.Missile;
import com.game.objects.ship.shipComponent.ShipComponent;
import com.game.worldGeneration.ChunkManager;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SingleMissileComp extends ShipComponent implements Component {
    public SingleMissileComp() {
        name = "Double missile upgrade";
    }

    /**
     * Fire two missiles with 10 degree difference
     * @param position
     * @param velocity
     * @param acceleration
     * @param angle
     * @param angularVelocity
     */
    public void fireMissile(Vector2 position, Vector2 velocity, Vector2 acceleration, float angle, float angularVelocity) {
        Missile missile = new Missile(position,velocity,acceleration,angle,angularVelocity);
        ChunkManager.addEntity(missile);
    }
}
