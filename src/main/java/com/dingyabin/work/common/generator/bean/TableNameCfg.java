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
public class TableNameCfg implements IGeneratorTableModel {

    public static final String[] HEADER = {"表名", "Model名"};

    private String tableName;

    private String modelName;


    public TableNameCfg(String tableName) {
        this.tableName = tableName;
    }

    public TableNameCfg(String tableName, String modelName) {
        this.tableName = tableName;
        this.modelName = modelName;
    }


    @Override
    public String getValueAtColumn(int column) {
        if (column == 0) {
            return getTableName();
        }
        if (column == 1) {
            return getModelName();
        }
        return StringUtils.EMPTY;
    }

    @Override
    public void setValueAt(Object aValue, int column) {
        String value = (aValue == null) ? StringUtils.EMPTY : aValue.toString();
        if (column == 0) {
            setTableName(value);
        }
        if (column == 1) {
            setModelName(value);
        }
    }

    @Override
    public String toString() {
        return  "tableName='" + tableName + '\'';
    }
}
