package io.tomoto.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.SuperMario;
import io.tomoto.game.utils.FilterBitManager;

/**
 * 敌人
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 21:00
 * @since 1.0
 */
public class Enemy {

    /**
     * 世界
     */
    private final World world;

    /**
     * 碰撞体
     */
    public Body body;

    /**
     * 形状装配器
     */
    public Fixture fixture;

    public Enemy(World world) {
        this.world = world;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(128 / SuperMario.PIXELS_PER_METER, 64 / SuperMario.PIXELS_PER_METER);

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperMario.PIXELS_PER_METER);
        fixtureDef.filter.categoryBits = FilterBitManager.ENEMY_BIT;
        fixtureDef.filter.maskBits = FilterBitManager.DEFAULT_BIT | FilterBitManager.COIN_BIT | FilterBitManager.BRICK_BIT | FilterBitManager.MARIO_BIT | FilterBitManager.ATTACK_BIT;
        fixtureDef.shape = shape;
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    public void destroy() {
        body.destroyFixture(fixture);
        Gdx.app.log("Enemy", "enemy destroyed");
    }
}
