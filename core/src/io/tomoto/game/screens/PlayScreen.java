package io.tomoto.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.tomoto.game.SuperMario;
import io.tomoto.game.scenes.Hud;
import io.tomoto.game.sprites.Mario;
import io.tomoto.game.utils.B2WorldCreator;
import io.tomoto.game.utils.WorldContactListener;

/**
 * 游戏界面
 *
 * @author Tomoto
 * @version 1.0
 * @since 1.0 2022/5/16 14:32
 */
public class PlayScreen implements Screen {

    /* 游戏组件 */

    /**
     * game
     */
    public SuperMario game;

    /**
     * 动画拆分
     */
    public TextureAtlas atlas;

    /**
     * 主相机
     */
    public OrthographicCamera gameCamera;

    /**
     * 游戏视口
     */
    public Viewport gameViewport;

    /**
     * hud
     */
    public Hud hud;

    /* 瓦片地图 */

    /**
     * 地图加载器
     */
    public TmxMapLoader mapLoader;

    /**
     * 瓦片地图
     */
    public TiledMap map;

    /**
     * 地图绘制器
     */
    public OrthogonalTiledMapRenderer mapRenderer;

    /* box2d */

    /**
     * 世界
     */
    public World world;

    /**
     * 碰撞边框绘制器
     */
    public Box2DDebugRenderer debugRenderer;

    /* 游戏元素 */

    /**
     * 马总
     */
    public Mario mario;

    /**
     * @param game game
     */
    public PlayScreen(SuperMario game) {
        this.game = game;
        atlas = new TextureAtlas("Mario_and_enemies.pack");
        gameCamera = new OrthographicCamera();

        gameViewport = new FitViewport(
                SuperMario.V_WIDTH / SuperMario.PIXELS_PER_METER,
                SuperMario.V_HEIGHT / SuperMario.PIXELS_PER_METER, gameCamera);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mario1-1.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1.0f / SuperMario.PIXELS_PER_METER);

        gameCamera.position.set(gameViewport.getWorldWidth() / 2, gameViewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        new B2WorldCreator(world, map).init();

        mario = new Mario(world, this);

        world.setContactListener(new WorldContactListener());
    }

    /**
     * 获取动画拆分
     *
     * @return 动画拆分
     */
    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {
    }

    /**
     * 数据逻辑更新
     *
     * @param delta 时间增量
     */
    public void update(float delta) {
        handleInput(delta);

        world.step(1 / 60f, 6, 2);

        mario.update(delta);
        hud.update(delta);

        Vector3 position = gameCamera.position;
        position.x += (mario.body.getPosition().x - position.x) * .1f;
        position.y += (mario.body.getPosition().y - position.y) * .1f;

        gameCamera.update();

        mapRenderer.setView(gameCamera);
    }

    /**
     * 输入处理
     *
     * @param delta 时间增量
     */
    private void handleInput(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            mario.body.applyLinearImpulse(new Vector2(0, 4f), mario.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.body.getLinearVelocity().x <= 2f) {
            mario.body.applyLinearImpulse(new Vector2(.1f, 0), mario.body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.body.getLinearVelocity().x >= -2f) {
            mario.body.applyLinearImpulse(new Vector2(-.1f, 0), mario.body.getWorldCenter(), true);
        }
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderTexture();

        debugRenderer.render(world, gameCamera.combined);
    }

    /**
     * 画面贴图更新
     */
    private void renderTexture() {
        mapRenderer.render();

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        mario.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }
}
