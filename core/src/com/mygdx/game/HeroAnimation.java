package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Animation;

public class HeroAnimation {

    private GameAnimation heroAnimation;

    public void stay() {
        heroAnimation = new GameAnimation("atlas/hero.atlas", "Idle", Animation.PlayMode.LOOP);
    }

    public void run() {
        heroAnimation = new GameAnimation("atlas/hero.atlas", "Run", Animation.PlayMode.LOOP);
    }

    public void jump() {
        heroAnimation = new GameAnimation("atlas/hero.atlas", "Jump", Animation.PlayMode.NORMAL);
    }

    public GameAnimation getAnimation() {
        return heroAnimation;
    }

    public void dispose(){
        heroAnimation.dispose();
    }

}
