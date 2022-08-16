package io.tomoto.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.SuperMario;

/**
 * 可互动瓦片对象
 *
 * @author Tomoto
 * @version 1.0 2022/5/31 15:23
 * @since 1.0
 */
public abstract class InteractiveTileObject {

    /**
     * 世界
     */
    protected World world;

    /**
     * 瓦片地图
     */
    protected TiledMap map;

    /**
     * 边界范围
     */
    protected Rectangle bounds;

    /**
     * 碰撞体
     */
    protected Body body;

    /**
     * 形状装配器
     */
    protected Fixture fixture;

    /**
     * @param world  世界
     * @param map    瓦片地图
     * @param bounds 边界范围
     */
    public InteractiveTileObject(World world, TiledMap map, Rectangle bounds) {
        this.world = world;
        this.map = map;
        this.bounds = bounds;

        BodyDef bodyDef = new BodyDef();
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((bounds.x + bounds.width / 2) / SuperMario.PIXELS_PER_METER,
                (bounds.y + bounds.height / 2) / SuperMario.PIXELS_PER_METER);

        body = world.createBody(bodyDef);

        shape.setAsBox(bounds.width / 2 / SuperMario.PIXELS_PER_METER, bounds.height / 2 / SuperMario.PIXELS_PER_METER);
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
    }

    /**
     * 玩家顶部碰撞事件
     */
    public abstract void onHeadHit();

    /**
     * 设置碰撞筛选蒙版
     *
     * @param categoryBits 碰撞蒙版
     */
    public void setFilterCategory(short categoryBits) {
        Filter filter = new Filter();
        filter.categoryBits = categoryBits;
        fixture.setFilterData(filter);
    }

    /**
     * 获取所在地图单元
     *
     * @return 地图单元
     */
    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("graphics");
        return layer.getCell(
                (int) (body.getPosition().x * SuperMario.PIXELS_PER_METER / 16),
                (int) (body.getPosition().y * SuperMario.PIXELS_PER_METER / 16));
    }
}
