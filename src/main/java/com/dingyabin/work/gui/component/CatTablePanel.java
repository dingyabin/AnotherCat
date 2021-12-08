package com.dingyabin.work.gui.component;

import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.common.model.TableSchema;
import lombok.Getter;

import javax.swing.*;
import java.util.Objects;

/**
 * @author 丁亚宾
 * Date: 2021/12/9.
 * Time:1:00
 */
@Getter
public class CatTablePanel extends JPanel {

    private ConnectConfig connectConfig;

    private DataBaseSchema dataBaseSchema;

    private TableSchema tableSchema;


    public CatTablePanel() {
    }

    public CatTablePanel(ConnectConfig connectConfig, DataBaseSchema dataBaseSchema, TableSchema tableSchema) {
        this.connectConfig = connectConfig;
        this.dataBaseSchema = dataBaseSchema;
        this.tableSchema = tableSchema;
    }


    public boolean sameWith(ConnectConfig connectConfig, String schemaName, String tableName) {
        return this.connectConfig.equals(connectConfig) && Objects.equals(dataBaseSchema.getSchemaName(), schemaName) && Objects.equals(tableSchema.getTableName(), tableName);
    }
}
