package io.tomoto.game.utils;

import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.event.EventCenter;
import io.tomoto.game.event.brick.HitBrickEvent;
import io.tomoto.game.event.coin.HitCoinBrickEvent;
import io.tomoto.game.sprites.Brick;
import io.tomoto.game.sprites.CoinBrick;

/**
 *  世界碰撞时间监听器
 *
 * @author Tomoto
 * @version 1.0 2022/6/1 9:21
 * @since 1.0
 */
public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        Fixture head = null;
        Fixture other = null;
        // 头顶碰撞事件
        if ("head".equals(fixtureA.getUserData())) {
            head = fixtureA;
            other = fixtureB;
        } else if ("head".equals(fixtureB.getUserData())) {
            head = fixtureB;
            other = fixtureA;
        }

        if (head == null) {
            return;
        }

        if (other.getUserData() instanceof Brick) { // 砖块
            HitBrickEvent event = new HitBrickEvent(head, other);
            EventCenter.handleEvent(event);
        }

        if (other.getUserData() instanceof CoinBrick) { // 金币砖块
            HitCoinBrickEvent event = new HitCoinBrickEvent(head, other, GlobalStatus.hud);
            EventCenter.handleEvent(event);
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
