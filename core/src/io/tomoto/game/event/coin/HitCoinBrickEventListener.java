package io.tomoto.game.event.coin;

import io.tomoto.game.event.IEventListener;
import io.tomoto.game.sprites.CoinBrick;

/**
 * 顶金币砖块事件监听器
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 14:28
 * @since 1.0
 */
public class HitCoinBrickEventListener extends IEventListener<HitCoinBrickEvent> {

    public static final IEventListener.IEventHandler<HitCoinBrickEvent> DEFAULT_HANDLER =
            event -> {
                ((CoinBrick) event.getCoinBrick().getUserData()).onHeadHit();
                event.getHud().addScore(100);
            };

    public HitCoinBrickEventListener() {
        addHandler(DEFAULT_HANDLER);
    }

}
