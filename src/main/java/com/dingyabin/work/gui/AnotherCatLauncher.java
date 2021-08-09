package com.dingyabin.work.gui;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:21:22
 */
@Lazy
@Component
public class AnotherCatLauncher {

    /**
     * 主窗口
     */
    private final JFrame jf = new JFrame("AnotherCat");

    /**
     * 主分界panel
     */
    private final JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
    /**
     * 右侧的tab页面
     */
    private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    /**
     * 左侧的tree页
     */
    private final Box leftTreeBox = Box.createVerticalBox();


    /**
     * 组装组件
     *
     */
    public void assembleComponent() {
        intComponent();



        mainSplitPane.setLeftComponent(leftTreeBox);
        mainSplitPane.setRightComponent(tabbedPane);


        jf.add(mainSplitPane);

        GuiUtils.jFrameCommonAction(jf);
    }




    /**
     * 对组件、容器等，做一些初始化操作
     */
    private void intComponent(){

        jf.setPreferredSize(new Dimension(1600,1000));

        leftTreeBox.setPreferredSize(new Dimension(300,1000));

        //左右分割栏设置一键收起
        mainSplitPane.setOneTouchExpandable(true);

        //tab页面设置界面主题
        tabbedPane.putClientProperty (StyleId.STYLE_PROPERTY, StyleId.tabbedpane);
    }



}
