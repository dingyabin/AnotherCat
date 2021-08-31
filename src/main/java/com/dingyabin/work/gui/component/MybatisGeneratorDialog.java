package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/29.
 * Time:22:15
 */
public class MybatisGeneratorDialog extends JDialog {


    private JPanel projectInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,10,5));

    private JTextField projectInputField = GuiUtils.createTextField(50, StyleId.textfieldNoFocus);

    private JButton projectInputBtn = GuiUtils.createButton("打开目录...", CatIcons.open, StyleId.button);

    private LineBorder lineBorder = new LineBorder(CatColors.GENERATOR_WINDOW_BORDER, 4, true);


    public MybatisGeneratorDialog() {
        super(ComContextManager.getMainFrame());
        init();
    }




    private void init(){
        generateComponent();
        //始终在最上面
        setAlwaysOnTop(true);
        //大小设置
        setSize(new Dimension(750, 850));
        //可调大小
        setResizable(true);
        //位置居中设置
        setLocationRelativeTo(null);
        //默认关闭动作
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }


    private void generateComponent(){
        //设置border
        projectInputPanel.setBorder(new TitledBorder(lineBorder, "工程目录", TitledBorder.CENTER, TitledBorder.TOP, CatFonts.GENERATOR_WINDOW_BORDER));

        projectInputPanel.add(GuiUtils.createLabel("选择工程目录：", SwingConstants.RIGHT, 15));
        projectInputPanel.add(projectInputField);
        projectInputPanel.add(projectInputBtn);


        add(projectInputPanel);

    }




    public void showSelf(){
        //弹出
        setVisible(true);
    }


}
