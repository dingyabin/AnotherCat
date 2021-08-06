package com.dingyabin.work.ctrl.meta;

import org.springframework.beans.factory.InitializingBean;

/**
 * 这个接口表示子类需要知道从哪个库哪个表里查询所有的数据库信息
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:23:12
 */
public interface SchemaMeta extends InitializingBean {


    /**
     * 如何查找所有数据库名
     * @return sql
     */
    String getDbNameSql();


    /**
     * 如何查找所有数据库名
     * @param dbName 库名
     * @return sql
     */
    String getTableSql(String dbName);


    /**
     * 如何查找所有列
     * @param tableName 表名字
     * @return sql
     */
    String getColumnSql(String tableName);


    /**
     * 如何查找所有索引
     * @param tableName 表名字
     * @return sql
     */
    String getIndexSql(String tableName);


    /**
     * 连接url
     * @param host 域名
     * @param port 端口
     * @param dbName 库名
     * @return 连接url
     */
    String connectUrl(String host, String port, String dbName);

    /**
     * 驱动名
     * @return 驱动名
     */
    String driverClassName();


    /**
     * 默认的库名(通常是系统库，用来查找系统配置的库)
     * @return  默认的库名
     */
    String getDefaultDbName();


    @Override
    default void afterPropertiesSet() throws Exception {

    }
}
