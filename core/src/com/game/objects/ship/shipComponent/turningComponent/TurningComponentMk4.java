package com.game.objects.ship.shipComponent.turningComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class TurningComponentMk4 extends ShipComponent implements Component {
    public TurningComponentMk4() {
        //reload time
        stats = 225f;
        name = "Mk4 turning speed upgrade";
    }
}
