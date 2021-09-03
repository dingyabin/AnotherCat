package com.dingyabin.work.common.generator.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 丁亚宾
 * Date: 2021/9/2.
 * Time:0:34
 */
@Getter
@Setter
public class TableNameCfg {

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
    public String toString() {
        return  "tableName='" + tableName + '\'';
    }
}
