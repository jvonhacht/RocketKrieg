package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.StringBuilder;
import com.game.menus.GameOver;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.System.err;

/**
 * Rocket League game mode
 * @author Johan von Hacht & David Johansson
 * @version 1.6 (2017-08-06)
 */
public class RocketKrieg implements Screen {
	static final int GAME_RUNNING = 1;
	static final int GAME_PAUSED = 2;
	static final int GAME_OVER = 3;
	private int state;

	private final GameEntry game;
	private static OrthographicCamera camera;
	private static PlayerSpaceShip ship;
	private Sprite instructions;
	private Sprite gameOver;
	private Sprite singleSparkle;
	private Sprite gamePaused;
	private Sprite pausedFilter;
	private ChunkManager cm;
	private AssetStorage ass; //:-)
	private static int score;
	private static int currency;
	private boolean startPhase;

	private double time = 0.0;
	private double tick = 1/300f;
	private double accumulator = 0.0;
	private Vector2 prevPos;
	private Vector2 currentPos;

	BitmapFont font2;

	//Timers
	private float timeElapsed;
	private float instructionTimer;
	private static float pointTimer;
	private float gameOverTimer;
	private float pauseTimer;


	/**
	 * Constructor for RocketKrieg screen.
	 * @param game GameEntry with SpriteBatch
	 */
	public RocketKrieg(final GameEntry game) {
		this.game = game;
		startPhase = true;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		camera.position.x = 0;
		camera.position.y = 0;

		instructions = AssetStorage.instructions;
		gameOver = AssetStorage.gameOver;
		singleSparkle = AssetStorage.singleSparkle;
		gamePaused = AssetStorage.gamePaused;
		pausedFilter = AssetStorage.pauseFilter;
		ass = new AssetStorage();
		ship = new PlayerSpaceShip();
		cm = new ChunkManager(ship, camera);
		timeElapsed = 10;
		instructionTimer = 0;
		pointTimer = 2;
		gameOverTimer = 0;
		reloadPlayerStats();
		score = 0;

		state = GAME_RUNNING;

		pauseTimer = 3;

		//Initialize second font
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Tw_Cen_MT_Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 50;
		font2 = generator.generateFont(parameter);
		generator.dispose();
	}

