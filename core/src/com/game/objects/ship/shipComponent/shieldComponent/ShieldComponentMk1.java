package com.game.objects.ship.shipComponent.shieldComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class ShieldComponentMk1 extends ShieldComponent implements ShieldComponentInterface {
    public ShieldComponentMk1() {
        //reload time
        stats = 1;
        name = "Mk1 shield upgrade";
    }
}
