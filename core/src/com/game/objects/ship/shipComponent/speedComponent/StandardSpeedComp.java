package com.game.objects.ship.shipComponent.speedComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public class StandardSpeedComp extends ShipComponent implements Component {
    public StandardSpeedComp() {
        //reload time
        stats = 4000f;
        name = "Beginner speed upgrade";
    }
}
