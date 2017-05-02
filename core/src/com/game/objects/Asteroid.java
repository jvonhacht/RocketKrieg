package com.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import java.util.Random;

/**
 *  Asteroid class.
 *  @author David Johansson 
 *  @version 1.0 (2017-05-02)
 */
public class Asteroid extends GameEntity implements Entity{
    //Textures
    private Sprite asteroid;
    private Random random;

    public Asteroid(){
        super();
        random = new Random();

        //Load texture
        asteroid = new Sprite(new Texture("images/asteroid/Asteroid.png"));
        asteroid.setSize(MathUtils.random(50, 100), MathUtils.random(50, 100));
        asteroid.setOriginCenter();

        //Initialize starting position and direction
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
        angularVelocity = MathUtils.random(-3, 3);
    }

    public void render(SpriteBatch batch) {
        asteroid.setRotation((float)Math.toDegrees(angle)-90);
        asteroid.setPosition(position.x,position.y);
        super.render(batch, asteroid);
    }

    public void update(float delta){
        move(delta);
    }
}
