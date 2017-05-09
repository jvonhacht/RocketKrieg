package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.GameEntry;


/**
 * Created by Johan on 27/04/2017.
 */
public class PlayerSpaceShip extends GameEntity implements Entity{
    private float timeElapsed;
    private float sizeX;
    private float sizeY;
    //Multipliers
    private final float SPEED_MULTIPLIER = 5000F;
    private final float MAX_ANGULARVELOCITY = 10F;
    private final float TURNING_SPEED = 3F;
    //Textures
    private Sprite spaceship;
    private Animation<TextureRegion> animation;

    /**
     * Initialise player ship.
     */
    public PlayerSpaceShip() {
        super();
        //set size of spaceship
        sizeX = 25;
        sizeY = 70;

        //load images
        spaceship = AssetStorage.spaceship;
        spaceship.setSize(sizeX,sizeY);
        animation = AssetStorage.flameAnimation;

        //setup hitbox
        Rectangle bounds = new Rectangle(position.x+sizeX,position.y+sizeY,sizeX,sizeY);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2,bounds.height/2);
        timeElapsed = 0;
    }

    /**
     * Render the players ship.
     */
    public void render(SpriteBatch batch) {
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y+60, sizeX/2, sizeY/2-60, sizeX, sizeY, 2.5f, 0.8f,(float)Math.toDegrees(angle)+90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y+40, sizeX/2, sizeY/2-40, sizeX, sizeY, 1f, 0.4f,(float)Math.toDegrees(angle)+180);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y+40, sizeX/2, sizeY/2-40, sizeX, sizeY, 1f, 0.4f,(float)Math.toDegrees(angle));
        }
        spaceship.setOriginCenter();
        spaceship.setRotation((float)Math.toDegrees(angle)-90);
        spaceship.setPosition(position.x,position.y);
        super.render(batch, spaceship);
    }

    /**
     * Update ship values based on input.
     * @param delta
     */
    public void update(float delta) {
        timeElapsed += delta;
        move(delta);

        //flight controls
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            accel(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            turnLeft(delta);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            turnRight(delta);
        }
        //reset position to center of the screen.
        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            setPos(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
        }
    }

    /**
     * Turn right by changing the spaceship angular velocity.
     */
    public void turnRight(float delta) {
        if(angularVelocity<MAX_ANGULARVELOCITY) {
            angularVelocity += TURNING_SPEED*delta;
        }
    }

    /**
     * Turn left by changing the spaceship angular velocity.
     */
    public void turnLeft(float delta) {
        if(angularVelocity>-MAX_ANGULARVELOCITY) {
            angularVelocity -= TURNING_SPEED*delta;
        }
    }

    /**
     * Accelerate forward.
     */
    public void accel(float delta) {
        acceleration.set((float)Math.cos(angle)*SPEED_MULTIPLIER*delta,(float)Math.sin(angle)*SPEED_MULTIPLIER*delta);
    }
}
