package com.dingyabin.work.ctrl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import com.dingyabin.work.ctrl.model.ConnectConfig;
import com.dingyabin.work.ctrl.model.DataSourceKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dingyabin
 * @date 2016/11/7
 */

public class DynamicDataSource extends AbstractRoutingDataSource {


    private Map<Object, Object> dynamicDataSources = new HashMap<>();


    public DynamicDataSource() {
        setTargetDataSources(dynamicDataSources);
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceKeyHolder.getKey();
    }


    private void addDataSource(DataSourceKey key, DataSource dataSource) {
        //如果是已经包含的key，就不添加了
        if (dynamicDataSources.containsKey(key)) {
            return;
        }
        dynamicDataSources.put(key, dataSource);
        afterPropertiesSet();
    }


    /**
     * 增加一个新的数据库连接池, 连接默认的库
     *
     * @param config 类型
     * @return dataSourceKey
     */
    public DataSourceKey addDefaultDataSource(ConnectConfig config) {
        return addDefaultDataSource(config.typeEnum(), config.getHost(), config.getPort(), config.getUserName(), config.getPwd());
    }


    /**
     * 增加一个新的数据库连接池, 连接默认的库
     *
     * @param dataBaseTypeEnum 类型
     * @param host             主机地址
     * @param port             端口
     * @param userName         用户名
     * @param pwd              密码
     * @return dataSourceKey
     */
    public DataSourceKey addDefaultDataSource(DataBaseTypeEnum dataBaseTypeEnum, String host, String port, String userName, String pwd) {
        return addDataSource(dataBaseTypeEnum, host, port, userName, pwd, null);
    }


    /**
     * 增加一个新的数据库连接池
     *
     * @param dataBaseTypeEnum 类型
     * @param host             主机地址
     * @param port             端口
     * @param userName         用户名
     * @param pwd              密码
     * @param dbName           库名
     * @return dataSourceKey
     */
    public DataSourceKey addDataSource(DataBaseTypeEnum dataBaseTypeEnum, String host, String port, String userName, String pwd, String dbName) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return null;
        }
        //配置数据源连接池
        if (StringUtils.isBlank(dbName)) {
            dbName = schemaMeta.getDefaultDbName();
        }
        //已经包含了，就不在添加了
        DataSourceKey dataSourceKey = new DataSourceKey(host, port, dbName);
        if (dynamicDataSources.containsKey(dataSourceKey)) {
            return dataSourceKey;
        }

        //没包含，继续添加
        DruidDataSource dataSource = SpringBeanUtil.getBean(DruidDataSource.class);
        dataSource.setUrl(schemaMeta.connectUrl(host, port, dbName));
        dataSource.setUsername(userName);
        dataSource.setPassword(pwd);

        //注册数据源连接池，连接默认的库
        addDataSource(dataSourceKey, dataSource);
        return dataSourceKey;
    }


}
