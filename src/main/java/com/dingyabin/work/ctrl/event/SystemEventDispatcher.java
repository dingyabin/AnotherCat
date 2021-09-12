package com.dingyabin.work.ctrl.event;

import com.dingyabin.work.common.listeners.CatActionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/9/12.
 * Time:14:22
 */
public class SystemEventDispatcher {

    private static final Map<Class, List<CatActionListener>> CAT_SYSTEM_LISTENER_MAP = new HashMap<>();



    public static <T> void register(CatActionListener<T> listener) {
        Class<T> listenType = listener.getListenType();
        List<CatActionListener> catSystemListeners = CAT_SYSTEM_LISTENER_MAP.get(listenType);
        if (catSystemListeners == null) {
            catSystemListeners = new ArrayList<>();
        }
        catSystemListeners.add(listener);
        CAT_SYSTEM_LISTENER_MAP.put(listenType, catSystemListeners);
    }


    public static void unRegister(Class event) {
        CAT_SYSTEM_LISTENER_MAP.remove(event);
    }


    @SuppressWarnings("unchecked")
    public static void post(Object event) {
        List<CatActionListener> catSystemListeners = CAT_SYSTEM_LISTENER_MAP.get(event.getClass());
        if (catSystemListeners == null) {
            return;
        }
        for (CatActionListener catSystemListener : catSystemListeners) {
            if (catSystemListener.getListenType() != event.getClass()) {
                continue;
            }
            catSystemListener.process(event);
        }
    }

}
