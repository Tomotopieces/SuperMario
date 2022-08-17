package io.tomoto.game.event;

import java.util.LinkedList;
import java.util.List;

/**
 * 事件监听器
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 10:34
 * @since 1.0
 */
public abstract class IEventListener<E extends IEvent> {

    /**
     * 处理器表
     */
    private final List<IEventHandler<E>> handlerList = new LinkedList<>();

    /**
     * 处理事件
     *
     * @param event 事件
     */
    public void handle(E event) {
        handlerList.forEach(handler -> handler.invoke(event));
    }

    /**
     * 添加处理器
     *
     * @param handler 处理器
     * @return 成功与否
     */
    public boolean addHandler(IEventHandler<E> handler) {
        return handlerList.add(handler);
    }

    /**
     * 移除处理器
     *
     * @param handler 处理器
     * @return 成功与否
     */
    public boolean removeHandler(IEventHandler<E> handler) {
        return handlerList.remove(handler);
    }

    /**
     * 清空处理器表
     */
    public void clearHandler() {
        handlerList.clear();
    }


    /**
     * 事件处理器
     *
     * @author Tomoto
     * @version 1.0 2022/8/17 10:36
     * @since 1.0
     */
    public interface IEventHandler<E extends IEvent> {

        /**
         * 处理事件
         *
         * @param event 事件
         */
        void invoke(E event);

    }

}
