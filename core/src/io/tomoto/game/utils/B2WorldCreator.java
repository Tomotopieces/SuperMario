package io.tomoto.game.utils;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.SuperMario;
import io.tomoto.game.sprites.Brick;
import io.tomoto.game.sprites.Coin;

/**
 * Description
 *
 * @author Tomoto
 * @version 1.0 2022/5/31 15:18
 * @since 1.0
 */
public class B2WorldCreator {
    private final World world;
    private final TiledMap map;

    public B2WorldCreator(World world, TiledMap map) {
        this.world = world;
        this.map = map;

        createStaticBody("ground");
        createStaticBody("pipes");

        for (RectangleMapObject object :
                map.getLayers().get("bricks").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            new Brick(world, map, rectangle);
        }

        for (RectangleMapObject object :
                map.getLayers().get("coins").getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            new Coin(world, map, rectangle);
        }
    }

    public void createStaticBody(String layerName) {
        BodyDef bodyDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fixtureDef = new FixtureDef();
        Body body;

        for (RectangleMapObject object :
                map.getLayers().get(layerName).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rectangle = object.getRectangle();

            bodyDef.type = BodyDef.BodyType.StaticBody;
            bodyDef.position.set((rectangle.x + rectangle.width / 2) / SuperMario.PIXELS_PER_METER,
                    (rectangle.y + rectangle.height / 2) / SuperMario.PIXELS_PER_METER);
            body = world.createBody(bodyDef);

            shape.setAsBox(rectangle.width / 2 / SuperMario.PIXELS_PER_METER,
                    rectangle.height / 2 / SuperMario.PIXELS_PER_METER);
            fixtureDef.shape = shape;
            body.createFixture(fixtureDef);
        }
    }
}
