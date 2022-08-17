package io.tomoto.game.event.coin;

import com.badlogic.gdx.physics.box2d.Fixture;
import io.tomoto.game.event.IEvent;
import io.tomoto.game.scenes.Hud;

/**
 * 顶金币砖块事件
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 14:19
 * @since 1.0
 */
public class HitCoinBrickEvent implements IEvent {

    /**
     * 头
     */
    private final Fixture head;

    /**
     * 金币砖块
     */
    private final Fixture coinBrick;

    /**
     * hud
     */
    private final Hud hud;

    public HitCoinBrickEvent(Fixture head, Fixture coinBrick, Hud hud) {
        this.head = head;
        this.coinBrick = coinBrick;
        this.hud = hud;
    }

    public Fixture getHead() {
        return head;
    }

    public Fixture getCoinBrick() {
        return coinBrick;
    }

    public Hud getHud() {
        return hud;
    }
}
