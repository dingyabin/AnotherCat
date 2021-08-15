package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.ConnectConfigManager;
import com.dingyabin.work.common.utils.CatUtils;
import com.dingyabin.work.gui.utils.GuiUtils;
import com.sun.istack.internal.NotNull;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 新建连接对话框
 *
 * @author dingyabin
 * @date 2021-08-12 15:16
 */
public class CatNewConnectDialog extends JDialog implements ActionListener {

    /**
     * 新建-保存模式
     */
    public static final int SAVE_MODE = 0;

    /**
     * 编辑模式
     */
    public static final int EDIT_MODE = 1;


    private DataBaseTypeEnum dataBaseType;

    private int mode;

    private Consumer<ConnectConfig> okBtnCallBack;

    private ConnectConfig oldConnectConfig;

    private JLabel conNameLabel = GuiUtils.createLabel("连接名：", SwingConstants.RIGHT, 14);

    private JTextField conNameField = new JTextField();

    private JLabel hosLabel = GuiUtils.createLabel("主机名或IP地址：", SwingConstants.RIGHT, 14);

    private JTextField hostField = new JTextField();

    private JLabel portLabel = GuiUtils.createLabel("端口：", SwingConstants.RIGHT, 14);

    private JTextField portField = new JTextField();

    private JLabel userNameLabel = GuiUtils.createLabel("用户名：", SwingConstants.RIGHT, 14);

    private JTextField userNameField = new JTextField();

    private JLabel pwdLabel = GuiUtils.createLabel("密码：", SwingConstants.RIGHT, 14);

    private JPasswordField pwdField = new JPasswordField();

    //测试按钮，测试连接是否可用
    private JButton checkBtn = FontMethodsImpl.setFontSize(new JButton("测试", CatIcons.test), 15);

    //确定按钮，保存连接
    private JButton okBtn = FontMethodsImpl.setFontSize(new JButton("确定", CatIcons.ok), 15);

    //取消按钮，关闭窗口
    private JButton cancelBtn = FontMethodsImpl.setFontSize(new JButton("取消", CatIcons.cancel), 15);

    private JPanel inputPanel = new JPanel(new GridLayout(5, 2, 25, 20));

