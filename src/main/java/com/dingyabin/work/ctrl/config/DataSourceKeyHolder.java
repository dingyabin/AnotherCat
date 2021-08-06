package com.dingyabin.work.ctrl.config;

import com.dingyabin.work.ctrl.model.DataSourceKey;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:2:08
 */
public class DataSourceKeyHolder {


    private static DataSourceKey  currentKey = null;


    private DataSourceKeyHolder() {
    }

    public static DataSourceKey getKey() {
        return currentKey;
    }

    public static void setKey(DataSourceKey dataSourceKey) {
        currentKey = dataSourceKey;
    }

}
