package com.dingyabin.work.common.model;

import com.dingyabin.work.gui.component.CatNewConnectDialog;
import lombok.Getter;

/**
 * @author 丁亚宾
 * Date: 2021/8/17.
 * Time:0:52
 */
@Getter
public class SaveConnectEvent {

    private CatNewConnectDialog source;

    /**
     * 当前的模式
     */
    private CatNewConModel catNewConModel;

    /**
     * 当前保存的连接信息
     */
    private ConnectConfig savedConnectConfig;


    public SaveConnectEvent(CatNewConModel catNewConModel, ConnectConfig savedConnectConfig, CatNewConnectDialog catNewConnectDialog) {
        this.catNewConModel = catNewConModel;
        this.savedConnectConfig = savedConnectConfig;
        this.source = catNewConnectDialog;
    }


}
