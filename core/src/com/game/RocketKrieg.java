package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.game.objects.Asteroid;
import com.game.objects.PlayerSpaceShip;

public class RocketKrieg implements Screen {
	private final GameEntry game;
	private PlayerSpaceShip ship;
	private Asteroid asteroid1;
	private Asteroid asteroid2;
	private Asteroid asteroid3;
	private Asteroid asteroid4;
	private OrthographicCamera camera;

	public RocketKrieg(final GameEntry game) {
		this.game = game;
		ship = new PlayerSpaceShip();
		asteroid1 = new Asteroid();
		asteroid2 = new Asteroid();
		asteroid3 = new Asteroid();
		asteroid4 = new Asteroid();
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
		ship.update(delta);
		asteroid1.update(delta);
		asteroid2.update(delta);
		asteroid3.update(delta);
		asteroid4.update(delta);
		game.batch.begin();
		ship.render(game.batch);
		asteroid1.render(game.batch);
		asteroid2.render(game.batch);
		asteroid3.render(game.batch);
		asteroid4.render(game.batch);
		game.batch.end();
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

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		game.batch.dispose();
	}
}
