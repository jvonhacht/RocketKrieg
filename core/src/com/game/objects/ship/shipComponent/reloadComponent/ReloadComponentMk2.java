package com.game.objects.ship.shipComponent.reloadComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class ReloadComponentMk2 extends ShipComponent implements Component {
    public ReloadComponentMk2() {
        //reload time
        stats = 0.9f;
        name = "Mk2 reload upgrade";
    }
}
