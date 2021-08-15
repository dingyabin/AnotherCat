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

    private Icon itemIcon;


    public CatListCellRenderer() {
    }

    public CatListCellRenderer(Icon icon) {
        this.itemIcon = icon;
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (itemIcon != null) {
            setIcon(itemIcon);
        }
        if (value instanceof IconAble) {
            IconAble iconAble = (IconAble) value;
            setIcon(iconAble.getIcon());
        }
        return component;
    }


    public Icon getItemIcon() {
        return itemIcon;
    }

    public void setItemIcon(Icon itemIcon) {
        this.itemIcon = itemIcon;
    }

}