	/**
	 * Render method for rendering the graphics.
	 * Also checks for player input
	 * @param delta time since last frame
	 */
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		switch (state) {
			case GAME_RUNNING:
				GameEntry.batch.begin();
				updateRunning(true);
				GameEntry.batch.end();
				break;
			case GAME_PAUSED:
				GameEntry.batch.begin();
				updateGamePaused();
				GameEntry.batch.end();
				break;
			case GAME_OVER:
				GameEntry.batch.begin();
				updateGameOver();
				GameEntry.batch.end();
				break;
		}
		timeElapsed += delta;
		instructionTimer += delta;
		pointTimer += delta;
		gameOverTimer += delta;
		pauseTimer += delta;
	}

	/**
	 * Game running screen.
	 */
	public void updateRunning(boolean updatePhysics) {
		boolean isAlive = ship.getPlayerState();

		//camera
		GameEntry.batch.setProjectionMatrix(camera.combined);
		camera.update();
		Vector3 cameraPosition = camera.position;
		Vector2 shipPosition = ship.getPosition();
		Vector2 shipVelocity = ship.getVelocity();
		float lerp = 2f;
		float delta = 2*Gdx.graphics.getDeltaTime();
		cameraPosition.x += (shipPosition.x - cameraPosition.x) * lerp * delta + shipVelocity.x*delta/lerp;
		cameraPosition.y += (shipPosition.y - cameraPosition.y) * lerp * delta + shipVelocity.y*delta/lerp;

		//fixed step update and rendering
		float frameTime = Gdx.graphics.getDeltaTime();
		if(frameTime>0.25) {
			frameTime = 0.25f;
		}
		if(updatePhysics) {
			accumulator += frameTime;
			while (accumulator >= tick) {
				prevPos = ship.getPosition();
				cm.render(true,(float)tick);
				accumulator -= tick;
				time += tick;
			}
		}
		cm.render(false,1);
		//interpolate ship
		float alpha = (float)(accumulator/tick);
		currentPos = ship.getPosition();
		Vector2 lerpPosition = prevPos.interpolate(currentPos,alpha, Interpolation.linear);
		if(isAlive) {
			ship.renderr(GameEntry.batch, lerpPosition);
		}

		//draw score
		if(state != GAME_PAUSED) {
			GameEntry.font.draw(GameEntry.batch, "Score: " + score, cameraPosition.x - 25, cameraPosition.y + Gdx.graphics.getHeight() / 2 - 50);
			GameEntry.font.draw(GameEntry.batch, cm.getMissionMessage(), cameraPosition.x - 500, cameraPosition.y + Gdx.graphics.getHeight() / 2 - 100);
			GameEntry.font.draw(GameEntry.batch, "Currency: " + currency, cameraPosition.x - 50, cameraPosition.y + Gdx.graphics.getHeight() / 2 - 75);
			GameEntry.font.draw(GameEntry.batch, "Shield Charges: " + Integer.toString(ship.getShieldCharge()), cameraPosition.x - 200, cameraPosition.y + Gdx.graphics.getHeight() / 2 - 50);
			GameEntry.font.draw(GameEntry.batch, "Time: " + Integer.toString((int) time), cameraPosition.x + 90, cameraPosition.y + Gdx.graphics.getHeight() / 2 - 50);
			GameEntry.font.draw(GameEntry.batch, "Bcharge: " + Float.toString(ship.getBoostCharge()), cameraPosition.x + 90, cameraPosition.y + Gdx.graphics.getHeight() / 2 - 100);
			GameEntry.font.draw(GameEntry.batch, "Speed: " + Float.toString(ship.getSpeed()), cameraPosition.x + 90, cameraPosition.y + Gdx.graphics.getHeight() / 2 - 120);
		}

		//draw instructions
		if(startPhase && isAlive && state != GAME_PAUSED) {
			GameEntry.batch.draw(instructions, ship.getPosition().x, ship.getPosition().y - instructions.getHeight() / 2);
			GameEntry.batch.draw(singleSparkle, ship.getPosition().x + 331, ship.getPosition().y - 115, 25f, 25f);
			if(instructionTimer > 15){
				startPhase = false;
			}
		}

		//draw "+1"
		if(pointTimer < 2) {
			GameEntry.font.draw(GameEntry.batch,"+1",ship.getPosition().x,ship.getPosition().y);
		}

		//game over
		if(!isAlive){
			ship.restart();
			savePlayerStats();
			state = GAME_OVER;
		} else  {
			gameOverTimer = 0;
		}

		//input
		if(Gdx.input.isKeyPressed(Input.Keys.O)) {
			saveGame();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			savePlayerStats();
			Gdx.app.exit();
		}

		if(Gdx.input.isKeyPressed(Input.Keys.L) && pauseTimer > 3) {
			state = GAME_PAUSED;
			pauseTimer = 0;
			//Screenshot.saveScreenshot();
		}
		if(state == GAME_PAUSED && pauseTimer > 1){
			//Create blurred background, currently bad quality
			//PauseBlur pb = new PauseBlur();
			//pb.createBlur();
			//pb.render();

			GameEntry.batch.draw(pausedFilter, shipPosition.x - Gdx.graphics.getWidth()/2, shipPosition.y - Gdx.graphics.getHeight()/2, Gdx.graphics.getWidth() + 200, Gdx.graphics.getHeight() + 200);
			GameEntry.batch.draw(gamePaused, shipPosition.x - gamePaused.getWidth()/2, shipPosition.y - gamePaused.getHeight()/2);
			font2.draw(GameEntry.batch, "" + score , shipPosition.x + 155, shipPosition.y - 7);
		}
	}

	/**
	 * Game over screen.
	 */
	public void updateGameOver() {
		updateRunning(true);
		GameEntry.batch.draw(gameOver, ship.getPosition().x - (gameOver.getWidth()/2), ship.getPosition().y - (gameOver.getHeight()/2) + 40);
		if(gameOverTimer>2) {
			ship.restart();
			game.setScreen(new GameOver(game, score));
		}
	}

	/**
	 * Game paused screen.
	 */
	public void updateGamePaused() {
		if(Gdx.input.isKeyPressed(Input.Keys.K)) {
			state = GAME_RUNNING;
		}
		updateRunning(false);
	}

	/**
	 * Method to save player stats to file.
	 */
	public void savePlayerStats() {
		//save game stats
		try {
			File gameChunks = new File("playerData.rk");
			FileOutputStream fos = new FileOutputStream(gameChunks);
			PrintWriter pw = new PrintWriter(fos);
			StringBuilder sb = new StringBuilder();
			sb.append(score); sb.append("B");
			sb.append(currency); sb.append("B");
			sb.append(time); sb.append("B");
			sb.append(ship.getShieldCharge());
			String toPrint = sb.toString();
			toPrint = Base64Coder.encodeString(toPrint);
			pw.println(toPrint);
			// Add support to save component and loadout.

			pw.flush();
			pw.close();
			fos.close();
		} catch(Exception e) {
			err.println(e);
		}
	}

	/**
	 * Method to save game to file.
	 */
	public void saveGame() {
		cm.saveGame();
	}

	/**
	 * Method to reload player stats from file.
	 */
	public void reloadPlayerStats() {
		try{
			File toRead = new File("playerData.rk");
			FileInputStream fis = new FileInputStream(toRead);
			Scanner sc = new Scanner(fis);
			String currentLine;
			if(sc.hasNextLine()){
				currentLine = sc.nextLine();
				byte[] byteLine = Base64Coder.decode(currentLine);
				currentLine = new String(byteLine,"UTF-8");
				String[] data = currentLine.split(Pattern.quote("B"));
				score = Integer.parseInt(data[0]);
				currency = Integer.parseInt(data[1]);
				time = Float.parseFloat(data[2]);
				ship.setShieldCharge(Integer.parseInt(data[3]));
			}
			fis.close();
		}catch(Exception e){
			err.println(e);
		}
	}

	/**
	 * Method to reload game from file.
	 */
	public void reloadGame() {
		cm.reloadGame();
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
	 * Return the camera.
	 * @return
	 */
	public static OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * Increases the player's score
	 * @param amount to increase
	 */
	public static void inscreaseScore(int amount) {
		score += amount;
		currency += amount;
		pointTimer = 0;
	}

	/**
	 * Get player score
	 * @return score
	 */
	public static int getScore() {
		return score;
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