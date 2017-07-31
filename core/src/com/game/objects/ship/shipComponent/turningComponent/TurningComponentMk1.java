package com.game.objects.ship.shipComponent.turningComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class TurningComponentMk1 extends ShipComponent implements Component {
    public TurningComponentMk1() {
        //reload time
        stats = 150f;
        name = "Mk1 turning speed upgrade";
    }
}
