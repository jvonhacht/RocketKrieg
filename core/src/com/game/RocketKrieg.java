package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;

/**
 * Rocket League game mode
 * @author Johan von Hacht & David Johansson
 * @version 1.5 (2017-05-11)
 */
public class RocketKrieg implements Screen {
	private final GameEntry game;
	private OrthographicCamera camera;
	private static PlayerSpaceShip ship;
	private Sprite instructions;
	private Sprite gameOver;
	private Sprite singleSparkle;
	private ChunkManager cm;
	private AssetStorage ass; //:-)
	private static int score;
	private static float timeElapsed;
	private boolean startPhase;
	private static boolean playerState;

	private double time = 0.0;
	private double tick = 1/300f;
	private double accumulator = 0.0;
	private Vector2 prevPos;
	private Vector2 currentPos;

	/**
	 * Constructor for RocketKrieg screen.
	 * @param game GameEntry with SpriteBatch
	 */
	public RocketKrieg(final GameEntry game) {
		this.game = game;
		startPhase = true;
		playerState = false;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.position.x = 0;
		camera.position.y = 0;

		instructions = AssetStorage.instructions;
		gameOver = AssetStorage.gameOver;
		singleSparkle = AssetStorage.singleSparkle;
		ass = new AssetStorage();
		ship = new PlayerSpaceShip();
		cm = new ChunkManager(ship, camera);
		score = 0;
		timeElapsed = 10;
	}

	/**
	 * Render method for rendering the graphics.
	 * Also checks for player input
	 * @param delta time since last frame
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

		//fixed step update and rendering
		GameEntry.batch.begin();
		float frameTime = Gdx.graphics.getDeltaTime();
		if(frameTime>0.25) {
			frameTime = 0.25f;
		}
		accumulator += frameTime;
		while (accumulator >= tick) {
			prevPos = ship.getPosition();
			cm.render(true,(float)tick);
			accumulator -= tick;
			time += tick;
		}
		cm.render(false,1);
		//interpolate ship
		float alpha = (float)(accumulator/tick);
		currentPos = ship.getPosition();
		Vector2 lerpPosition = prevPos.interpolate(currentPos,alpha, Interpolation.linear);
		if(!playerState) {
			ship.renderr(GameEntry.batch, lerpPosition);
		}
		//draw score
		GameEntry.font.draw(GameEntry.batch, "Score: " + score,cameraPosition.x, cameraPosition.y + Gdx.graphics.getHeight()/2 -50);

		GameEntry.font.draw(GameEntry.batch,"Shield Charges: " + Integer.toString(ship.getShieldCharge()),cameraPosition.x, cameraPosition.y + Gdx.graphics.getHeight()/2 -75);

		//draw instructions
		if(startPhase && !playerState) {
			GameEntry.batch.draw(instructions, ship.getPosition().x, ship.getPosition().y - instructions.getHeight() / 2);
			GameEntry.batch.draw(singleSparkle, ship.getPosition().x + 331, ship.getPosition().y - 115, 25f, 25f);
			if(timeElapsed > 17){
				startPhase = false;
			}
		}

		//draw "+1"
		if(8 < timeElapsed  && timeElapsed < 10) {
			GameEntry.font.draw(GameEntry.batch,"+1",ship.getPosition().x,ship.getPosition().y);
		}

		//draw game over message
		if(playerState){
			GameEntry.batch.draw(gameOver, ship.getPosition().x - (gameOver.getWidth()/2), ship.getPosition().y - (gameOver.getHeight()/2) + 40);
		}

		GameEntry.batch.end();

		//input
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}

		//game over
		if(playerState){
			if(timeElapsed > 2){
				game.setScreen(new GameOver(game, score));
			}
		}
		timeElapsed += delta;
	}

	public static float getDelta() {
		return Gdx.graphics.getDeltaTime();
	}

	/**
	 * Returns the position vector of
	 * the spaceship
	 * @return position vector
	 */
	public static PlayerSpaceShip getShip(){
		return ship;
	}

	/**
	 * Increases the player's score
	 * @param amount to increase
	 */
	public static void inscreaseScore(int amount) {
		score += amount;
		timeElapsed = 8;
	}

	/**
	 * Get player score
	 * @return score
	 */
	public static int getScore() {
		return score;
	}

	/**
	 * Indicate that player is dead
	 */
	public static void playerDead(boolean planetCollision){
		if(ship.getShieldCharge()>0 && !planetCollision) {
			ship.reduceShieldCharge();
		} else {
			ChunkManager.removeEntity(ship);
			playerState = true;
			timeElapsed = 0;
			ship.setPos(ship.position.x,ship.position.y);
		}
	}

	/**
	 * Called when this screen becomes current screen.
	 */
	public void show() {}

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