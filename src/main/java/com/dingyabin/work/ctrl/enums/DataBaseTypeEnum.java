package com.dingyabin.work.ctrl.enums;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:0:04
 */
public enum DataBaseTypeEnum {

    /**
     * mysql
     */
    MYSQL() {
        @Override
        public String connectUrl(String host, String port, String dbName) {
            return String.format("jdbc:mysql://%s:%s/%s?characterEncoding=UTF-8", host, port, dbName);
        }

        @Override
        public String driverClassName() {
            return "com.mysql.jdbc.Driver";
        }
    };





    public abstract String connectUrl(String host, String port, String dbName);


    public abstract String driverClassName();


}
