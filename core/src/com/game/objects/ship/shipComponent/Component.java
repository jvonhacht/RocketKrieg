package com.game.objects.ship.shipComponent;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by JohanvonHacht on 2017-07-30.
 */
public interface Component {
    /**
     * Get component name
     * @return name
     */
    String getName();

    /**
     * Get component stats
     * @return stats
     */
    float getStats();
}
