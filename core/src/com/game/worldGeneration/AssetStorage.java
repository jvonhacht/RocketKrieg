package com.game.worldGeneration;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Johan on 03/05/2017.
 */
public class AssetStorage {
    public static final Sprite tile1 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile.png")));
    public static final Sprite tile2 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile1.png")));
    public static final Sprite tile3 = new Sprite(new Texture(Gdx.files.internal("images/worldGeneration/testTile2.png")));
}
