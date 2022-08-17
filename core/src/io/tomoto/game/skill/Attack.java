package io.tomoto.game.skill;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.SuperMario;
import io.tomoto.game.sprites.Mario;
import io.tomoto.game.utils.FilterBitManager;

import java.util.LinkedList;
import java.util.List;

/**
 * 普通攻击
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 16:59
 * @since 1.0
 */
public class Attack extends Sprite {
    public Body range;

    public Mario mario;

    public List<Fixture> sensorEnemyList = new LinkedList<>();

    public Attack(World world, Mario mario) {
        this.mario = mario;

        setBounds(mario.getX(), mario.getY(), mario.getWidth(), mario.getHeight());

        BodyDef bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.gravityScale = 0;
        Vector2 marioPosition = mario.body.getPosition();
        float bodyX = mario.facingRight ?
                marioPosition.x + 10 / SuperMario.PIXELS_PER_METER :
                marioPosition.x - 10 / SuperMario.PIXELS_PER_METER;
        bodyDef.position.set(bodyX, marioPosition.y);

        range = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = FilterBitManager.ATTACK_BIT;
        fixtureDef.filter.maskBits = FilterBitManager.ENEMY_BIT;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 / SuperMario.PIXELS_PER_METER, 5 / SuperMario.PIXELS_PER_METER);
        fixtureDef.shape = shape;

        Fixture fixture = range.createFixture(fixtureDef);
        fixture.setUserData(this);
    }

    /**
     * 切换启用或禁用状态
     */
    public void toggle() {
        range.setActive(!range.isActive());
    }

    public void updatePosition() {
        Vector2 marioPosition = mario.body.getPosition();
        float bodyX = mario.facingRight ?
                marioPosition.x + 12 / SuperMario.PIXELS_PER_METER :
                marioPosition.x - 12 / SuperMario.PIXELS_PER_METER;
        range.setTransform(bodyX, marioPosition.y, range.getAngle());
    }
}
