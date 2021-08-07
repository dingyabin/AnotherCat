package com.dingyabin.work.ctrl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import com.dingyabin.work.ctrl.model.ConnectConfigManager;
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



    public boolean addDataSource(DataBaseTypeEnum dataBaseTypeEnum, String host, String port, String userName, String pwd) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return false;
        }
        //配置数据源连接池
        DruidDataSource dataSource = SpringBeanUtil.getBean(DruidDataSource.class);
        dataSource.setUrl(schemaMeta.connectUrl(host, port, schemaMeta.getDefaultDbName()));
        dataSource.setUsername(userName);
        dataSource.setPassword(pwd);

        //注册数据源到内存
        addDataSource(new DataSourceKey(host, port, schemaMeta.getDefaultDbName()), dataSource);

        //将配置写入文件，以便下次加载
        return ConnectConfigManager.addConnectConfig(dataBaseTypeEnum.getType(), host, port, userName, pwd);
    }


}
