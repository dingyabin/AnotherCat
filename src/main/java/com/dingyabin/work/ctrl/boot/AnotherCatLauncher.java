package com.dingyabin.work.ctrl.boot;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.model.ConnectConfigManager;
import com.dingyabin.work.ctrl.adapter.CatAdapterService;
import com.dingyabin.work.gui.component.CatIcons;
import com.dingyabin.work.gui.component.CatMenuBar;
import com.dingyabin.work.gui.component.ConnectDisplayAccording;
import com.dingyabin.work.gui.utils.GuiUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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

    @Resource
    private CatAdapterService catAdapterService;

    /**
     * 主窗口
     */
    private final JFrame jf = new JFrame("AnotherCat");

    /**
     * 主分界SplitPane
     */
    private final JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
    /**
     * 右侧的tab页面
     */
    private final JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

    /**
     * 菜单栏
     */
    private final CatMenuBar catMenuBar = new CatMenuBar(tabbedPane, jf);

    /**
     * 左侧的tree页
     */
    private ConnectDisplayAccording connectDisplay = new ConnectDisplayAccording(jf, tabbedPane);


    /**
     * 组装组件
     *
     */
    public void assembleComponent() {
        intComponent();

        mainSplitPane.setLeftComponent(connectDisplay);
        mainSplitPane.setRightComponent(tabbedPane);

        jf.setJMenuBar(catMenuBar);

        jf.add(mainSplitPane);

        GuiUtils.jFrameCommonAction(jf);
    }




    /**
     * 对组件、容器等，做一些初始化操作
     */
    private void intComponent(){
        //窗口logo
        jf.setIconImage(CatIcons.cat.getImage());
        //大小
        jf.setPreferredSize(new Dimension(1300,800));

        connectDisplay.setCatAdapterService(catAdapterService);
        connectDisplay.setPreferredSize(new Dimension(170,1000));
        connectDisplay.setConnectConfigs(ConnectConfigManager.getConnectConfigs());

        //左右分割栏设置一键收起
        mainSplitPane.setOneTouchExpandable(true);

        //tab页面设置界面主题
        tabbedPane.putClientProperty (StyleId.STYLE_PROPERTY, StyleId.tabbedpane);
    }



}
