package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Physics {
    private final World world;
    private final Box2DDebugRenderer debugRenderer;
    public final float PPM = 100;

    public Physics() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(new ContList());
        debugRenderer = new Box2DDebugRenderer();
    }

    public Body addObject(RectangleMapObject object) {
        Rectangle rect = object.getRectangle();
        String type = (String) object.getProperties().get("BodyType");
        BodyDef def = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape polygonShape = new PolygonShape();

        switch (type) {
            case "StaticBody":
                def.type = BodyDef.BodyType.StaticBody;
                break;
            case "DynamicBody":
                def.type = BodyDef.BodyType.DynamicBody;
                break;
        }

        def.position.set((rect.x + rect.width / 2) / PPM, (rect.y + rect.height / 2) / PPM);
        def.gravityScale = (float) object.getProperties().get("gravityScale");

        polygonShape.setAsBox(rect.width / 2 / PPM, rect.height / 2 / PPM);
        fdef.shape = polygonShape;
        fdef.friction = 1;
        fdef.density = 1;
        fdef.restitution = 0.1f;
//        fdef.restitution = (float) object.getProperties().get("restitution");

        Body body;
        body = world.createBody(def);
        String name = object.getName();
        body.createFixture(fdef).setUserData(name);
        if (name != null && name.equals("Герой")) {
            polygonShape.setAsBox(rect.width / 3 / PPM, rect.height / 12 / PPM, new Vector2(0, -rect.width / 2 / PPM), 0);
            body.createFixture(fdef).setUserData("Ноги");
            body.setFixedRotation(true);
            body.getFixtureList().get(body.getFixtureList().size - 1).setSensor(true);
        }

        polygonShape.dispose();
        return body;
    }

    public void step() {
        world.step(1 / 60f, 2, 2);
    }

    public void debugDraw(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
    }

    public void dispose() {
        world.dispose();
        debugRenderer.dispose();
    }
}
