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


    public CatNewConnectDialog(Frame owner, DataBaseTypeEnum dataBaseType, String title, boolean modal) {
        super(owner, title, modal);
        this.dataBaseType = dataBaseType;
        init();
    }


    private void init() {
        generateComponent();
        //始终在最上面
        setAlwaysOnTop(true);
        //大小设置
        setSize(new Dimension(400,400));
        //位置居中设置
        setLocationRelativeTo(null);
        //默认关闭动作
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //弹出
        setVisible(true);
    }


    private void generateComponent() {
        JPanel inputPanel = new JPanel(new GridLayout(4,2));
        inputPanel.add(new JLabel("11"));
        inputPanel.add(new JLabel("22"));

        add(inputPanel);
    }


}