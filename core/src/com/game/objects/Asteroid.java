package com.game.objects;


import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.game.AssetStorage;

/**
 *  Asteroid class
 *  @author David Johansson 
 *  @version 1.0 (2017-05-02)
 */
public class Asteroid extends GameEntity implements Entity{
    private Sprite asteroid;
    private float sizeX;
    private float sizeY;

    public Asteroid(float x, float y){
        super();
        sizeX = MathUtils.random(15, 50);
        sizeY = MathUtils.random(15, 50);

        //set properties
        asteroid = AssetStorage.asteroid;
        position.set(x+MathUtils.random(0,500),y+MathUtils.random(0,500));
        velocity.set(MathUtils.random(-50,50),MathUtils.random(-50,50));
        angularVelocity = MathUtils.random(-5, 5);

        //setup hitbox
        Rectangle bounds =  new Rectangle(position.x,position.y,sizeX-10,sizeY-10);
        hitbox = new Polygon(new float[]{0,0,bounds.width,0,bounds.width,bounds.height,0,bounds.height});
        hitbox.setOrigin(bounds.width/2, bounds.height/2);

        //Initialize starting position and direction
        /*
        switch(random.nextInt(4)){
            //Spawn left side
            case 0:
                position.set(-asteroid.getWidth(), (MathUtils.random(0, Gdx.graphics.getHeight())) - asteroid.getHeight());
                if(position.y > (Gdx.graphics.getHeight()/2 - asteroid.getHeight())){
                    velocity.set(100, -100);
                }
                else{
                    velocity.set(100, 100);
                }
                break;
            //Spawn bottom side
            case 1:
                position.set((MathUtils.random(0, Gdx.graphics.getWidth())) - asteroid.getWidth(), -asteroid.getHeight());
                if(position.x > (Gdx.graphics.getWidth()/2 - asteroid.getWidth())){
                    velocity.set(-MathUtils.random(100, 200), MathUtils.random(100, 200));
                }
                else{
                    velocity.set(MathUtils.random(100, 200), MathUtils.random(100, 200));
                }
                break;
            //Spawn right side
            case 2:
                position.set(Gdx.graphics.getWidth(), (MathUtils.random(0, Gdx.graphics.getHeight())) - asteroid.getHeight());
                if(position.y > (Gdx.graphics.getHeight()/2 - asteroid.getHeight())){
                    velocity.set(-MathUtils.random(100, 200), -MathUtils.random(100, 200));
                }
                else{
                    velocity.set(-MathUtils.random(100, 200), MathUtils.random(100, 200));
                }
                break;
            //Spawn top side
            case 3:
                position.set((MathUtils.random(0, Gdx.graphics.getWidth())) - asteroid.getWidth(), Gdx.graphics.getHeight());
                if(position.x > (Gdx.graphics.getWidth()/2 - asteroid.getWidth())){
                    velocity.set(-MathUtils.random(100, 200), -MathUtils.random(100, 200));
                }
                else{
                    velocity.set(MathUtils.random(100, 200), -MathUtils.random(100, 200));
                }
                break;
        }
        */
    }

    /**
     * Method to render asteroid.
     * @param batch SpriteBatch batch.
     */
    public void render(SpriteBatch batch) {
        asteroid.setSize(sizeX,sizeY);
        asteroid.setOriginCenter();
        asteroid.setRotation((float)Math.toDegrees(angle)-90);
        asteroid.setPosition(position.x,position.y);

        super.render(batch, asteroid);
    }

    /**
     * Update asteroid position.
     * @param delta time since last frame.
     */
    public void update(float delta){
        move(delta);
    }
}
