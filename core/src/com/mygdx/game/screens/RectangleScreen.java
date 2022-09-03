package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;

public class RectangleScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private Rectangle rectangle;
    private ShapeRenderer shapeRenderer;

    public RectangleScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        rectangle = new Rectangle((Gdx.graphics.getWidth() - 300) / 2, (Gdx.graphics.getHeight() - 200) / 2, 300, 200);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.LIME);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        shapeRenderer.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
            int x = Gdx.input.getX();
            int y = Gdx.graphics.getHeight() - Gdx.input.getY();
            if (rectangle.contains(x, y)) {
                dispose();
                game.setScreen(new CircleScreen(game));
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        this.batch.dispose();
        this.shapeRenderer.dispose();
    }
}
