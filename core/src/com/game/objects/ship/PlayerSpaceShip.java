package com.game.objects.ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.AssetStorage;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.game.GameEntry;

import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.ship.shipComponent.*;
import com.game.objects.ship.shipComponent.reloadComponent.ReloadComponentMk5;
import com.game.objects.ship.shipComponent.shieldComponent.ShieldComponentInterface;
import com.game.objects.ship.shipComponent.shieldComponent.ShieldComponentMk5;
import com.game.objects.ship.shipComponent.speedComponent.SpeedComponentMk5;
import com.game.objects.ship.shipComponent.turningComponent.TurningComponentMk5;
import com.game.objects.ship.shipComponent.weaponComponent.*;

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
    private WeaponComponentInterface weaponComponent;
    private Component speedComponent;
    private Component turningComponent;
    private ShieldComponentInterface shieldComponent;
    private Component reloadComponent;
    private float MAX_ANGULARVELOCITY = 1000f;
    //Textures
    private Sprite spaceship;
    private Animation<TextureRegion> flame;
    private Animation<TextureRegion> shield;
    private boolean playerHit;
    private float spaceshipTime;

    /**
     * Initialise player ship.
     */
    public PlayerSpaceShip() {
        super();
        weaponComponent = new SingleMissileComp();
        speedComponent = new SpeedComponentMk5();
        turningComponent = new TurningComponentMk5();
        shieldComponent = new ShieldComponentMk5();
        reloadComponent = new ReloadComponentMk5();

        //set size of spaceship
        sizeX = 25;
        sizeY = 70;
        playerState = false;

        //load images
        spaceship = AssetStorage.spaceship;
        spaceship.setSize(sizeX,sizeY);
        flame = AssetStorage.flameAnimation;
        shield = AssetStorage.shieldAnimation;
        spaceshipTime = 0;

        //setup hitbox
        Rectangle bounds = new Rectangle(position.x+sizeX,position.y+sizeY,sizeX,sizeY);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2,bounds.height/2);

        ID = 1;
    }

    /**
     * Render the players ship.
     */
    public void renderr(SpriteBatch batch, Vector2 position) {
        Sprite img = spaceship;

        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            GameEntry.batch.draw(flame.getKeyFrame(timeElapsed,true), position.x, position.y+70, sizeX/2, sizeY/2-70, sizeX, sizeY, 2f, 0.5f,(float)Math.toDegrees(angle)+90);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            GameEntry.batch.draw(flame.getKeyFrame(timeElapsed,true), position.x-18, position.y+50, sizeX/2+18, sizeY/2-50, sizeX, sizeY, 1f, 0.2f,(float)Math.toDegrees(angle)+180);
            GameEntry.batch.draw(flame.getKeyFrame(timeElapsed,true), position.x-18, position.y+45, sizeX/2+18, sizeY/2-45, sizeX, sizeY, 1f, 0.28f,(float)Math.toDegrees(angle));
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            //GameEntry.batch.draw(animation.getKeyFrame(timeElapsed,true), position.x, position.y+40, sizeX/2, sizeY/2-40, sizeX, sizeY, 1f, 0.4f,(float)Math.toDegrees(angle));
            GameEntry.batch.draw(flame.getKeyFrame(timeElapsed,true), position.x+18, position.y+50, sizeX/2-18, sizeY/2-50, sizeX, sizeY, 1f, 0.2f,(float)Math.toDegrees(angle));
            GameEntry.batch.draw(flame.getKeyFrame(timeElapsed,true), position.x+18, position.y+45, sizeX/2-18, sizeY/2-45, sizeX, sizeY, 1f, 0.28f,(float)Math.toDegrees(angle)+180);
        }
        spaceship.setRotation((float)Math.toDegrees(angle));

        super.render(batch, spaceship, Math.toDegrees(angle));

        if(playerHit){
            GameEntry.batch.draw(shield.getKeyFrame(timeElapsed, true), position.x, position.y, sizeX / 2, sizeY / 2, sizeX, sizeY, 2.4f, 1f, (float) Math.toDegrees(angle) + 270);
            if(spaceshipTime > 1) {
                playerHit = false;
            }
        }
    }

    /**
     * Empty method for interface.
     * @param batch
     */
    public void render(SpriteBatch batch) {}

    /**
     * Update ship values based on input.
     */
    public void update(float delta) {
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
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireMissile();
        }
        //reset position to center of the screen.
        if(Gdx.input.isKeyPressed(Input.Keys.R)) {
            setPos(0,0);
        }
        spaceshipTime += delta;
    }

    /**
     * Fire a missile.
     */
    public void fireMissile() {
        if(timeElapsed > reloadComponent.getStats()) {
            weaponComponent.fireMissile(position,velocity,acceleration,angle,angularVelocity);
            timeElapsed = 0;
        }
    }

    /**
     * Turn right by changing the spaceship angular velocity.
     */
    public void turnRight(float delta) {
        if(angularVelocity<MAX_ANGULARVELOCITY) {
            angularVelocity += turningComponent.getStats()*delta;
        }
    }

    /**
     * Turn left by changing the spaceship angular velocity.
     */
    public void turnLeft(float delta) {
        if(angularVelocity>-MAX_ANGULARVELOCITY) {
            angularVelocity -= turningComponent.getStats()*delta;
        }
    }

    /**
     * Accelerate forward.
     */
    public void accel(float delta) {
        float SPEED_MULTIPLIER = speedComponent.getStats();
        acceleration.add((float)Math.cos(angle)*SPEED_MULTIPLIER*delta,
                (float)Math.sin(angle)*SPEED_MULTIPLIER*delta);
    }

    /**
     * Return shield charge.
     * @return
     */
    public int getShieldCharge() {
        return shieldComponent.getCharges();
    }

    /**
     * Reduce shield charges.
     */
    public void reduceShieldCharge() {
        shieldComponent.reduceCharge();
        spaceshipTime = 0;
        playerHit = true;
    }

    /**
     * Change shield component.
     * @param component
     */
    public void setShieldComponent(ShieldComponentInterface component) {
        shieldComponent = component;
    }

    /**
     * Change weapon component.
     * @param component
     */
    public void setWeaponComponent(WeaponComponentInterface component) {
        weaponComponent = component;
    }

    /**
     * Change speed component.
     * @param component
     */
    public void setSpeedComponent(Component component) {
        speedComponent = component;
    }

    /**
     * Change turning speed component.
     * @param component
     */
    public void setTurningComponent(Component component) {
        turningComponent = component;
    }

    /**
     * Change reload component.
     * @param component
     */
    public void setReloadComponent(Component component) {
        reloadComponent = component;
    }
}
