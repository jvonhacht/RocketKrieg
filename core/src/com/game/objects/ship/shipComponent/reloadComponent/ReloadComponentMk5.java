package com.game.objects.ship.shipComponent.reloadComponent;

import com.game.objects.ship.shipComponent.Component;
import com.game.objects.ship.shipComponent.ShipComponent;

/**
 * Created by JohanvonHacht on 2017-07-31.
 */
public class ReloadComponentMk5 extends ShipComponent implements Component {
    public ReloadComponentMk5() {
        //reload time
        stats = 0.5f;
        name = "Mk5 reload upgrade";
    }
}
