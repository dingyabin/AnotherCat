package com.dingyabin.work.ctrl.adapter;

import com.dingyabin.work.ctrl.config.DynamicDataSource;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.common.model.*;
import com.dingyabin.work.ctrl.service.SystemMetaService;
import com.dingyabin.work.ctrl.service.TableContentDataService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

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

    @Resource
    private TableContentDataService tableContentDataService;


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


    public List<Map<String,Object>> queryTable(DataSourceKey dataSourceKey, String tableName){
        return tableContentDataService.queryTable(dataSourceKey ,tableName);
    }

}
