package com.dingyabin.work.common.generator.bean;

import com.dingyabin.work.gui.component.model.IGeneratorTableModel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 丁亚宾
 * Date: 2021/9/2.
 * Time:0:34
 */
@Getter
@Setter
public class ColumnNameCfg implements IGeneratorTableModel {

    public static final String[] HEADER = {"列名", "Model字段名"};

    private String columnName;

    private String fieldName;


    public ColumnNameCfg(String columnName) {
        this.columnName = columnName;
    }

    public ColumnNameCfg(String columnName, String fieldName) {
        this.columnName = columnName;
        this.fieldName = fieldName;
    }


    @Override
    public String getValueAtColumn(int column) {
        if (column == 0) {
            return getColumnName();
        }
        if (column == 1) {
            return getFieldName();
        }
        return StringUtils.EMPTY;
    }


    @Override
    public String toString() {
        return  "columnName='" + columnName + '\'';
    }
}
