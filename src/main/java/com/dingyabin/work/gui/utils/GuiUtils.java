package com.dingyabin.work.gui.utils;

import com.alee.laf.label.WebLabel;
import com.alee.managers.icon.Icons;
import com.alee.managers.notification.NotificationManager;
import com.alee.managers.style.StyleId;
import com.dingyabin.work.gui.component.CatIcons;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.net.URI;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:22:22
 */
@Slf4j
public class GuiUtils {


    /**
     * 使用默认浏览器打开链接
     * @param url url
     */
    public static void openBrowser(String url) {
        try {
            if (!Desktop.isDesktopSupported()) {
                NotificationManager.showNotification("设备暂不支持自动打开!", CatIcons.cry);
                return;
            }
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(URI.create(url));
        } catch (Exception ex) {
            log.error("使用默认浏览器打开链接异常,url={}", url, ex);
        }
    }


    /**
     * JFrame的通用操作
     *
     * @param jf JFrame
     */
    public static void jFrameCommonAction(JFrame jf) {
        jf.pack();
        //关闭
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //显示
        jf.setVisible(true);

    }


    /**
     * 带样式的滚动条
     *
     * @param component 需要加滚动条的组件
     * @return 滚动条
     */
    public static JComponent createJscrollPane(Component component) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless);
        return scrollPane;
    }



    /**
     * @param title 标题
     * @param icon 图标
     * @param tabbedPane tabbedPane
     * @param content 当前tab页面放置的组件
     * @return tab面板
     */
    public static Component createTabBarComponent(String title, Icon icon, JTabbedPane tabbedPane, Component content) {
        Box tabBox = Box.createHorizontalBox();
        JButton closeBtn = new JButton(CatIcons.cross);
        closeBtn.addActionListener(e -> tabbedPane.removeTabAt(tabbedPane.indexOfComponent(content)));
        closeBtn.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.buttonIconHover);
        closeBtn.setPreferredSize(new Dimension(20, 20));
        tabBox.add(new JLabel(title, icon, SwingConstants.CENTER));
        tabBox.add(closeBtn);
        return tabBox;
    }



    /**
     * 打开一个对话框
     * @param image 标题图标
     * @param width  宽度
     * @param height 高度
     * @param labelStr 内容文字
     * @param centerWithWho 相对于哪个组件居中
     */
    public static void createDialog(Image image, int width, int height , String labelStr, Component centerWithWho) {
        JDialog dialog = new JDialog();
        dialog.getRootPane().putClientProperty(StyleId.STYLE_PROPERTY, StyleId.dialog);
        dialog.setIconImage(image);
        dialog.add(new WebLabel(StyleId.label, labelStr, WebLabel.CENTER).setFontSize(14));
        dialog.setSize(width, height);
        //居中显示
        dialog.setLocationRelativeTo(centerWithWho);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //不可调大小
        dialog.setResizable(false);
        dialog.setVisible(true);
    }


    /**
     * 展示
     * @param container
     * @param message
     * @return
     */
    public static boolean createJoptionPane(Component container, String message) {
        int opt = JOptionPane.showConfirmDialog(container, message, "确认信息", JOptionPane.YES_NO_OPTION);
        return opt == JOptionPane.YES_OPTION;
    }


}
