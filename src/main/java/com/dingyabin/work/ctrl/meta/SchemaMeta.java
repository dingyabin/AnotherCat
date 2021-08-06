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


    @Override
    default void afterPropertiesSet() throws Exception {

    }
}
