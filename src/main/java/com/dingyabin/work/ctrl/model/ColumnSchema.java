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
public class ColumnSchema implements Serializable {
    private static final long serialVersionUID = -96296771934823599L;

    private String columnName;

    private String columnType;

    private String columnComment;



    @Override
    public String toString() {
        return String.format("%s-%s-%s", columnName, columnType, columnComment);
    }


}
