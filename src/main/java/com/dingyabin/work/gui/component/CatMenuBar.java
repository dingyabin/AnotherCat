package com.dingyabin.work.gui.component;

import com.alee.laf.label.WebLabel;
import com.alee.laf.menu.MenuBarLayout;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;
import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.gui.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:23:30
 */
public class CatMenuBar extends JMenuBar {

    private CatTabPane tabbedPane;

    private JFrame jframe;

    private final JMenu fileMenu = new JMenu("文件");

    private final JMenu watchMenu = new JMenu("查看");

    private final JMenu favMenu = new JMenu("收藏夹");

    private final JMenu toolsMenu = new JMenu("工具");

    private final JMenu windowsMenu = new JMenu("窗口");

    private final JMenu helpMenu = new JMenu("帮助");


    public CatMenuBar() {
        super();
        init();
    }


    public CatMenuBar(CatTabPane tabbedPane, JFrame jframe) {
        this();
        this.tabbedPane = tabbedPane;
        this.jframe = jframe;
    }


    private void init() {
        //设置JMenu间距
        MenuBarLayout layout = (MenuBarLayout) getLayout();
        layout.setSpacing(16);

        //设置图标
        watchMenu.setIcon(Icons.magnifier);
        favMenu.setIcon(CatIcons.fav);
        windowsMenu.setIcon(CatIcons.windows);


        //添加菜单
        add(generateFileMenu());
        add(watchMenu);
        add(favMenu);
        add(generateToolMenu());
        add(windowsMenu);
        add(generateHelpMenu());
    }




    /**
     * '帮助'菜单按钮
     *
     * @return 菜单
     */
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
        about.addActionListener(e -> createAboutDialog());
        helpMenu.add(about);
        return helpMenu;
    }




    /**
     * '工具'菜单按钮
     *
     * @return 菜单
     */
    private JMenu generateToolMenu() {
        String title = "历史日志";
        //设置icon
        toolsMenu.setIcon(CatIcons.tool);
        //日志调试
        JMenuItem log = new JMenuItem(title, CatIcons.log);
        log.addActionListener(e -> {
            if (tabbedPane == null) {
                return;
            }
            LogTabTextArea logTabTextArea = new LogTabTextArea(10, 10).lineWrap(true).fontSize(15).noEdit().addPopupMenu();
            JScrollPane jScrollPane = GuiUtils.createJscrollPane(logTabTextArea);
            tabbedPane.addTabWithTabComponent(title, CatIcons.log, jScrollPane,true);
            //读取日志
            logTabTextArea.showLog();
        });
        toolsMenu.add(log);
        return toolsMenu;
    }




    /**
     * '文件'菜单按钮
     *
     * @return 菜单
     */
    private JMenu generateFileMenu() {
        String title = "添加数据源";
        //设置icon
        fileMenu.setIcon(Icons.dir);
        //新建数据源
        JMenu connectMenu = new JMenu(title);
        connectMenu.setIcon(CatIcons.connect);

        //具体数据源类型
        for (DataBaseTypeEnum typeEnum : DataBaseTypeEnum.values()) {
            JMenuItem dsItem = new JMenuItem(typeEnum.getType(), typeEnum.getIcon());
            dsItem.addActionListener(e -> new CatNewConnectDialog(jframe, typeEnum, title,true).showSelf());
            connectMenu.add(dsItem);
        }
        //未知数据源
        connectMenu.addSeparator();
        connectMenu.add(new JMenuItem("更多类型，敬请期待", CatIcons.unknown));

        //添加按钮
        fileMenu.add(connectMenu);
        return fileMenu;
    }


    /**
     * 打开“关于”对话框
     */
    private void createAboutDialog() {
        JDialog dialog = new JDialog(jframe,"关于");
        dialog.getRootPane().putClientProperty(StyleId.STYLE_PROPERTY, StyleId.dialog);
        //标题图标
        dialog.setIconImage(CatIcons.about.getImage());
        //图标以及说明
        dialog.add(new WebLabel(StyleId.label, Const.ABOUT, CatIcons.biglogo, WebLabel.CENTER).setFontSize(14));
        //最佳大小
        dialog.pack();
        //居中显示
        dialog.setLocationRelativeTo(jframe);
        dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //不可调大小
        dialog.setResizable(false);
        dialog.setVisible(true);
    }



}
