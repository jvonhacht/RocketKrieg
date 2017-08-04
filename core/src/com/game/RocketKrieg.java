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
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.StringBuilder;
import com.game.objects.ship.PlayerSpaceShip;
import com.game.worldGeneration.ChunkManager;
import com.game.worldGeneration.Pair;

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
	private boolean startPhase;
	private static boolean playerState;

	private double time = 0.0;
	private double tick = 1/300f;
	private double accumulator = 0.0;
	private Vector2 prevPos;
	private Vector2 currentPos;

	//Timers
	private static float timeElapsed;
	private static float instructionTimer;
	private static float pointTimer;
	private static float gameOverTimer;


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
		instructionTimer = 0;
		pointTimer = 2;
		gameOverTimer = 0;
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
		GameEntry.font.draw(GameEntry.batch, "Score: " + score,cameraPosition.x - 25, cameraPosition.y + Gdx.graphics.getHeight()/2 -50);
		GameEntry.font.draw(GameEntry.batch,"Shield Charges: " + Integer.toString(ship.getShieldCharge()),cameraPosition.x - 200, cameraPosition.y + Gdx.graphics.getHeight()/2 -50);
		GameEntry.font.draw(GameEntry.batch,"Time: " + Integer.toString((int)time),cameraPosition.x + 90, cameraPosition.y + Gdx.graphics.getHeight()/2 - 50);

		//draw instructions
		if(startPhase && !playerState) {
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

		//draw game over message
		if(playerState){
			GameEntry.batch.draw(gameOver, ship.getPosition().x - (gameOver.getWidth()/2), ship.getPosition().y - (gameOver.getHeight()/2) + 40);
		}

		GameEntry.batch.end();

		//input
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
			Gdx.app.exit();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.O)) {
			saveGame();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.P)) {
			reloadGame();
		}

		//game over
		if(playerState){
			if(gameOverTimer > 2){
				game.setScreen(new GameOver(game, score));
			}
		} else {
			gameOverTimer = 0;
		}
		timeElapsed += delta;
		instructionTimer += delta;
		pointTimer += delta;
		gameOverTimer += delta;
	}

	/**
	 * Method to save game to file.
	 */
	public void saveGame() {
		cm.saveGame();
		//save game stats
		try {
			File gameChunks = new File("playerData.rk");
			FileOutputStream fos = new FileOutputStream(gameChunks);
			PrintWriter pw = new PrintWriter(fos);
			StringBuilder sb = new StringBuilder();
			sb.append(score); sb.append("&");
			sb.append(time); sb.append("&");
			sb.append(ship.getShieldCharge());
			String toPrint = sb.toString();
			Base64Coder.encodeString(toPrint);
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
	 * Method to reload game from file.
	 */
	public void reloadGame() {
		cm.reloadGame();
		try{
			File toRead = new File("playerData.rk");
			FileInputStream fis = new FileInputStream(toRead);
			Scanner sc = new Scanner(fis);
			String currentLine;
			if(sc.hasNextLine()){
				currentLine = sc.nextLine();
				byte[] byteLine = Base64Coder.decode(currentLine);
				currentLine = new String(byteLine,"UTF-8");
				String[] data = currentLine.split(Pattern.quote("&"));
				score = Integer.parseInt(data[0]);
				time = Float.parseFloat(data[1]);
				ship.setShieldCharge(Integer.parseInt(data[2]));
			}
			fis.close();
		}catch(Exception e){
			err.println(e);
		}
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
	 * Indicate that player is dead
	 */
	public static void playerDead(boolean planetCollision){
		if(ship.getShieldCharge()>0 && !planetCollision) {
			ship.reduceShieldCharge();
		} else {
			ChunkManager.removeEntity(ship);
			playerState = true;
			ship.setPos(ship.position.x,ship.position.y);
		}
		timeElapsed = 0;
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