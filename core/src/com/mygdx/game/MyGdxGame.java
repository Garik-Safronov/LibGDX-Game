package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {

    private SpriteBatch batch;
    private GameAnimation animation;
    private float x = 0;
    private float y = 0;
    private boolean toRight = true;

    @Override
    public void create() {
        batch = new SpriteBatch();
        animation = new GameAnimation("bird.png", 5, 3, Animation.PlayMode.LOOP);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        animation.setTime(Gdx.graphics.getDeltaTime());
//        float x = Gdx.input.getX() - animation.getFrame().getRegionWidth() / 2;
//        float y = Gdx.graphics.getHeight() - Gdx.input.getY() - animation.getFrame().getRegionHeight() / 2;

        if (x + animation.getFrame().getRegionWidth() >= Gdx.graphics.getWidth()){
            toRight = false;
        }
        if (x <= 0){
            toRight = true;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            toRight = true;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            toRight = false;
        }

        if(animation.getFrame().isFlipX() && toRight){
            animation.getFrame().flip(true,false);
        }
        if(!animation.getFrame().isFlipX() && !toRight){
            animation.getFrame().flip(true,false);
        }

        if(toRight){
            x++;
        } else {
            x--;
        }

        batch.begin();
        batch.draw(animation.getFrame(), x, y);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        animation.dispose();
    }
}
