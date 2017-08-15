package com.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g3d.particles.ResourceData;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.StringBuilder;
import com.game.menus.ComponentsMenu;
import com.game.menus.GameOver;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;
import com.game.worldGeneration.ZoneManager;

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
	private Sprite hudBar;
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
	BitmapFont font3;

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
		hudBar = AssetStorage.hudBar;

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

		//Initialize third font
		FreeTypeFontGenerator generator3 = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Tw_Cen_MT_Bold.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter3 = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter3.size = 35;
		font3 = generator3.generateFont(parameter3);
		generator3.dispose();
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

		//draw HUD
		if(updatePhysics) {
			GameEntry.batch.draw(hudBar, cameraPosition.x - hudBar.getWidth()/2, cameraPosition.y + Gdx.graphics.getHeight()/2 - hudBar.getHeight() - 20);
			font3.draw(GameEntry.batch, "" + score, cameraPosition.x - 170, cameraPosition.y + Gdx.graphics.getHeight()/2 - 30);
			font3.draw(GameEntry.batch, "" + Integer.toString(ship.getShieldCharge()), cameraPosition.x - 70, cameraPosition.y + Gdx.graphics.getHeight()/2 - 30);
			font3.draw(GameEntry.batch, "" + (int)(((ship.getBoostCharge())/5)*100) + "%", cameraPosition.x + 20, cameraPosition.y + Gdx.graphics.getHeight()/2 - 30);
			font3.draw(GameEntry.batch, "" + Integer.toString((int) time) + "s", cameraPosition.x + 170, cameraPosition.y + Gdx.graphics.getHeight()/2 - 30);
			//TODO fix formatting of mission message
			GameEntry.font.draw(GameEntry.batch, ("Mission: " + cm.getMissionMessage()), cameraPosition.x - hudBar.getWidth()/2, cameraPosition.y + Gdx.graphics.getHeight()/2 - 70);
		}

		//draw instructions
		if(startPhase && isAlive && updatePhysics) {
			GameEntry.batch.draw(instructions, ship.getPosition().x, ship.getPosition().y - instructions.getHeight() / 2);
			GameEntry.batch.draw(singleSparkle, ship.getPosition().x + 331, ship.getPosition().y - 115, 25f, 25f);
			if(instructionTimer > 15){
				startPhase = false;
			}
		}

		//draw "+1"
		if(pointTimer < 2) {
			GameEntry.font.draw(GameEntry.batch,"+" + Integer.toString(ZoneManager.getZone()),ship.getPosition().x,ship.getPosition().y);
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
		//TODO fix background blur during pauses
		//PauseBlur pb = new PauseBlur();
		//pb.createBlur();
		//pb.render();
		GameEntry.batch.setColor(1.0f, 1.0f, 1.0f, (float)-(Math.exp(-pauseTimer)-1));
		GameEntry.batch.draw(pausedFilter, ship.position.x - Gdx.graphics.getWidth()/2 - 100, ship.position.y - Gdx.graphics.getHeight()/2 - 100, Gdx.graphics.getWidth() + 200, Gdx.graphics.getHeight() + 200);
		GameEntry.batch.draw(gamePaused, ship.position.x - gamePaused.getWidth()/2, ship.position.y - gamePaused.getHeight()/2);
		font2.draw(GameEntry.batch, "" + score , ship.position.x + 155, ship.position.y - 7);
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
			sb.append(score); sb.append("&");
			sb.append(currency); sb.append("&");
			sb.append(time);
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
			if(toRead.exists()) {
				FileInputStream fis = new FileInputStream(toRead);
				Scanner sc = new Scanner(fis);
				String currentLine;
				if(sc.hasNextLine()){
					currentLine = sc.nextLine();
					byte[] byteLine = Base64Coder.decode(currentLine);
					currentLine = new String(byteLine,"UTF-8");
					String[] data = currentLine.split(Pattern.quote("&"));
					score = Integer.parseInt(data[0]);
					currency = Integer.parseInt(data[1]);
					time = Float.parseFloat(data[2]);
				}
				fis.close();
			}
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
	 */
	public static void inscreaseScore() {
		score += ZoneManager.getZone();
		currency += ZoneManager.getZone();
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