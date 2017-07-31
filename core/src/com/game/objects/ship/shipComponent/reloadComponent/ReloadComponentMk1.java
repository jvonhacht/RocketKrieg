package com.game.objects.ship.shipComponent.reloadComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class ReloadComponentMk1 extends ShipComponent implements Component {
    public ReloadComponentMk1() {
        //reload time
        stats = 1f;
        name = "Mk1 reload upgrade";
    }
}
