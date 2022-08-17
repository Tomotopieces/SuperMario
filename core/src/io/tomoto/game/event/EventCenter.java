package io.tomoto.game.event;

import java.util.HashMap;
import java.util.Map;

/**
 * 事件中心
 *
 * @author Tomoto
 * @version 1.0 2022/8/17 10:48
 * @since 1.0
 */
public final class EventCenter {

    /**
     * 事件-事件监听器 map
     */
    private static final Map<Class<? extends IEvent>, IEventListener<? extends IEvent>> EVENT_TO_LISTENER_MAP; // 日你妈, 为什么Java没有Dependent Type

    static {
        EVENT_TO_LISTENER_MAP = new HashMap<>();
    }

    /**
     * 注册事件监听器
     *
     * @param eventClass 事件类型
     * @param listener   监听器
     */
    public static <E extends IEvent> void register(Class<E> eventClass, IEventListener<E> listener) {
        EVENT_TO_LISTENER_MAP.put(eventClass, listener);
    }

    /**
     * 获取当前注册的事件对应监听器
     *
     * @param eventClass 事件类型
     * @param <E>        事件类型
     * @return 事件监听器
     */
    public static <E extends IEvent> IEventListener<E> getListener(Class<E> eventClass) {
        return (IEventListener<E>) EVENT_TO_LISTENER_MAP.get(eventClass);
    }

    /**
     * 处理时间
     *
     * @param event 事件
     */
    public static <E extends IEvent> void handleEvent(E event) {
        IEventListener<E> listener = (IEventListener<E>) EVENT_TO_LISTENER_MAP.get(event.getClass());
        if (listener != null) {
            listener.handle(event);
        }
    }

    /**
     * 禁用构造器
     */
    private EventCenter() {
    }

}
