package com.dingyabin.work.ctrl.enums;

import java.util.stream.Stream;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:0:04
 */
public enum DataBaseTypeEnum {

    /**
     * mysql
     */
    MYSQL("MYSQL");


    private String type;

    DataBaseTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public static DataBaseTypeEnum getByType(String type) {
        return Stream.of(values()).filter(data -> data.getType().equals(type)).findFirst().orElse(null);
    }


}
