package com.game.objects.ship.shipComponent.shieldComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class StandardShieldComp extends ShipComponent implements Component {
    public StandardShieldComp() {
        //reload time
        stats = 1;
        name = "Beginner shield upgrade";
    }
}
