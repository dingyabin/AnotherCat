package com.dingyabin.work.ctrl.boot;

import com.dingyabin.work.common.model.ConnectConfigManager;
import com.dingyabin.work.ctrl.adapter.CatAdapterService;
import com.dingyabin.work.gui.component.*;
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
    private final CatTabPane tabbedPane = new CatTabPane(SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);

    /**
     * 左侧的连接展示页
     */
    private ConnectDisplayAccording connectDisplay = new ConnectDisplayAccording(jf, tabbedPane);

    /**
     * 菜单栏
     */
    private final CatMenuBar catMenuBar = new CatMenuBar(tabbedPane, jf);



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
        //注册容器
        ComContextManager.registerMainFrame(jf);

        //窗口logo
        jf.setIconImage(CatIcons.cat.getImage());
        //大小
        jf.setPreferredSize(new Dimension(1600,1000));

        connectDisplay.setCatAdapterService(catAdapterService);
        connectDisplay.setPreferredSize(new Dimension(170,1000));
        connectDisplay.setConnectConfigs(ConnectConfigManager.getConnectConfigs());

        //左右分割栏设置一键收起
        mainSplitPane.setOneTouchExpandable(true);
    }



}
