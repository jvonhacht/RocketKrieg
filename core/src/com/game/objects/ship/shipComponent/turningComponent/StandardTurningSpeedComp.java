package com.game.objects.ship.shipComponent.turningComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public class StandardTurningSpeedComp extends ShipComponent implements Component {
    public StandardTurningSpeedComp() {
        //reload time
        stats = 250f;
        name = "Beginner turning speed upgrade";
    }
}
