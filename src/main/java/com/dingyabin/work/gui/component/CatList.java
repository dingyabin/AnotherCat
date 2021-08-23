package com.dingyabin.work.gui.component;

import com.alee.managers.style.StyleId;
import com.alee.utils.swing.extensions.FontMethodsImpl;
import com.dingyabin.work.common.cons.Const;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @author 丁亚宾
 * Date: 2021/8/15.
 * Time:18:00
 */
public class CatList<T> extends JList<T> {

    private CatListCellRenderer listCellRenderer = new CatListCellRenderer();


    public CatList(Icon itemIcon, List<T> listData) {
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

    public CatList<T> multi(){
        setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return this;
    }


    public CatList<T> single(){
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return this;
    }



    public CatList<T> layoutOri(int layoutOrientation) {
        super.setLayoutOrientation(layoutOrientation);
        return this;
    }



    public T getModelByIndex(int index){
        ListModel<T> model = getModel();
        if (model == null || model.getSize() <= index) {
            return null;
        }
        return model.getElementAt(index);
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



    public void clearAndResetModel(List<T> listData) {
        clear();
        DefaultListModel<T> listModel = (DefaultListModel<T>) (getModel());
        listData.forEach(listModel::addElement);
    }



    public void searchEveryElement(BiConsumer<T, Integer> consumer) {
        ListModel<T> model = getModel();
        if (model == null) {
            return;
        }
        int size = model.getSize();
        for (int i = 0; i < size; i++) {
            consumer.accept(model.getElementAt(i), i);
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



    public void addPopMenuToList(JPopupMenu jPopupMenu) {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() || e.getButton() == MouseEvent.BUTTON3) {
                    int index = locationToIndex(new Point(e.getX(), e.getY()));
                    if (index < 0) {
                        return;
                    }
                    CatList.this.clearSelection();
                    setSelectedIndex(index);
                    jPopupMenu.putClientProperty(Const.JLIST_CURRENT_SELECTED_INDEX, index);
                    jPopupMenu.show(CatList.this, e.getX(), e.getY());
                }
            }
        });
    }



}
