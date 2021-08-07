package com.dingyabin.work.ctrl.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:22:36
 */
@Getter
@Setter
public class DataSourceKey implements Serializable {
    private static final long serialVersionUID = 3270984248700791508L;

    private String host;

    private String port;

    private String dbName;


    public DataSourceKey() {
    }


    public DataSourceKey(String host, String port, String dbName) {
        this.host = host;
        this.port = port;
        this.dbName = dbName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DataSourceKey that = (DataSourceKey) o;
        return Objects.equals(host, that.host) && Objects.equals(port, that.port) && Objects.equals(dbName, that.dbName);
    }


    @Override
    public int hashCode() {
        return Objects.hash(host, port, dbName);
    }
}
