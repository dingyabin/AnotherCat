package com.dingyabin.work.gui.component;

import javax.swing.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/21.
 * Time:19:53
 */
public class ComContextManager {

    private static JFrame jFrame;

    private static CatTabPane tabbedPane;

    public static void registerMainFrame(JFrame jFrame) {
        ComContextManager.jFrame = jFrame;
    }

    public static void registerTabbedPane(CatTabPane tabbedPane) {
        ComContextManager.tabbedPane = tabbedPane;
    }

    public static JFrame getMainFrame() {
        return ComContextManager.jFrame;
    }

    public static CatTabPane getTabbedPane() {
        return ComContextManager.tabbedPane;
    }

}
