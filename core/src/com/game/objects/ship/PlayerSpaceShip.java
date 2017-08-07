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

import com.game.RocketKrieg;
import com.game.objects.Entity;
import com.game.objects.GameEntity;
import com.game.objects.ship.shipComponent.*;
import com.game.objects.ship.shipComponent.boostComponent.*;
import com.game.objects.ship.shipComponent.reloadComponent.*;
import com.game.objects.ship.shipComponent.shieldComponent.*;
import com.game.objects.ship.shipComponent.speedComponent.*;
import com.game.objects.ship.shipComponent.turningComponent.*;
import com.game.objects.ship.shipComponent.weaponComponent.*;
import com.game.worldGeneration.ChunkManager;

/**
 *  PlayerSpaceShip entity class.
 *  @author Johan von Hacht
 *  @version 1.0 (2017-04-27)
 */
public class PlayerSpaceShip extends GameEntity implements Entity {
    private boolean isAlive;
    //Components and multipliers
    private WeaponComponent weaponComponent;
    private ShipComponent speedComponent;
    private ShipComponent turningComponent;
    private ShieldComponent shieldComponent;
    private ShipComponent reloadComponent;
    private BoostComponent boostComponent;
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
        boostComponent = new BoostComponentMk5();

        //set size of spaceship
        sizeX = 25;
        sizeY = 70;
        isAlive = true;

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

        hitpoints = 100;
        totalHealth = 100;

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
        //render hp
        float width = (float)hitpoints / (float)totalHealth * 200;
        healthBar.draw(batch, position.x,position.y, width, 5f);
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
        boost(delta); //check if boost

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
        if(timeElapsed > reloadComponent.getStats()*weaponComponent.getReloadTime()) {
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
        if(shieldComponent!=null) {
            return shieldComponent.getCharges();
        } else {
            return 0;
        }
    }

    /**
     * Return boost charge.
     * @return
     */
    public float getBoostCharge() {
        if(boostComponent!=null) {
            return boostComponent.getCharge();
        } else {
            return 0;
        }
    }

    public float getSpeed() {
        if(speedComponent!=null) {
            return speedComponent.getStats();
        } else {
            return 0;
        }
    }

    /**
     * Boost ship speed.
     * @param delta
     */
    public void boost(float delta){
        float originalSpeed = speedComponent.getUnchangedStats();
        if(boostComponent != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && (boostComponent.getCharge() > 0)) {
                boostComponent.boost(delta);
                speedComponent.setStats(2f * originalSpeed);
            } else if(!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
                speedComponent.setStats(originalSpeed);
                boostComponent.reChargeBoost(delta);
            } else {
                speedComponent.setStats(originalSpeed);
            }
        }
    }

    /**
     * Ship hit by entity.
     */
    public boolean hit(int amount, boolean planetCollision) {
        if(hitpoints<1) {
            isAlive = false;
            return true;
        }
        if (!planetCollision) {
            if (getShieldCharge() > 0) {
                reduceShieldCharge();
                return false;
            } else {
                hitpoints -= amount;
                return hitpoints>0;
            }
        } else {
            isAlive = false;
            setPos(position.x, position.y);
            return true;
        }
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
     * Restart ship in between games.
     */
    public void restart() {
        if(shieldComponent!=null) {
            shieldComponent.shieldReset();
        }
    }

    /**
     * Get state of player.
     * @return
     */
    public boolean getPlayerState() {
        return isAlive;
    }

    /**
     * Set shield charge.
     * @param amount
     */
    public void setShieldCharge(int amount) {
        shieldComponent.setShieldCharge(amount);
    }

    /**
     * Change shield component.
     * @param component
     */
    public void setShieldComponent(ShieldComponent component) {
        shieldComponent = component;
    }

    /**
     * Change weapon component.
     * @param component
     */
    public void setWeaponComponent(WeaponComponent component) {
        weaponComponent = component;
    }

    /**
     * Change speed component.
     * @param component
     */
    public void setSpeedComponent(ShipComponent component) {
        speedComponent = component;
    }

    /**
     * Change turning speed component.
     * @param component
     */
    public void setTurningComponent(ShipComponent component) {
        turningComponent = component;
    }

    /**
     * Change reload component.
     * @param component
     */
    public void setReloadComponent(ShipComponent component) {
        reloadComponent = component;
    }

    public WeaponComponent getWeaponComponent() {
        return weaponComponent;
    }
}
