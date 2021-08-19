package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;

import javax.swing.*;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/20.
 * Time:0:04
 */
public class CatTabPane extends JTabbedPane {


    public CatTabPane() {
        init();
    }

    public CatTabPane(int tabPlacement) {
        super(tabPlacement);
        init();
    }

    public CatTabPane(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
        init();
    }


    public int addTabWithTabComponent(String title, Icon icon, Component component, boolean withCloseBtn) {
        int newTabIndex = getTabCount();
        super.addTab(title, icon, component);
        if (withCloseBtn) {
            setTabComponentAt(newTabIndex, createTabBarComponent(title, icon));
        }
        setSelectedIndex(newTabIndex);
        return newTabIndex;
    }



    public int addTabWithTabComponent(String title, Component component, boolean withCloseBtn) {
        int newTabIndex = getTabCount();
        super.addTab(title, component);
        if (withCloseBtn) {
            setTabComponentAt(newTabIndex, createTabBarComponent(title, null));
        }
        setSelectedIndex(newTabIndex);
        return newTabIndex;
    }




    public int addTabWithFirstIndex(String title, Icon icon, Component component, boolean withCloseBtn) {
        int firstIndex = 0;
        insertTab(title, icon, component, null, firstIndex);
        if (withCloseBtn) {
            setTabComponentAt(firstIndex, createTabBarComponent(title, icon));
        }
        setSelectedIndex(firstIndex);
        return firstIndex;
    }



    private void init() {
        //tab页面设置界面主题
        putClientProperty (StyleId.STYLE_PROPERTY, StyleId.tabbedpane);
    }



    private Component createTabBarComponent(String title, Icon icon) {
        Box tabHorizontalBox = Box.createHorizontalBox();
        //关闭 按钮
        JButton closeBtn = new JButton(CatIcons.cross);
        closeBtn.addActionListener(e -> removeTabAt(indexOfTabComponent(tabHorizontalBox)));
        closeBtn.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.buttonIconHover);
        closeBtn.setPreferredSize(new Dimension(20, 20));
        //添加标签
        tabHorizontalBox.add(new JLabel(title, icon, SwingConstants.CENTER));
        //添加关闭按钮
        tabHorizontalBox.add(closeBtn);
        return tabHorizontalBox;
    }


}
