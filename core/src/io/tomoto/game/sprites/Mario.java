package io.tomoto.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import io.tomoto.game.SuperMario;
import io.tomoto.game.screens.PlayScreen;
import io.tomoto.game.utils.FilterBitManager;

/**
 * 马总
 *
 * @author Tomoto
 * @version 1.0
 * @since 1.0 2022/5/21 11:13
 */
public class Mario extends Sprite {

    /**
     * 状态
     */
    public enum MarioState {FALLING, JUMPING, STANDING, RUNNING}

    /**
     * 当前状态
     */
    public MarioState currentState;

    /**
     * 前一状态
     */
    public MarioState previousState;

    /**
     * 世界
     */
    private final World world;

    /**
     * 碰撞体
     */
    public Body body;

    /**
     * 闲置贴图
     */
    private final TextureRegion marioStand;

    /**
     * 跑步动画
     */
    private final Animation<TextureRegion> marioRun;

    /**
     * 跳跃动画
     */
    private final Animation<TextureRegion> marioJump;

    /**
     * 状态时间
     */
    private float stateTimer;

    /**
     * 是否面向右边
     */
    private boolean facingRight;

    /**
     * @param world  世界
     * @param screen 游戏窗口
     */
    public Mario(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        previousState = currentState = MarioState.STANDING;
        stateTimer = 0;
        facingRight = true;

        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i < 4; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        marioRun = new Animation<>(0.1f, frames);
        frames.clear();
        for (int i = 5; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        marioJump = new Animation<>(0.1f, frames);

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);

        defineMario();
        setBounds(0, 0, 16 / SuperMario.PIXELS_PER_METER, 16 / SuperMario.PIXELS_PER_METER);
        setRegion(marioStand);
    }

    /**
     * 逻辑数据更新
     *
     * @param delta 时间增量
     */
    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
    }

    /**
     * 获取当前动画帧贴图
     *
     * @param delta 时间增量
     * @return 动画帧贴图
     */
    private TextureRegion getFrame(float delta) {
        currentState = getState();

        TextureRegion region;
        switch (currentState) {
            case JUMPING:
                region = marioJump.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioStand;
                break;
        }

        if ((body.getLinearVelocity().x < 0 || !facingRight) && !region.isFlipX()) {
            region.flip(true, false);
            facingRight = false;
        } else if ((body.getLinearVelocity().x > 0 || facingRight) && region.isFlipX()) {
            region.flip(true, false);
            facingRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + delta : 0;
        previousState = currentState;
        return region;
    }

    /**
     * 通过物理量判断当前状态
     *
     * @return 状态
     */
    private MarioState getState() {
        if (body.getLinearVelocity().y > 0 || (body.getLinearVelocity().y < 0 && previousState == MarioState.JUMPING)) {
            return MarioState.JUMPING;
        } else if (body.getLinearVelocity().y < 0) {
            return MarioState.FALLING;
        } else if (body.getLinearVelocity().x != 0) {
            return MarioState.RUNNING;
        } else {
            return MarioState.STANDING;
        }
    }

    /**
     * 生成马总
     */
    private void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(32 / SuperMario.PIXELS_PER_METER, 64 / SuperMario.PIXELS_PER_METER);

        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / SuperMario.PIXELS_PER_METER);
        fixtureDef.filter.categoryBits = FilterBitManager.MARIO_BIT;
        fixtureDef.filter.maskBits = FilterBitManager.DEFAULT_BIT | FilterBitManager.COIN_BIT | FilterBitManager.BRICK_BIT;
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);

        // 头顶的特殊碰撞
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / SuperMario.PIXELS_PER_METER, 6 / SuperMario.PIXELS_PER_METER),
                new Vector2(4 / SuperMario.PIXELS_PER_METER, 6 / SuperMario.PIXELS_PER_METER));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData("head");
    }
}
