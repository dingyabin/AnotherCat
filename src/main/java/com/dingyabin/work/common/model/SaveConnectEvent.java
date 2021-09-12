package com.dingyabin.work.common.model;

import lombok.Getter;

/**
 * @author 丁亚宾
 * Date: 2021/8/17.
 * Time:0:52
 */
@Getter
public class SaveConnectEvent {

    /**
     * 当前的模式
     */
    private CatNewConModel catNewConModel;

    /**
     * 当前保存的连接信息
     */
    private ConnectConfig savedConnectConfig;


    public SaveConnectEvent(CatNewConModel catNewConModel, ConnectConfig savedConnectConfig) {
        this.catNewConModel = catNewConModel;
        this.savedConnectConfig = savedConnectConfig;
    }


}
