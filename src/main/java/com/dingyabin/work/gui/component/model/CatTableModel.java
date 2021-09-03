package com.dingyabin.work.gui.component.model;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

/**
 * @author dingyabin
 * @date 2021-09-03 18:15
 */
public class CatTableModel<T> extends DefaultTableModel {


    public CatTableModel() {
    }


    public CatTableModel(int rowCount, int columnCount) {
        super(rowCount, columnCount);
    }


    public CatTableModel(T[] columnNames, int rowCount) {
        super(columnNames, rowCount);
    }

    public CatTableModel(List<String> columnNames, int rowCount) {
        setDataVector(createVector(rowCount), listToVector(columnNames));
    }



    private <E> Vector<E> listToVector(List<E> data) {
        Vector<E> vector = new Vector<>(data.size());
        vector.addAll(data);
        return vector;
    }


    private Vector createVector(int rowCount) {
        Vector vector = new Vector(rowCount);
        vector.setSize(rowCount);
        return vector;
    }

}