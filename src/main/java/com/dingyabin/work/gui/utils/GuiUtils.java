package com.dingyabin.work.gui.utils;

import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;

import javax.swing.*;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:22:22
 */
public class GuiUtils {


    /**
     * JFrame的通用操作
     *
     * @param jf JFrame
     */
    public static void jFrameCommonAction(JFrame jf) {
        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }



    /**
     * 带样式的滚动条
     * @param component 需要加滚动条的组件
     * @return 滚动条
     */
    public static JComponent createJScrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless);
        return scrollPane;
    }



    public static Component createTabBarComponent(String title, Icon icon, JTabbedPane tabbedPane, Component content) {
        Box tabBox = Box.createHorizontalBox();
        JLabel jLabel = new JLabel(title, icon, SwingConstants.CENTER);
        final JButton closeBtn = new JButton(Icons.cross);
        closeBtn.addActionListener(e -> {
            tabbedPane.removeTabAt(tabbedPane.indexOfComponent(content));
        });
        closeBtn.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.buttonIconHover);
        closeBtn.setPreferredSize(new Dimension(20, 20));
        tabBox.add(jLabel);
        tabBox.add(closeBtn);
        return tabBox;
    }
}
