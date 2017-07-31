package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SpeedComponentMk1 extends ShipComponent implements Component {
    public SpeedComponentMk1() {
        stats = 2000f;
        name = "Mk1 speed upgrade";
    }
}
