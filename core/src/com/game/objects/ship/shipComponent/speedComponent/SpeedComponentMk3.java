package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SpeedComponentMk3 extends ShipComponent {
    public SpeedComponentMk3() {
        stats = 4000f;
        unChangedStats = stats;
        name = "Mk3 speed upgrade";
    }
}
