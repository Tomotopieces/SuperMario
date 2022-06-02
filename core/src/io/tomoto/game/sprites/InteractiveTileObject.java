package io.tomoto.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.SuperMario;

/**
 * Description
 *
 * @author Tomoto
 * @version 1.0 2022/5/31 15:23
 * @since 1.0
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected TiledMapTile tile;
    protected Rectangle bounds;
    protected Body body;
    protected Fixture fixture;

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

    public abstract void onHeadHit();

    public void setFilterCategory(short categoryBits) {
        Filter filter = new Filter();
        filter.categoryBits = categoryBits;
        fixture.setFilterData(filter);
    }
}
