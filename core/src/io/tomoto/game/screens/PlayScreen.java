package io.tomoto.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import io.tomoto.game.SuperMario;
import io.tomoto.game.scenes.Hud;
import io.tomoto.game.sprites.Mario;
import io.tomoto.game.utils.B2WorldCreator;
import io.tomoto.game.utils.WorldContactListener;

/**
 * play screen
 *
 * @author Tomoto
 * @version 1.0
 * @since 1.0 2022/5/16 14:32
 */
public class PlayScreen implements Screen {
    /* game basic */
    public final SuperMario game;
    private final TextureAtlas atlas;
    private final OrthographicCamera gameCamera;
    private final Viewport gameViewport;
    private final Hud hud;

    /* tile map */
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;

    /* box2d */
    private final World world;
    private final Box2DDebugRenderer debugRenderer;

    /* game element */
    private final Mario mario;

    public PlayScreen(SuperMario game) {
        this.game = game;
        atlas = new TextureAtlas("Mario_and_enemies.pack");
        gameCamera = new OrthographicCamera();

        gameViewport = new FitViewport(SuperMario.V_WIDTH / SuperMario.PIXELS_PER_METER, SuperMario.V_HEIGHT / SuperMario.PIXELS_PER_METER, gameCamera);

        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mario1-1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1.0f / SuperMario.PIXELS_PER_METER);

        gameCamera.position.set(gameViewport.getWorldWidth() / 2, gameViewport.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -9.8f), true);
        debugRenderer = new Box2DDebugRenderer();

        B2WorldCreator worldCreator = new B2WorldCreator(world, map);
        worldCreator.createStaticBody("ground");
        worldCreator.createStaticBody("pipes");
        worldCreator.createStaticBody("coins");
        worldCreator.createStaticBody("bricks");

        mario = new Mario(world, this);

        world.setContactListener(new WorldContactListener());
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {
    }

    public void update(float delta) {
        handleInput(delta);

        world.step(1 / 60f, 6, 2);

        mario.update(delta);

        Vector3 position = gameCamera.position;
        position.x += (mario.body.getPosition().x - position.x) * .1f;
//        position.y += (mario.body.getPosition().y / SuperMario.PIXELS_PER_METER - position.y) * .1f;

        gameCamera.update();

        renderer.setView(gameCamera);
    }

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

        renderer.render();

        game.batch.setProjectionMatrix(gameCamera.combined);
        game.batch.begin();
        mario.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        debugRenderer.render(world, gameCamera.combined);
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
        renderer.dispose();
        world.dispose();
        debugRenderer.dispose();
        hud.dispose();
    }
}
