package com.dingyabin.work.gui.componet;

import com.dingyabin.work.ctrl.model.ConnectConfig;

import javax.swing.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:17:16
 */
public class CatComBox<E> extends JComboBox<E> {


    private ConnectConfig connectConfig;



    public ConnectConfig getConnectConfig() {
        return connectConfig;
    }


    public void setConnectConfig(ConnectConfig connectConfig) {
        this.connectConfig = connectConfig;
    }


}
