package io.tomoto.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import io.tomoto.game.event.EventCenter;
import io.tomoto.game.event.brick.HitBrickEvent;
import io.tomoto.game.event.coin.HitCoinBrickEvent;
import io.tomoto.game.skill.Attack;
import io.tomoto.game.sprites.Brick;
import io.tomoto.game.sprites.CoinBrick;
import io.tomoto.game.sprites.Enemy;

/**
 *  世界碰撞事件监听器
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

        // 敌人进入攻击范围事件
        if (fixtureA.getUserData() instanceof Attack && fixtureB.getUserData() instanceof Enemy) {
            addEnemy(fixtureA, fixtureB);
        } else if (fixtureB.getUserData() instanceof Attack && fixtureA.getUserData() instanceof Enemy) {
            addEnemy(fixtureB, fixtureA);
        }

        // 头顶碰撞事件
        if ("head".equals(fixtureA.getUserData())) {
            handleHeadHit(fixtureA, fixtureB);
        } else if ("head".equals(fixtureB.getUserData())) {
            handleHeadHit(fixtureB, fixtureA);
        }
    }

    /**
     * 处理敌人进入攻击范围事件
     *
     * @param attack 攻击范围
     * @param enemy  敌人
     */
    private void addEnemy(Fixture attack, Fixture enemy) {
        ((Attack) attack.getUserData()).sensorEnemyList.add(enemy);
        Gdx.app.log("WorldContactListener", "add enemy");
    }

    /**
     * 处理头部碰撞事件
     *
     * @param head  头
     * @param other 其他
     */
    private void handleHeadHit(Fixture head, Fixture other) {
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
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getFilterData().categoryBits == FilterBitManager.ATTACK_BIT) {
            removeEnemy(fixtureA, fixtureB);
        } else if (fixtureB.getFilterData().categoryBits == FilterBitManager.ATTACK_BIT) {
            removeEnemy(fixtureB, fixtureA);
        }
    }

    /**
     * 处理敌人离开攻击范围事件
     *
     * @param attack 攻击范围
     * @param enemy  敌人
     */
    public void removeEnemy(Fixture attack, Fixture enemy) {
        ((Attack) attack.getUserData()).sensorEnemyList.remove(enemy);
        Gdx.app.log("WorldContactListener", "remove enemy");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}
