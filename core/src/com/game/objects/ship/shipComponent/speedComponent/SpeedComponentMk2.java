package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SpeedComponentMk2 extends ShipComponent implements Component {
    public SpeedComponentMk2() {
        stats = 3500f;
        name = "Mk2 speed upgrade";
    }
}
