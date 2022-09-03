package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GameAnimation {

//    private Texture img;
    private TextureAtlas atlas;
    private Animation <TextureRegion> anm;
    private float time;

    public GameAnimation(String name, int col, int row, Animation.PlayMode playMode){
//        img = new Texture(name);
//
//        TextureRegion region = new TextureRegion(img);
//        int xCnt = region.getRegionWidth() / col;
//        int yCnt = region.getRegionHeight() / row;
//
//        TextureRegion[][] regions = region.split(xCnt, yCnt);
//        TextureRegion[] region1 = new TextureRegion[regions.length * regions[0].length];
//
//        int index = 0;
//        for (int i = 0; i < regions.length; i++) {
//            for (int j = 0; j < regions[0].length; j++) {
//                region1[index++] = regions[i][j];
//            }
//        }
        atlas = new TextureAtlas("atlas/pokemons.atlas");

        anm = new Animation<TextureRegion>(1/15f, atlas.findRegions("green"));
        anm.setPlayMode(playMode);

        time += Gdx.graphics.getDeltaTime();
    }

    public TextureRegion getFrame(){
        return anm.getKeyFrame(time);
    }

    public void setTime(float time){
        this.time += time;
    }

    public void zeroTime(){
        this.time = 0;
    }

    public boolean isAnimationOver(){
        return anm.isAnimationFinished(time);
    }

    public void setPlayMode(Animation.PlayMode playMode) {
        anm.setPlayMode(playMode);
    }

    public void dispose(){
//        img.dispose();
        atlas.dispose();
    }
}
