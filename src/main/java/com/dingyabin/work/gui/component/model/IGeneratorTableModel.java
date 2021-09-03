package com.dingyabin.work.gui.component.model;

/**
 * @author 丁亚宾
 * Date: 2021/9/3.
 * Time:23:59
 */
public interface IGeneratorTableModel {

    String getValueAtColumn(int column);

    void setValueAt(Object aValue, int columnIndex);
}