    //进度条
    private JProgressBar progressBar = new JProgressBar(JProgressBar.HORIZONTAL);


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
    }


    private void generateComponent() {

        //设置border
        LineBorder lineBorder = new LineBorder(CatColors.CONNECT_WINDOW_BORDER, 5, true);
        inputPanel.setBorder(new TitledBorder(lineBorder, dataBaseType.getType(), TitledBorder.CENTER, TitledBorder.TOP, CatFonts.CONNECT_WINDOW_BORDER));

        inputPanel.add(conNameLabel);
        inputPanel.add(conNameField);

        inputPanel.add(hosLabel);
        inputPanel.add(hostField);

        inputPanel.add(portLabel);
        Box portInputBox = Box.createHorizontalBox();
        portInputBox.add(portField);
        portInputBox.add(Box.createHorizontalStrut(50));
        inputPanel.add(portInputBox);

        inputPanel.add(userNameLabel);
        inputPanel.add(userNameField);

        inputPanel.add(pwdLabel);
        inputPanel.add(pwdField);

        add(inputPanel);

///////////////////////////////////////////按钮区域/////////////////////////////////////////////////////////////////
        JPanel btPanel = new JPanel();

        //组装测试按钮
        checkBtn.addActionListener(this);
        btPanel.add(checkBtn);

        //进度条设置
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);
        progressBar.setString(StringUtils.EMPTY);
        FontMethodsImpl.setFontSize(progressBar,15);
        progressBar.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.progressbar);

        //组装进度条
        btPanel.add(progressBar);

        okBtn.addActionListener(this);
        btPanel.add(okBtn);

        cancelBtn.addActionListener(this);
        btPanel.add(cancelBtn);

        add(btPanel, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == checkBtn) {
            checkConnect();
            return;
        }
        if (source == okBtn) {
            saveConnect();
            return;
        }
        if (source == cancelBtn) {
            dispose();
        }
    }




    /**
     * 测试连接
     */
    private void checkConnect() {
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        char[] password = pwdField.getPassword();
        //校验参数信息
        if (StringUtils.isAnyBlank(host, port, userName) || ArrayUtils.getLength(password) == 0) {
            GuiUtils.createOptionPane(inputPanel, "请填写完整的数据源信息", JOptionPane.DEFAULT_OPTION);
            return;
        }
        //测试按钮置灰
        checkBtn.setEnabled(false);
        progressBar.setString("连接中...");
        //测试是否可以连接成功
        CatUtils.checkNewConnect(dataBaseType, host, port, userName, new String(password), ok -> {
            checkBtn.setEnabled(true);
            progressBar.setForeground(ok ? CatColors.CONNECT_OK : CatColors.CONNECT_ERR);
            progressBar.setString(ok ? "连接成功" : "连接失败...");
            GuiUtils.createOptionPane(inputPanel, ok ? "恭喜，连接成功" : "连接失败...", JOptionPane.DEFAULT_OPTION);
        });
    }


    public Consumer<ConnectConfig> getOkBtnCallBack() {
        return okBtnCallBack;
    }



    public void setOkBtnCallBack(Consumer<ConnectConfig> okBtnCallBack) {
        this.okBtnCallBack = okBtnCallBack;
    }


    public ConnectConfig getOldConnectConfig() {
        return oldConnectConfig;
    }



    /**
     * 保存连接
     */
    private void saveConnect() {
        String conName = conNameField.getText();
        String host = hostField.getText();
        String port = portField.getText();
        String userName = userNameField.getText();
        char[] password = pwdField.getPassword();
        //校验参数信息
        if (StringUtils.isAnyBlank(conName, host, port, userName) || ArrayUtils.getLength(password) == 0) {
            GuiUtils.createOptionPane(inputPanel, "请填写完整的数据源信息!", JOptionPane.DEFAULT_OPTION);
            return;
        }
        String pwd = new String(password);

        ConnectConfig curConnect = new ConnectConfig(conName, dataBaseType.getType(), host, port, userName, pwd);

        boolean saveRet;
        //编辑模式，进行修改操作, 否则才是新增操作
        if (mode == EDIT_MODE) {
            saveRet = oldConnectConfig.equals(curConnect) || ConnectConfigManager.updateConnectConfig(oldConnectConfig, curConnect);
        } else {
            saveRet = ConnectConfigManager.addConnectConfig(curConnect);
        }
        //编辑模式不弹框
        if (mode == SAVE_MODE) {
            //弹框
            GuiUtils.createOptionPane(inputPanel, saveRet ? "保存成功！" : "情况不妙，失败了！", JOptionPane.DEFAULT_OPTION);
        }
        //如果有回调的话,执行回调
        if (saveRet && this.okBtnCallBack != null) {
            okBtnCallBack.accept(curConnect);
        }
        //如果是编辑模式的话，就关闭
        if (mode == EDIT_MODE) {
            dispose();
        }
    }



    public CatNewConnectDialog showSelf(){
        //弹出
        setVisible(true);

        return this;
    }


    public void editMode(@NotNull ConnectConfig oldConfig) {
        this.mode = EDIT_MODE;
        conNameField.setText(oldConfig.getName());
        hostField.setText(oldConfig.getHost());
        portField.setText(oldConfig.getPort());
        userNameField.setText(oldConfig.getUserName());
        pwdField.setText(oldConfig.getPwd());
        this.oldConnectConfig = oldConfig;
    }



    public void enAbleInput( boolean enable){
        conNameField.setEditable(enable);
        hostField.setEditable(enable);
        portField.setEditable(enable);
        userNameField.setEditable(enable);
        pwdField.setEditable(enable);
        checkBtn.setEnabled(false);
        okBtn.setEnabled(false);
    }


}