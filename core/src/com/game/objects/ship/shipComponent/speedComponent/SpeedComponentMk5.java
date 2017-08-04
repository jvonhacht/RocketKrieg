package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SpeedComponentMk5 extends ShipComponent {
    public SpeedComponentMk5() {
        stats = 5000f;
        unChangedStats = stats;
        name = "Mk5 speed upgrade";
    }
}
