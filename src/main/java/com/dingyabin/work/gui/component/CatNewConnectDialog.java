package com.dingyabin.work.gui.component;

import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.common.utils.CatUtils;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * @author dingyabin
 * @date 2021-08-12 15:16
 */
public class CatNewConnectDialog extends JDialog {

    private DataBaseTypeEnum dataBaseType;

    private ActionListener cancelListener = e -> dispose();

    private ActionListener okListener = e -> {
        dispose();
    };


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
        setSize(new Dimension(350, 330));
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
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 25, 20));

        //设置border
        LineBorder lineBorder = new LineBorder(CatColors.CONNECT_WINDOW_BORDER, 5, true);
        inputPanel.setBorder(new TitledBorder(lineBorder, dataBaseType.getType(), TitledBorder.CENTER, TitledBorder.TOP, CatFonts.CONNECT_WINDOW_BORDER));

        inputPanel.add(GuiUtils.createLabel("连接名：", SwingConstants.RIGHT, 14));
        inputPanel.add(new JTextField());

        inputPanel.add(GuiUtils.createLabel("主机名或IP地址：", SwingConstants.RIGHT, 14));
        JTextField hostField = new JTextField();
        inputPanel.add(hostField);

        inputPanel.add(GuiUtils.createLabel("端口：", SwingConstants.RIGHT, 14));
        Box portInputBox = Box.createHorizontalBox();
        JTextField portField = new JTextField();
        portInputBox.add(portField);
        portInputBox.add(Box.createHorizontalStrut(50));
        inputPanel.add(portInputBox);

        inputPanel.add(GuiUtils.createLabel("用户名：", SwingConstants.RIGHT, 14));
        JTextField userNameField = new JTextField();
        inputPanel.add(userNameField);

        inputPanel.add(GuiUtils.createLabel("密码：", SwingConstants.RIGHT, 14));
        JPasswordField pwdField = new JPasswordField();
        inputPanel.add(pwdField);

        add(inputPanel);

///////////////////////////////////////////按钮区域/////////////////////////////////////////////////////////////////
        JPanel btPanel = new JPanel();

        JButton testBtn = FontMethodsImpl.setFontSize(new JButton("测试", CatIcons.test), 15);
        testBtn.addActionListener(e -> {
            String host = hostField.getText();
            String port = portField.getText();
            String userName = userNameField.getText();
            char[] password = pwdField.getPassword();
            //校验参数信息
            if (StringUtils.isAnyBlank(host, port, userName) || ArrayUtils.getLength(password) == 0) {
                GuiUtils.createJoptionPane(inputPanel,  "请填写完整的数据源信息", JOptionPane.DEFAULT_OPTION);
                return;
            }
            //测试是否可以连接成功
            boolean ok = CatUtils.checkNewConnect(dataBaseType, host, port, userName, new String(password));
            GuiUtils.createJoptionPane(inputPanel, ok ? "恭喜，连接成功" : "连接失败", JOptionPane.DEFAULT_OPTION);
        });
        btPanel.add(testBtn);

        btPanel.add(Box.createHorizontalStrut(30));

        JButton okBtn = FontMethodsImpl.setFontSize(new JButton("确定", CatIcons.ok), 15);
        okBtn.addActionListener(okListener);
        btPanel.add(okBtn);

        JButton cancelBtn = FontMethodsImpl.setFontSize(new JButton("取消", CatIcons.cancel), 15);
        cancelBtn.addActionListener(cancelListener);
        btPanel.add(cancelBtn);

        add(btPanel, BorderLayout.SOUTH);
    }


}