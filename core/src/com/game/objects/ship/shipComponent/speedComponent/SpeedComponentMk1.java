package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SpeedComponentMk1 extends ShipComponent {
    public SpeedComponentMk1() {
        stats = 3000f;
        unChangedStats = stats;
        name = "Mk1 speed upgrade";
    }
}
