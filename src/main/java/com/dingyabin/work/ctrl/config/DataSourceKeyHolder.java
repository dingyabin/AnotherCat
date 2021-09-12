package com.dingyabin.work.ctrl.config;

import com.dingyabin.work.common.model.DataSourceKey;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:2:08
 */
public class DataSourceKeyHolder {


    private static ThreadLocal<DataSourceKey> currentKeyHolder = new ThreadLocal<>();


    private DataSourceKeyHolder() {
    }

    public static DataSourceKey getKey() {
        return currentKeyHolder.get();
    }

    public static void setKey(DataSourceKey dataSourceKey) {
        currentKeyHolder.set(dataSourceKey);
    }

    public static void remove() {
        currentKeyHolder.remove();
    }

}
