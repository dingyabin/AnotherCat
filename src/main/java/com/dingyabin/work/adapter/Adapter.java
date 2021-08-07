package com.dingyabin.work.adapter;

import com.dingyabin.work.ctrl.config.DynamicDataSource;
import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.model.*;
import com.dingyabin.work.ctrl.service.SystemMetaService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:17:05
 */
@Component
public class Adapter {


    @Resource
    private SystemMetaService systemMetaService;

    @Resource
    private DynamicDataSource dynamicDataSource;


    public List<DataBaseSchema> getDbsOnConnectChange(ConnectConfig connectConfig){
        DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
        return systemMetaService.selectDataBaseSchema(dataSourceKey, connectConfig.typeEnum());
    }


    public List<TableSchema> getTablesOnDbSchemaChange(ConnectConfig connectConfig, DataBaseSchema dataBaseSchema){
        DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
        return systemMetaService.selectTableSchema(dataSourceKey, connectConfig.typeEnum(), dataBaseSchema.getSchemaName());
    }



    public List<ColumnSchema> getColumnOnTableChange(ConnectConfig connectConfig, TableSchema tableSchema){
        DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
        return systemMetaService.selectColumnSchema(dataSourceKey, connectConfig.typeEnum(), tableSchema.getTableName());
    }



    public DataSourceKey addDataSource(ConnectConfig connectConfig){
       return dynamicDataSource.addDefaultDataSource(connectConfig);
    }


    public DataSourceKey addDataSource(DataBaseTypeEnum dataBaseTypeEnum, String host, String port, String userName, String pwd, String dbName){
        return dynamicDataSource.addDataSource(dataBaseTypeEnum, host, port, userName, pwd, dbName);
    }




}
