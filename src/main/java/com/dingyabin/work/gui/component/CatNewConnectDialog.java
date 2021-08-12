package com.dingyabin.work.gui.component;

import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;

import javax.swing.*;
import java.awt.*;

/**
 * @author dingyabin
 * @date 2021-08-12 15:16
 */
public class CatNewConnectDialog extends JDialog {

    private DataBaseTypeEnum dataBaseType;


    public CatNewConnectDialog(Frame owner, DataBaseTypeEnum dataBaseType, boolean modal) {
        super(owner, modal);
        this.dataBaseType = dataBaseType;
        init();
    }


    public CatNewConnectDialog(Frame owner, DataBaseTypeEnum dataBaseType, String title, boolean modal) {
        super(owner, title, modal);
        this.dataBaseType = dataBaseType;
        init();
    }


    private void init() {
        generateComponent();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
    }


    private void generateComponent() {


    }


}