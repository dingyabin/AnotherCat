package com.dingyabin.work.gui.component;

import com.dingyabin.work.common.model.IconAble;

import javax.swing.*;
import java.awt.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/15.
 * Time:16:44
 */
public class CatListCellRenderer extends DefaultListCellRenderer {

    private Icon icon;

    public CatListCellRenderer() {
    }

    public CatListCellRenderer(Icon icon) {
        this.icon = icon;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (icon != null) {
            setIcon(icon);
        }
        if (value instanceof IconAble) {
            IconAble iconAble = (IconAble) value;
            setIcon(iconAble.getIcon());
        }
        return component;
    }


}
