package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.objects.Asteroid;
import com.game.objects.EntityHandler;
import com.game.objects.PlayerSpaceShip;

public class RocketKrieg implements Screen {
	private final GameEntry game;
	private PlayerSpaceShip ship;
	private EntityHandler eh;
	private OrthographicCamera camera;

	public RocketKrieg(final GameEntry game) {
		this.game = game;
		ship = new PlayerSpaceShip();
		eh = new EntityHandler(game.batch);
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
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
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		//batch
		game.batch.begin();
		eh.render();
		game.batch.end();
		//input
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
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

	public void hide() {}

	public void dispose () {
		game.batch.dispose();
	}
}
