package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.objects.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;

public class RocketKrieg implements Screen {
	private final GameEntry game;
	private OrthographicCamera camera;
	private final float FPS = 60f;
	private PlayerSpaceShip ship;
	private ChunkManager cm;
	private AssetStorage ass; //:-)

	public RocketKrieg(final GameEntry game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		ship = new PlayerSpaceShip();
		cm = new ChunkManager(ship);
		ass = new AssetStorage();
	}

	/**
	 * Called when this screen becomes current screen.
	 */
	public void show() {}

	/**
	 * Called every frame.
	 * @param delta time since last frame.
	 */
	public void render(float delta) {
		//clear screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//camera
		GameEntry.batch.setProjectionMatrix(camera.combined);
		camera.update();
		float lerp = 2f;
		Vector3 cameraPosition = camera.position;
		Vector2 shipPosition = ship.getPosition();
		cameraPosition.x += (shipPosition.x - cameraPosition.x) * lerp * delta;
		cameraPosition.y += (shipPosition.y - cameraPosition.y) * lerp * delta;

		//update ship
		ship.update(1f/FPS);
		//render all entities and tiles
		GameEntry.batch.begin();
		cm.render();
		GameEntry.batch.end();
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
	public void resize(int width, int height) {}

	/**
	 * Actions performed when game is paused (good place to save the game).
	 */
	public void pause() {}

	/**
	 * Only called on android.
	 */
	public void resume() {}
	public void hide() {}
	public void dispose () {
		GameEntry.batch.dispose();
	}
}
