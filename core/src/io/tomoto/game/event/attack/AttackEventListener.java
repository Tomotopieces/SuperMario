package io.tomoto.game.event.attack;

import com.badlogic.gdx.physics.box2d.Fixture;
import io.tomoto.game.event.IEventListener;
import io.tomoto.game.skill.Attack;
import io.tomoto.game.sprites.Enemy;

/**
 * 攻击事件监听器
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 20:43
 * @since 1.0
 */
public class AttackEventListener extends IEventListener<AttackEvent> {
    public AttackEventListener() {
        addHandler(event -> {
            Attack attack = event.getAttack();
            for (Fixture enemyBody : attack.sensorEnemyList) {
                Enemy enemy = (Enemy) enemyBody.getUserData();
                enemy.destroy();
            }
        });
    }
}
