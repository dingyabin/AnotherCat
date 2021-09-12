package com.dingyabin.work.ctrl.event;

import com.google.common.eventbus.EventBus;

/**
 * @author 丁亚宾
 * Date: 2021/9/12.
 * Time:14:22
 */
public class SystemEventDispatcher {

    private static final EventBus EVENT_BUS = new EventBus(SystemEventDispatcher.class.getSimpleName());


    public static void register(Object object) {
        EVENT_BUS.register(object);
    }


    public static void unRegister(Object object) {
        EVENT_BUS.unregister(object);
    }


    public static void post(Object object) {
        EVENT_BUS.post(object);
    }

}
