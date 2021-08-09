package com.dingyabin.work.gui.utils;

import com.alee.managers.style.StyleId;

import javax.swing.*;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/9.
 * Time:22:22
 */
public class GuiUtils {



    public static JComponent createJScrollPane(Component component){
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.putClientProperty(StyleId.STYLE_PROPERTY, StyleId.scrollpaneTransparentButtonless);
        return scrollPane;
    }
}
