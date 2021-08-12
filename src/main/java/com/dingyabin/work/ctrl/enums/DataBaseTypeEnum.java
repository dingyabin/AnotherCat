package com.dingyabin.work.ctrl.enums;

import com.dingyabin.work.gui.component.CatIcons;

import javax.swing.*;
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
    MYSQL("MYSQL", CatIcons.mysql);


    private String type;

    private Icon icon;

    DataBaseTypeEnum(String type, Icon icon) {
        this.type = type;
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public Icon getIcon() {
        return icon;
    }

    public static DataBaseTypeEnum getByType(String type) {
        return Stream.of(values()).filter(data -> data.getType().equals(type)).findFirst().orElse(null);
    }


}
