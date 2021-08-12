package com.dingyabin.work.gui.component;

import com.dingyabin.work.common.enums.DataBaseTypeEnum;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
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
        setSize(new Dimension(350, 320));
        //不可调大小
        setResizable(false);
        //位置居中设置
        setLocationRelativeTo(null);
        //默认关闭动作
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        //弹出
        setVisible(true);
    }


    private void generateComponent() {
        JPanel jPanel = new JPanel(new GridLayout(5, 2, 25, 20));

        //设置border
        LineBorder lineBorder = new LineBorder(CatColors.CONNECT_WINDOW_BORDER, 5, true);
        jPanel.setBorder(new TitledBorder(lineBorder, dataBaseType.getType(), TitledBorder.CENTER, TitledBorder.TOP, CatFonts.CONNECT_WINDOW_BORDER));

        jPanel.add(new JLabel("连接名：", SwingConstants.RIGHT));
        jPanel.add(new JTextField());


        jPanel.add(new JLabel("主机名或IP地址：", SwingConstants.RIGHT));
        jPanel.add(new JTextField());


        JLabel portLabel = new JLabel("端口：", SwingConstants.RIGHT);
        Box portInputBox = Box.createHorizontalBox();
        portInputBox.add(new JTextField(9));
        portInputBox.add(Box.createHorizontalStrut(50));
        jPanel.add(portLabel);
        jPanel.add(portInputBox);


        jPanel.add(new JLabel("用户名：", SwingConstants.RIGHT));
        jPanel.add(new JTextField());


        jPanel.add(new JLabel("密码：", SwingConstants.RIGHT));
        jPanel.add(new JPasswordField());

        add(jPanel);


        JPanel btPanel = new JPanel();
        btPanel.add(new JButton("确定"));
        btPanel.add(new JButton("取消"));

        add(btPanel, BorderLayout.SOUTH);

//        add(new JLabel(dataBaseType.getType(),dataBaseType.getIcon(),SwingConstants.CENTER), BorderLayout.NORTH);

    }


}