package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author 丁亚宾
 * Date: 2021/8/15.
 * Time:18:00
 */
public class CatList<T> extends JList<T> {

    private CatListCellRenderer listCellRenderer = new CatListCellRenderer();


    public CatList(@Nullable Icon itemIcon, List<T> listData) {
        this(listData);
        listCellRenderer.setItemIcon(itemIcon);
        setCellRenderer(listCellRenderer);
    }


    public CatList(List<T> listData) {
        putClientProperty(StyleId.STYLE_PROPERTY, StyleId.list);
        DefaultListModel<T> listModel = new DefaultListModel<>();
        listData.forEach(listModel::addElement);
        setModel(listModel);
    }


    public CatList<T> fontSize(int fontSize) {
        FontMethodsImpl.setFontSize(this, fontSize);
        return this;
    }


    public CatList<T> fontName(String fontName) {
        FontMethodsImpl.setFontName(this, fontName);
        return this;
    }


    public CatList<T> cellRenderer(ListCellRenderer<? super T> cellRenderer) {
        setCellRenderer(listCellRenderer);
        return this;
    }


    public CatList<T> layoutVW() {
        super.setLayoutOrientation(JList.VERTICAL_WRAP);
        return this;
    }


    public CatList<T> visCount(int count) {
        setVisibleRowCount(count);
        return this;
    }


    public CatList<T> layoutOri(int layoutOrientation) {
        super.setLayoutOrientation(layoutOrientation);
        return this;
    }


    public void clear() {
        ListModel<T> model = getModel();
        if (model instanceof DefaultListModel) {
            DefaultListModel<T> listModel = (DefaultListModel<T>) model;
            listModel.clear();
        } else {
            setModel(new DefaultListModel<>());
        }
    }


    public void addDoubleClickListener(Consumer<MouseEvent> eventConsumer) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    eventConsumer.accept(e);
                }
            }
        });
    }


}
