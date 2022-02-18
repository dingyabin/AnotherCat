package com.dingyabin.work.ctrl.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.common.listeners.CatActionListener;
import com.dingyabin.work.common.model.CatNewConModel;
import com.dingyabin.work.common.model.SaveConnectEvent;
import com.dingyabin.work.ctrl.event.SystemEventDispatcher;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataSourceKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author dingyabin
 * @date 2016/11/7
 */

public class DynamicDataSource extends AbstractRoutingDataSource implements CatActionListener<SaveConnectEvent> {


    private Map<Object, Object> dynamicDataSources = new HashMap<>();


    public DynamicDataSource() {
        setTargetDataSources(dynamicDataSources);
        SystemEventDispatcher.register(this);
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
     *
     * @param config 类型
     * @return dataSourceKey
     */
    public DataSourceKey addDefaultDataSource(ConnectConfig config) {
        return addDataSource(config.typeEnum(), config.getHost(), config.getPort(), config.getUserName(), config.getPwd(), null);
    }



    /**
     * 增加一个新的数据库连接池
     * 如果已经包含了这个连接，则noting to do
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
        DruidDataSource dataSource = SpringBeanHolder.getBean(DruidDataSource.class);
        dataSource.setUrl(schemaMeta.connectUrl(host, port, dbName));
        dataSource.setUsername(userName);
        dataSource.setPassword(pwd);

        //注册数据源连接池，连接默认的库
        addDataSource(dataSourceKey, dataSource);
        return dataSourceKey;
    }



    /**
     * 关闭 并删除连接
     * @param connectConfig 连接配置
     */
    public void closeAndRemoveDatasource(ConnectConfig connectConfig) {
        Iterator<Map.Entry<Object, Object>> iterator = dynamicDataSources.entrySet().iterator();
        boolean remove = false;
        while (iterator.hasNext()) {
            Map.Entry<Object, Object> entry = iterator.next();
            Object key = entry.getKey();
            if (!(key instanceof DataSourceKey)) {
                continue;
            }
            DataSourceKey sourceKey = (DataSourceKey) key;
            if (sourceKey.getHost().equals(connectConfig.getHost()) && sourceKey.getPort().equals(connectConfig.getPort())) {
                Object value = entry.getValue();
                if (!(value instanceof DruidDataSource)) {
                    continue;
                }
                ((DruidDataSource) value).close();
                iterator.remove();
                remove = true;
            }
        }
        if (remove) {
            afterPropertiesSet();
        }
    }


    @Override
    public void process(SaveConnectEvent saveConnectEvent) {
        CatNewConModel catNewConModel = saveConnectEvent.getCatNewConModel();
        //新增模式不处理
        if (catNewConModel.isSaveMode()) {
            return;
        }
        //修改模式的话，删除这个数据源
        ConnectConfig oldConFig = catNewConModel.getOldConnectConfig();
        if (oldConFig == null) {
            return;
        }
        //如果只是名字不一样，就不操作
        if (saveConnectEvent.getSavedConnectConfig().onlyNameDifferent(oldConFig)) {
            return;
        }
        closeAndRemoveDatasource(oldConFig);
    }



    @Override
    public Class<SaveConnectEvent> getListenType() {
        return SaveConnectEvent.class;
    }
}
