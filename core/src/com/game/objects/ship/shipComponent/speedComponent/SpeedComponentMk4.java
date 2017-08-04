package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SpeedComponentMk4 extends ShipComponent {
    public SpeedComponentMk4() {
        stats = 4500f;
        unChangedStats = stats;
        name = "Mk4 speed upgrade";
    }
}
