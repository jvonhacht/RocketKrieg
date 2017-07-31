package com.game.objects.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.game.AssetStorage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.GameEntry;

import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.ship.shipComponent.*;

/**
 *  PlayerSpaceShip entity class.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-04-27)
 */
public class PlayerSpaceShip extends GameEntity implements Entity {
    private float sizeX;
    private float sizeY;
    private boolean playerState;
    //Components and multipliers
    private Component weaponComponent;
    private Component speedComponent;
    private Component turningComponent;
    private float MAX_ANGULARVELOCITY = 1000f;
    //Textures
    private Sprite spaceship;
    private Animation<TextureRegion> animation;

    /**
     * Initialise player ship.
     */
    public PlayerSpaceShip() {
        super();
        weaponComponent = new DoubleMissileComp();
        speedComponent = new StandardSpeedComp();
        turningComponent = new StandardTurningSpeedComp();

        //set size of spaceship
        sizeX = 25;
        sizeY = 70;
        playerState = false;

        //load images
        spaceship = AssetStorage.spaceship;
        spaceship.setSize(sizeX,sizeY);
        animation = AssetStorage.flameAnimation;

        //setup hitbox
        Rectangle bounds = new Rectangle(position.x+sizeX,position.y+sizeY,sizeX,sizeY);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2,bounds.height/2);
    }

    /**
     * Render the players ship.
     */
    public void render(SpriteBatch batch) {
        System.out.println(Gdx.graphics.getDeltaTime());
        Sprite img = spaceship;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y+70, sizeX/2, sizeY/2-70, sizeX, sizeY, 2f, 0.5f,(float)Math.toDegrees(angle)+90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x-18, position.y+50, sizeX/2+18, sizeY/2-50, sizeX, sizeY, 1f, 0.2f,(float)Math.toDegrees(angle)+180);
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x-18, position.y+45, sizeX/2+18, sizeY/2-45, sizeX, sizeY, 1f, 0.28f,(float)Math.toDegrees(angle));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            //GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y+40, sizeX/2, sizeY/2-40, sizeX, sizeY, 1f, 0.4f,(float)Math.toDegrees(angle));
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x+18, position.y+50, sizeX/2-18, sizeY/2-50, sizeX, sizeY, 1f, 0.2f,(float)Math.toDegrees(angle));
            GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x+18, position.y+45, sizeX/2-18, sizeY/2-45, sizeX, sizeY, 1f, 0.28f,(float)Math.toDegrees(angle)+180);
        }
        spaceship.setRotation((float)Math.toDegrees(angle)-90);
        super.render(batch, spaceship);
    }

    /**
     * Update ship values based on input.
     */
    public void update() {
        move();

        //flight controls
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            accel();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            turnLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            turnRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireMissile();
        }
        //reset position to center of the screen.
        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            setPos(0,0);
        }
    }

    /**
     * Fire a missile.
     */
    public void fireMissile() {
        if(timeElapsed > weaponComponent.getStats()[0]) {
            weaponComponent.fireMissile(position,velocity,acceleration,angle,angularVelocity);
            timeElapsed = 0;
        }
    }

    /**
     * Turn right by changing the spaceship angular velocity.
     */
    public void turnRight() {
        if(angularVelocity<MAX_ANGULARVELOCITY) {
            angularVelocity += turningComponent.getStats()[2]*Gdx.graphics.getDeltaTime();
        }
    }

    /**
     * Turn left by changing the spaceship angular velocity.
     */
    public void turnLeft() {
        if(angularVelocity>-MAX_ANGULARVELOCITY) {
            angularVelocity -= turningComponent.getStats()[2]*Gdx.graphics.getDeltaTime();
        }
    }

    /**
     * Accelerate forward.
     */
    public void accel() {
        float SPEED_MULTIPLIER = speedComponent.getStats()[1];
        acceleration.add((float)Math.cos(angle)*SPEED_MULTIPLIER*Gdx.graphics.getDeltaTime(),
                (float)Math.sin(angle)*SPEED_MULTIPLIER*Gdx.graphics.getDeltaTime());
    }
}
