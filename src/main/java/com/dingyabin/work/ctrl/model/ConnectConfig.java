package com.dingyabin.work.ctrl.model;

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

    private String type;

    private String host;

    private String port;

    private String userName;

    private String pwd;

    public ConnectConfig() {
    }


    public ConnectConfig(String type, String host, String port, String userName, String pwd) {
        this.type = type;
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.pwd = pwd;
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
        return Objects.equals(type, that.type) && Objects.equals(host, that.host) && Objects.equals(port, that.port) && Objects.equals(userName, that.userName) && Objects.equals(pwd, that.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, host, port, userName, pwd);
    }
}
