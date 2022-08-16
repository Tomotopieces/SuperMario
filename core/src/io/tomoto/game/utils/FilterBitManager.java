package io.tomoto.game.utils;

/**
 * 碰撞蒙版管理
 *
 * @author Tomoto
 * @version 1.0 2022/6/2 15:22
 * @since 1.0
 */
public class FilterBitManager {

    /**
     * 默认
     */
    public static final short DEFAULT_BIT = 1;

    /**
     * 已销毁
     */
    public static final short DESTROY_BIT = 2;

    /**
     * 马总
     */
    public static final short MARIO_BIT = 4;

    /**
     * 砖块
     */
    public static final short BRICK_BIT = 8;

    /**
     * 金币砖块
     */
    public static final short COIN_BIT = 16;

}
