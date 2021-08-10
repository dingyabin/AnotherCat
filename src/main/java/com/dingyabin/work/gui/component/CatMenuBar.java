package com.dingyabin.work.gui.component;

import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.MenuBarLayout;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;
import com.alee.utils.CoreSwingUtils;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:23:30
 */
public class CatMenuBar {


    private final JMenuBar jMenuBar = new JMenuBar();

    private final JMenu fileMenu = new JMenu("文件");

    private final JMenu watchMenu = new JMenu("查看");

    private final JMenu favMenu = new JMenu("收藏夹");

    private final JMenu toolsMenu = new JMenu("工具");

    private final JMenu windowsMenu = new JMenu("窗口");

    private final JMenu helpMenu = new JMenu("帮助");


    public CatMenuBar() {
        init();
    }


    public JMenuBar getJMenuBar() {
        return jMenuBar;
    }


    private void init() {
        //设置JMenu间距
        MenuBarLayout layout = (MenuBarLayout) jMenuBar.getLayout();
        layout.setSpacing(20);

        //设置图标
        fileMenu.setIcon(Icons.dir);
        watchMenu.setIcon(Icons.magnifier);
        favMenu.setIcon(CatIcons.fav);
        toolsMenu.setIcon(CatIcons.tool);
        windowsMenu.setIcon(CatIcons.windows);
        ;




        //添加菜单
        jMenuBar.add(fileMenu);
        jMenuBar.add(watchMenu);
        jMenuBar.add(favMenu);
        jMenuBar.add(toolsMenu);
        jMenuBar.add(windowsMenu);
        jMenuBar.add(generateHelpMenu());
    }



    private JMenu generateHelpMenu() {
        //设置icon
        helpMenu.setIcon(CatIcons.help);
        //打开github
        JMenuItem gitHub = new JMenuItem("GitHub地址", CatIcons.github);
        gitHub.addActionListener(e -> GuiUtils.openBrowser(Const.GIT_ADDRESS));
        helpMenu.add(gitHub);
        //分割
        helpMenu.addSeparator();

        //关于
        JMenuItem about = new JMenuItem("关于AnotherCat", CatIcons.about);
        about.addActionListener(e -> {
            JDialog dialog = new JDialog();
            dialog.getRootPane().putClientProperty(StyleId.STYLE_PROPERTY, StyleId.dialog);
            dialog.setIconImage(CatIcons.about.getImage());
            dialog.add(new WebLabel(StyleId.label, Const.ABOUT, WebLabel.CENTER).setFontSize(14));
            dialog.setSize(400, 150);
            //居中显示
            dialog.setLocationRelativeTo(null);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dialog.setResizable(false);
            dialog.setVisible(true);
        });
        helpMenu.add(about);
        return helpMenu;
    }


}
