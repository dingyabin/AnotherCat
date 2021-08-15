package com.dingyabin.work.ctrl.adapter;

import com.dingyabin.work.ctrl.config.DynamicDataSource;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.common.model.*;
import com.dingyabin.work.ctrl.service.SystemMetaService;
import com.dingyabin.work.ctrl.service.TableContentDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:17:05
 */
@Slf4j
@Component
public class CatAdapterService {


    @Resource
    private SystemMetaService systemMetaService;

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Resource
    private TableContentDataService tableContentDataService;


    public CatRet<List<DataBaseSchema>> getDbsWithConnect(ConnectConfig connectConfig) {
        try {
            DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
            addDataSource(connectConfig);
            List<DataBaseSchema> dataBaseSchemas = systemMetaService.selectDataBaseSchema(dataSourceKey, connectConfig.typeEnum());
            return CatRet.success(dataBaseSchemas);
        } catch (Exception e) {
            log.error("connect error, connectConfig={}", connectConfig.toString(), e);
        }
        return CatRet.fail("无法获取连接:" + connectConfig.getHost() + ":" + connectConfig.getPort());
    }



    public List<TableSchema> getTablesWithDb(ConnectConfig connectConfig, DataBaseSchema dataBaseSchema){
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