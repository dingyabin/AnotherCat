package com.dingyabin.work.common.model;

import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:10:35
 */
@Getter
@Setter
public class ConnectConfig implements Serializable {
    private static final long serialVersionUID = -264703268549826760L;

    private String name;

    private String type;

    private String host;

    private String port;

    private String userName;

    private String pwd;

    public ConnectConfig() {
    }


    public ConnectConfig(String name, String type, String host, String port, String userName, String pwd) {
        this.name = name;
        this.type = type;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.pwd = pwd;
    }


    public DataBaseTypeEnum typeEnum() {
        return DataBaseTypeEnum.getByType(type);
    }


    public DataSourceKey defaultDataSourceKey() {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(typeEnum());
        return new DataSourceKey(host, port, schemaMeta.getDefaultDbName());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConnectConfig that = (ConnectConfig) o;
        return Objects.equals(name, that.name) && Objects.equals(type, that.type) && Objects.equals(host, that.host) && Objects.equals(port, that.port) && Objects.equals(userName, that.userName) && Objects.equals(pwd, that.pwd);
    }


    @Override
    public int hashCode() {
        return Objects.hash(name, type, host, port, userName, pwd);
    }


    @Override
    public String toString() {
        return String.format("%s-%s-%s-%s", name, host, port, userName);
    }

}