package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class SpeedComponentMk5 extends ShipComponent implements Component {
    public SpeedComponentMk5() {
        stats = 4000f;
        name = "Mk5 speed upgrade";
    }
}
