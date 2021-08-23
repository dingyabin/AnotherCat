package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.common.model.TableSchema;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/20.
 * Time:0:04
 */
public class CatTabPane extends JTabbedPane {

    private final int FIRST_INDEX = 0;

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
        return addTabWithTabComponent(title, null, component, withCloseBtn);
    }



    public void closeAllTab() {
        int count = getTabCount();
        for (int i = count-1; i >= 0; i--) {
            removeTabAt(i);
        }
    }


    private void init() {
        //tab页面设置界面主题
        putClientProperty(StyleId.STYLE_PROPERTY, StyleId.tabbedpane);
        //在第一个tab插入一个CatTableListPanel
        insertTab("表", CatIcons.table, new CatTableListPanel(), null, FIRST_INDEX);
        setSelectedIndex(FIRST_INDEX);
    }



    /**
     * 在新的连接下面刷新列表
     * @param listData 表
     * @param connectConfig 连接
     * @param dataBaseSchema 库
     */
    public void reSetTableListWithNewDataBaseSchema(List<TableSchema> listData, ConnectConfig connectConfig, DataBaseSchema dataBaseSchema) {
        Component component = getComponentAt(FIRST_INDEX);
        if (!(component instanceof CatTableListPanel)) {
            return;
        }
        ((CatTableListPanel) component).reFreshTableListWithNewDataBaseSchema(listData, connectConfig, dataBaseSchema);
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
