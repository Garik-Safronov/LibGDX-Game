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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.HeroAnimation;
import com.mygdx.game.Main;
import com.mygdx.game.Physics;

public class GameScreen implements Screen {

    private Main game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private ShapeRenderer shapeRenderer;
    private Physics physics;
    private Body body;
    private float step = 6;
    private final int[] background;
    private final int[] l1;
    private final Rectangle heroRect;
    private HeroAnimation hero;
    private boolean lookRight;


    public GameScreen(Main game) {
        this.game = game;
        batch = new SpriteBatch();
        physics = new Physics();
        shapeRenderer = new ShapeRenderer();
        hero = new HeroAnimation();

        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        background = new int[1];
        background[0] = map.getLayers().getIndex("Фон");
        l1 = new int[2];
        l1[0] = map.getLayers().getIndex("Слой 2");
        l1[1] = map.getLayers().getIndex("Слой 3");

        RectangleMapObject hero = (RectangleMapObject) map.getLayers().get("Сеттинг").getObjects().get("Герой");
        heroRect = hero.getRectangle();
        body = physics.addObject(hero);

        Array<RectangleMapObject> objects = map.getLayers().get("Объекты").getObjects().getByType(RectangleMapObject.class);
        for (RectangleMapObject object : objects) {
            physics.addObject(object);
        }
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

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            body.applyForceToCenter (new Vector2(-100000, 0), true);
            lookRight = false;
            hero.run();
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            body.applyForceToCenter (new Vector2(100000, 0), true);
            lookRight = true;
            hero.run();
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            body.applyForceToCenter (new Vector2(0, 150000), true);
            hero.jump();
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.y -= step;
        } else{
            hero.stay();
        }

        if(hero.getAnimation().getFrame().isFlipX() && !lookRight){
            hero.getAnimation().getFrame().flip(true,false);
        }
        if(!hero.getAnimation().getFrame().isFlipX() && lookRight){
            hero.getAnimation().getFrame().flip(true,false);
        }

        camera.position.x = body.getPosition().x;
        camera.position.y = body.getPosition().y;
        camera.update();

        ScreenUtils.clear(Color.DARK_GRAY);

        mapRenderer.setView(camera);
        mapRenderer.render(background);
        mapRenderer.render(l1);

        batch.setProjectionMatrix(camera.combined);
        heroRect.x = body.getPosition().x - heroRect.width/2;
        heroRect.y = body.getPosition().y - heroRect.height/2;
        batch.begin();
        batch.draw(hero.getAnimation().getFrame(), heroRect.x, heroRect.y, heroRect.width, heroRect.height);
        batch.end();

        physics.step();
        physics.debugDraw(camera);
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
        physics.dispose();
        hero.dispose();
    }
}
