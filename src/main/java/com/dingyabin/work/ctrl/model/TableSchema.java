package com.dingyabin.work.ctrl.model;

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

    /**
     * 表名字
     */
    private String tableName;

}



