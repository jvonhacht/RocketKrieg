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

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		ship.update(delta);
		game.batch.begin();
		ship.render(game.batch);
		game.batch.end();
	}

	@Override
	public void resize(int i, int i1) {

	}

	@Override
	public void pause() {

	}

	@Override
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
