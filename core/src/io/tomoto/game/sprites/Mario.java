package io.tomoto.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import io.tomoto.game.SuperMario;
import io.tomoto.game.screens.PlayScreen;
import io.tomoto.game.utils.FilterBitManager;

/**
 * Mario
 *
 * @author Tomoto
 * @version 1.0
 * @since 1.0 2022/5/21 11:13
 */
public class Mario extends Sprite {
    public enum MarioState {FALLING, JUMPING, STANDING, RUNNING}

    public MarioState currentState;
    public MarioState previousState;

    private final World world;
    public Body body;
    private final TextureRegion marioStand;
    private final Animation<TextureRegion> marioRun;
    private final Animation<TextureRegion> marioJump;
    private float stateTimer;
    private boolean facingRight;

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
        for (int i = 4; i < 6; i++) {
            frames.add(new TextureRegion(getTexture(), i * 16, 0, 16, 16));
        }
        marioJump = new Animation<>(0.1f, frames);

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);

        defineMario();
        setBounds(0, 0, 16 / SuperMario.PIXELS_PER_METER, 16 / SuperMario.PIXELS_PER_METER);
        setRegion(marioStand);
    }

    public void update(float delta) {
        setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(delta));
    }

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

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-4 / SuperMario.PIXELS_PER_METER, 6 / SuperMario.PIXELS_PER_METER),
                new Vector2(4 / SuperMario.PIXELS_PER_METER, 6 / SuperMario.PIXELS_PER_METER));
        fixtureDef.shape = head;
        fixtureDef.isSensor = true;

        body.createFixture(fixtureDef).setUserData("head");
    }
}
