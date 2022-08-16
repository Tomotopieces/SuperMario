package io.tomoto.game.utils;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.SuperMario;
import io.tomoto.game.sprites.Brick;
import io.tomoto.game.sprites.Coin;

/**
 * 世界生成工具
 *
 * @author Tomoto
 * @version 1.0 2022/5/31 15:18
 * @since 1.0
 */
public class B2WorldCreator {

    /**
     * 世界
     */
    private final World world;

    /**
     * 瓦片地图
     */
    private final TiledMap map;

    /**
     * @param world 世界
     * @param map   瓦片地图
     */
    public B2WorldCreator(World world, TiledMap map) {
        this.world = world;
        this.map = map;
    }

    /**
     * 初始化
     */
    public void init() {
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

    /**
     * 创建静态碰撞体
     *
     * @param layerName 图层名称
     */
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
