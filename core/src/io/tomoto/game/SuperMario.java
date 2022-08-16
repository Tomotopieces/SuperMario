package io.tomoto.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        setScreen(new PlayScreen(this));
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
