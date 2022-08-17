package io.tomoto.game.event.brick;

import com.badlogic.gdx.physics.box2d.Fixture;
import io.tomoto.game.event.IEvent;

/**
 * 顶砖块事件
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 14:15
 * @since 1.0
 */
public class HitBrickEvent implements IEvent {

    /**
     * 头
     */
    private final Fixture head;

    /**
     * 砖块
     */
    private final Fixture brick;

    /**
     * @param head  头
     * @param brick 砖块
     */
    public HitBrickEvent(Fixture head, Fixture brick) {
        this.head = head;
        this.brick = brick;
    }

    public Fixture getHead() {
        return head;
    }

    public Fixture getBrick() {
        return brick;
    }
}
