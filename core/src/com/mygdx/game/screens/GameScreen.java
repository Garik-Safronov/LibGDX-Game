package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Main;

public class GameScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private Rectangle mapSize;
    private ShapeRenderer shapeRenderer;
    private float step = 6;

    public GameScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        RectangleMapObject cameraRect = (RectangleMapObject) map.getLayers().get("Объекты").getObjects().get("Камера");
        camera.position.x = cameraRect.getRectangle().x;
        camera.position.y = cameraRect.getRectangle().y;

        RectangleMapObject borderRect = (RectangleMapObject) (map.getLayers().get("Объекты").getObjects().get("Граница"));
        mapSize = borderRect.getRectangle();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom += 0.01f;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.E) && camera.zoom > 0) {
            camera.zoom -= 0.01f;
        }


        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mapSize.x < (camera.position.x - 1)) {
            camera.position.x -= step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (mapSize.x + mapSize.width) > (camera.position.x + 1)) {
            camera.position.x += step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.y += step;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= step;
        }

        camera.update();
        ScreenUtils.clear(Color.GRAY);
        batch.setProjectionMatrix(camera.combined);

        mapRenderer.setView(camera);
        mapRenderer.render();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(mapSize.x, mapSize.y, mapSize.width, mapSize.height);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
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
