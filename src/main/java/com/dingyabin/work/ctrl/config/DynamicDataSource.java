package com.dingyabin.work.ctrl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.model.DataSourceKey;
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


    public void addDataSource(DataSourceKey key, DataSource dataSource) {
        //如果是已经包含的key，就不添加了
        if (dynamicDataSources.containsKey(key)) {
            return;
        }
        dynamicDataSources.put(key, dataSource);
        afterPropertiesSet();
    }


    public void addDataSource(String host, String port, String dbName, DataSource dataSource) {
        addDataSource(new DataSourceKey(host, port, dbName), dataSource);
    }


    public void addDataSource(DataBaseTypeEnum dataBaseTypeEnum, String host, String port, String dbName, String userName, String pwd) {
        DruidDataSource dataSource = SpringBeanUtil.getBean(DruidDataSource.class);
        dataSource.setUrl(dataBaseTypeEnum.connectUrl(host, port, dbName));
        dataSource.setUsername(userName);
        dataSource.setPassword(pwd);
        addDataSource(new DataSourceKey(host, port, dbName), dataSource);
    }


}
