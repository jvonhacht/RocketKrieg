package com.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.game.GameEntry;

/**
 * DesktopLauncher class specifies
 * various configuration settings.
 * @author Johan von Hacht
 * @version 1.0 (2017-04-29)
 */
public class DesktopLauncher {

	/**
	 * Main method
	 */
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		config.vSyncEnabled = false;
		config.foregroundFPS = -1;
		config.useGL30 = false;
		config.width = LwjglApplicationConfiguration.getDesktopDisplayMode().width;
		config.height = LwjglApplicationConfiguration.getDesktopDisplayMode().height;
		config.title = "RocketKrieg";
		new LwjglApplication(new GameEntry(), config);
	}
}
