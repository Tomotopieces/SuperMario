package io.tomoto.game.event.brick;

import io.tomoto.game.event.IEventListener;
import io.tomoto.game.sprites.Brick;

/**
 * 顶砖块事件监听器
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 14:25
 * @since 1.0
 */
public class HitBrickEventListener extends IEventListener<HitBrickEvent> {

    public static final IEventListener.IEventHandler<HitBrickEvent> DEFAULT_HANDLER =
            event -> ((Brick) event.getBrick().getUserData()).onHeadHit();

    public HitBrickEventListener() {
        addHandler(DEFAULT_HANDLER);
    }
}
