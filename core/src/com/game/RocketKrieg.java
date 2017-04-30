package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.game.objects.PlayerSpaceShip;

public class RocketKrieg implements Screen {
	private final GameEntry game;
	private PlayerSpaceShip ship;

	public RocketKrieg(final GameEntry game) {
		this.game = game;
		ship = new PlayerSpaceShip();
	}

	/**
	 * Called when this screen becomes current screen.
	 */
	public void show() {

	}

	/**
	 * Called every frame.
	 * @param delta time since last frame.
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ship.update(delta);
		game.batch.begin();
		ship.render(game.batch);
		game.batch.end();
	}

	/**
	 * Method to resize screen, objects etc.
	 * @param width int
	 * @param height int
	 */
	public void resize(int width, int height) {

	}

	/**
	 * Actions performed when game is paused (good place to save the game).
	 */
	public void pause() {

	}

	/**
	 * Only called on android.
	 */
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		game.batch.dispose();
	}
}
