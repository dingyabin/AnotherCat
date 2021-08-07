package com.dingyabin.work.ctrl.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:0:08
 */
@Getter
@Setter
public class DataBaseSchema implements Serializable {


    /**
     * 库名字
     */
    private String schemaName;


    @Override
    public String toString() {
        return schemaName;
    }
}
