package com.dingyabin.work.gui.component.model;

import org.apache.commons.collections4.CollectionUtils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/9/3.
 * Time:21:04
 */
public class ModelGeneratorTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -3989907319968614404L;

    private List<? extends IGeneratorTableModel> models;

    private String[] header;

    public ModelGeneratorTableModel(List<? extends IGeneratorTableModel> models, String[] header) {
        this.models = models;
        this.header = header;
    }


    @Override
    public int getRowCount() {
        if (CollectionUtils.isEmpty(models)) {
            return 0;
        }
        return models.size();
    }


    @Override
    public int getColumnCount() {
        return 2;
    }


    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        IGeneratorTableModel model = models.get(rowIndex);
        if (model == null) {
            return null;
        }
        return model.getValueAtColumn(columnIndex);
    }


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1;
    }


    @Override
    public String getColumnName(int column) {
        return header[column];
    }
}
