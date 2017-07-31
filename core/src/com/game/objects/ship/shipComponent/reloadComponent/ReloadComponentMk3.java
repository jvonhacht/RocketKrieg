package com.game.objects.ship.shipComponent.reloadComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class ReloadComponentMk3 extends ShipComponent implements Component {
    public ReloadComponentMk3() {
        //reload time
        stats = 0.8f;
        name = "Mk3 reload upgrade";
    }
}
