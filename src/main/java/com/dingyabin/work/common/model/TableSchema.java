package com.dingyabin.work.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:0:09
 */
@Getter
@Setter
public class TableSchema implements Serializable {

    private static final long serialVersionUID = -6571760774872910737L;

    /**
     * 表名字
     */
    private String tableName;

    public TableSchema() {
    }

    public TableSchema(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String toString() {
        return tableName;
    }
}



