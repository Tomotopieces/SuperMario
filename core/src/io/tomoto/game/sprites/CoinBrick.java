package io.tomoto.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import io.tomoto.game.utils.FilterBitManager;

/**
 * 金币砖块
 *
 * @author Tomoto
 * @version 1.0 2022/5/31 15:23
 * @since 1.0
 */
public class CoinBrick extends InteractiveTileObject {

    public CoinBrick(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setFilterCategory(FilterBitManager.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "collision");
    }
}
