package com.game.objects.ship.shipComponent;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public class StandardTurningSpeedComp extends ShipComponent implements Component {
    public StandardTurningSpeedComp() {
        //reload time
        stats[2] = 3f;
        name = "Beginner turning speed upgrade";
    }
}
