package io.tomoto.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;
import io.tomoto.game.SuperMario;
import io.tomoto.game.utils.FilterBitManager;

/**
 * Description
 *
 * @author Tomoto
 * @version 1.0 2022/5/31 15:23
 * @since 1.0
 */
public class Brick extends InteractiveTileObject{
    public Brick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setFilterCategory(FilterBitManager.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Filter filter1 = fixture.getFilterData();
        Gdx.app.log("Brick collision", "Before new category, category: " + filter1.categoryBits + ", mask: " + filter1.maskBits);
        setFilterCategory(FilterBitManager.DESTROY_BIT);
        Filter filter2 = fixture.getFilterData();
        Gdx.app.log("Brick collision", "After new category, category: " + filter2.categoryBits + ", mask: " + filter2.maskBits);
    }
}
