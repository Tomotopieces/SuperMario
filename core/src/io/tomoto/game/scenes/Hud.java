package io.tomoto.game.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.tomoto.game.SuperMario;

/**
 * HUD
 *
 * @author Tomoto
 * @version 1.0
 * @since 1.0 2022/5/16 14:52
 */
public class Hud implements Disposable {

    /* 窗口组件 */

    /**
     * stage
     */
    public Stage stage;

    /**
     * 视口
     */
    public Viewport viewport;

    /* 游戏数据 */

    /**
     * 游戏总剩余时间
     */
    public Integer totalTime;

    /**
     * 时间计算
     */
    public float timeCount;

    /**
     * 分数
     */
    public Integer score;

    /* hud标签 */

    /**
     * 倒计时标签
     */
    public Label countdownLabel;

    /**
     * 分数标签
     */
    public Label scoreLabel;

    /**
     * TIME字样标签
     */
    public Label timeLabel;

    /**
     * 关卡标签
     */
    public Label levelLabel;

    /**
     * WORLD字样标签
     */
    public Label worldLabel;

    /**
     * MARIO字样标签
     */
    public Label marioLabel;

    /**
     * @param spriteBatch 绘制器
     */
    public Hud(SpriteBatch spriteBatch) {
        totalTime = 300;
        timeCount = 0;
        score = 0;

        viewport = new FitViewport(SuperMario.V_WIDTH, SuperMario.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, spriteBatch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", totalTime), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    /**
     * 更新
     *
     * @param delta 时间增量
     */
    public void update(float delta) {
        timeCount += delta;
        if (timeCount >= 1) {
            countdownLabel.setText(String.format("%03d", totalTime - MathUtils.floor(timeCount)));
            timeCount -= MathUtils.floor(timeCount);
        }
    }

    /**
     * 增加分数
     *
     * @param value 积分增加量
     */
    public void addScore(int value) {
        this.score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
