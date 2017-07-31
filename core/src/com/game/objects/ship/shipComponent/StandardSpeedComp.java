package com.game.objects.ship.shipComponent;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public class StandardSpeedComp extends ShipComponent implements Component {
    public StandardSpeedComp() {
        //reload time
        stats[1] = 2f;
        name = "Beginner speed upgrade";
    }
}
