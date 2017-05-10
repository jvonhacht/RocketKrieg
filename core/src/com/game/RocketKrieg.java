package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.objects.GameEntity;
import com.game.objects.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;

public class RocketKrieg implements Screen {
	private final GameEntry game;
	private OrthographicCamera camera;
	private final float FPS = 60f;
	private static PlayerSpaceShip ship;
	private ChunkManager cm;
	private AssetStorage ass; //:-)
	private static int score;
	private static float timeElapsed;

	public RocketKrieg(final GameEntry game) {
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		ass = new AssetStorage();
		ship = new PlayerSpaceShip();
		cm = new ChunkManager(ship);
		score = 0;
		timeElapsed = 5;
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
		Vector3 cameraPosition = camera.position;
		Vector2 shipPosition = ship.getPosition();
		Vector2 shipVelocity = ship.getVelocity();
		float lerp = 2f;
		cameraPosition.x += (shipPosition.x - cameraPosition.x) * lerp * delta + shipVelocity.x*delta/lerp;
		cameraPosition.y += (shipPosition.y - cameraPosition.y) * lerp * delta + shipVelocity.y*delta/lerp;

		//update ship
		ship.update(1f/FPS);
		//render all entities and tiles
		GameEntry.batch.begin();
		cm.render();
		GameEntry.font.draw(GameEntry.batch,"Score: " + score,cameraPosition.x, cameraPosition.y + Gdx.graphics.getHeight()/2 -50);
		if(timeElapsed<2) {
			GameEntry.font.draw(GameEntry.batch,"+1",ship.getPosition().x,ship.getPosition().y);
		}
		GameEntry.batch.end();
		//input
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		timeElapsed += delta;
	}

	/**
	 * Returns the position vector of
	 * the spaceship
	 * @return position vector
	 */
	public static PlayerSpaceShip getShip(){
		return ship;
	}

	public static void inscreaseScore(int amount) {
		score += amount;
		timeElapsed = 0;
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
