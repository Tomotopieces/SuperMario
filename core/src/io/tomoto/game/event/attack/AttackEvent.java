package io.tomoto.game.event.attack;

import io.tomoto.game.event.IEvent;
import io.tomoto.game.skill.Attack;

/**
 * 攻击事件
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 20:40
 * @since 1.0
 */
public class AttackEvent implements IEvent {
    private final Attack attack;

    public AttackEvent(Attack attack) {
        this.attack = attack;
    }

    public Attack getAttack() {
        return attack;
    }
}
