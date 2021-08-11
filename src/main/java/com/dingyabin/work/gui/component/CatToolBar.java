package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;

import javax.swing.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/11.
 * Time:23:25
 */
public class CatToolBar extends JToolBar {


    public CatToolBar() {
        init();
    }

    public CatToolBar(int orientation) {
        super(orientation);
        init();
    }

    public CatToolBar(String name) {
        super(name);
        init();
    }

    public CatToolBar(String name, int orientation) {
        super(name, orientation);
        init();
    }


    private void init() {
        putClientProperty(StyleId.STYLE_PROPERTY, StyleId.toolbarAttachedNorth);
        JButton jButton = new JButton("用户", CatIcons.about);
        jButton.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.buttonHover);
        jButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        jButton.setHorizontalTextPosition(SwingConstants.CENTER);
        add(jButton);
    }


}
