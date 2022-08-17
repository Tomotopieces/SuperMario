package io.tomoto.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.tomoto.game.event.EventCenter;
import io.tomoto.game.event.brick.HitBrickEvent;
import io.tomoto.game.event.brick.HitBrickEventListener;
import io.tomoto.game.event.coin.HitCoinBrickEvent;
import io.tomoto.game.event.coin.HitCoinBrickEventListener;
import io.tomoto.game.screens.PlayScreen;

/**
 * 主类
 *
 * @author Tomoto
 * @since 2022/8/15 16:20
 */
public class SuperMario extends Game {

    /**
     * 视口宽度
     */
    public static final int V_WIDTH = 400;

    /**
     * 视口高度
     */
    public static final int V_HEIGHT = 208;

    /**
     * 单元像素数量
     */
    public static final float PIXELS_PER_METER = 100;

    /**
     * 绘制器
     */
    public SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        registerDefaultEvent();
        setScreen(new PlayScreen(this));
    }

    /**
     * 注册默认事件
     */
    private void registerDefaultEvent() {
        EventCenter.register(HitBrickEvent.class, new HitBrickEventListener());
        EventCenter.register(HitCoinBrickEvent.class, new HitCoinBrickEventListener());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
