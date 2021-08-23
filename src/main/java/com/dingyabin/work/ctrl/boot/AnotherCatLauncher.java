package com.dingyabin.work.ctrl.boot;

import com.dingyabin.work.common.model.ConnectConfigManager;
import com.dingyabin.work.gui.component.*;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:21:22
 */
public class AnotherCatLauncher {

    private static final AnotherCatLauncher ANOTHER_CAT_LAUNCHER = new AnotherCatLauncher();

    //单例模式
    public static AnotherCatLauncher getInstance() {
        return AnotherCatLauncher.ANOTHER_CAT_LAUNCHER;
    }

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
    private final CatTabPane tabbedPane = new CatTabPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

    /**
     * 左侧的连接展示页
     */
    private ConnectDisplayAccording connectDisplay = new ConnectDisplayAccording(jf, tabbedPane);


    private AnotherCatLauncher() {
        //注册容器
        ComContextManager.registerMainFrame(jf);
        ComContextManager.registerTabbedPane(tabbedPane);
    }


    /**
     * 组装组件
     *
     */
    public void assembleComponent() {
        intComponent();

        mainSplitPane.setLeftComponent(connectDisplay);
        mainSplitPane.setRightComponent(tabbedPane);

        jf.setJMenuBar(new CatMenuBar());

        jf.add(mainSplitPane);

        GuiUtils.jFrameCommonAction(jf);
    }




    /**
     * 对组件、容器等，做一些初始化操作
     */
    private void intComponent(){
        //窗口logo
        jf.setIconImage(CatIcons.cat.getImage());
        //屏幕大小
        Rectangle bounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        //设置窗口大小
        jf.setPreferredSize(new Dimension((int) bounds.getWidth(), (int) bounds.getHeight()));

        connectDisplay.setPreferredSize(new Dimension(170,1000));
        connectDisplay.setConnectConfigs(ConnectConfigManager.getConnectConfigs());

        //左右分割栏设置一键收起
        mainSplitPane.setOneTouchExpandable(true);
    }



}
