package com.dingyabin.work.swingtest;

import com.alee.laf.WebLookAndFeel;
import com.alee.managers.icon.Icons;
import com.alee.managers.style.StyleId;
import com.alee.utils.ArrayUtils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/8.
 * Time:23:26
 */
public class JtabPaneTest {


    private final JFrame jf = new JFrame("AnotherCat");




    public void init(){


        final JTabbedPane tabbedPane = new JTabbedPane ( SwingConstants.TOP, JTabbedPane.SCROLL_TAB_LAYOUT );
        tabbedPane.putClientProperty (StyleId.STYLE_PROPERTY, StyleId.tabbedpane );
        for ( int i = 0; i < 8; i++ ) {
            final Icon icon = ArrayUtils.roundRobin ( i, Icons.leaf, Icons.magnifier, Icons.computer, Icons.globe );
            JLabel content = new JLabel("xxxxxxxxxxxxxxxxxxxx"+i);
            tabbedPane.addTab ( "44444444",icon, content);

            tabbedPane.setTabComponentAt(i, createTabComponent("table_  " + i, icon, tabbedPane, content));
        }
        tabbedPane.setForegroundAt ( 3, new Color( 255, 80, 80 ) );


        jf.add(tabbedPane);

        jf.pack();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }


    private Component createTabComponent2(String title, Icon icon, JTabbedPane tabbedPane, Component content) {
        JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,0,0));
        JLabel jLabel = new JLabel(title, icon, SwingConstants.CENTER);
        final JButton basic = new JButton (Icons.cross);
        basic.addActionListener(e -> {
            tabbedPane.removeTabAt(tabbedPane.indexOfComponent(content));
        });
        basic.putClientProperty (StyleId.STYLE_PROPERTY, StyleId.buttonIconHover);
        basic.setPreferredSize(new Dimension(20,20));
        jPanel.add(jLabel);
        jPanel.add(basic);
        jPanel.setOpaque(false);
        jPanel.setSize(new Dimension(25,25));
        return jPanel;
    }


    private Component createTabComponent(String title, Icon icon, JTabbedPane tabbedPane, Component content) {
        Box horizontalBox = Box.createHorizontalBox();
        JLabel jLabel = new JLabel(title, icon, SwingConstants.CENTER);
        final JButton basic = new JButton (Icons.cross);
        basic.addActionListener(e -> {
            tabbedPane.removeTabAt(tabbedPane.indexOfComponent(content));
        });
        basic.putClientProperty (StyleId.STYLE_PROPERTY, StyleId.buttonIconHover);
        basic.setPreferredSize(new Dimension(20,20));
        horizontalBox.add(jLabel);
        horizontalBox.add(basic);

        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem move = new JMenuItem("前移一位");
        move.addActionListener(e -> {
            int index = tabbedPane.indexOfComponent(content);
            if (index <= 0) {
                return;
            }
            Component componentPre = tabbedPane.getComponentAt(index - 1);
            tabbedPane.setComponentAt(index ,componentPre);
            tabbedPane.setComponentAt(index - 1, content);

//            int indexTab = tabbedPane.indexOfTabComponent(horizontalBox);
//            if (indexTab == 0) {
//                return;
//            }
//            Component componentTabPre = tabbedPane.getTabComponentAt(indexTab - 1);
//            tabbedPane.setTabComponentAt(indexTab ,componentTabPre);
//            tabbedPane.setTabComponentAt(indexTab - 1, horizontalBox);

        });
        jPopupMenu.add(move);
        jPopupMenu.addSeparator();
        JMenuItem close = new JMenuItem("关闭所有");
        close.setIcon(Icons.crossHover);
        close.addActionListener(e -> {
            tabbedPane.removeAll();
        });
        jPopupMenu.add(close);
        horizontalBox.setComponentPopupMenu(jPopupMenu);
        return horizontalBox;
    }




    public static void main(String[] args) {
        WebLookAndFeel.install();
        new JtabPaneTest().init();
    }



}
