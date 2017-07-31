package com.game.objects.ship.shipComponent.turningComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class TurningComponentMk3 extends ShipComponent implements Component {
    public TurningComponentMk3() {
        //reload time
        stats = 200f;
        name = "Mk3 turning speed upgrade";
    }
}
