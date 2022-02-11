package com.dingyabin.work.ctrl.adapter;

import com.dingyabin.work.ctrl.config.DynamicDataSource;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.common.model.*;
import com.dingyabin.work.ctrl.config.SpringBeanHolder;
import com.dingyabin.work.ctrl.service.SystemMetaService;
import com.dingyabin.work.ctrl.service.TableContentDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
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
public class CatAdapterService implements InitializingBean {


    @Resource
    private SystemMetaService systemMetaService;

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Resource
    private TableContentDataService tableContentDataService;


    @Override
    public void afterPropertiesSet() {
        SpringBeanHolder.registryCatAdapter(this);
    }


    public void closeConnect(ConnectConfig connectConfig){
        dynamicDataSource.closeAndRemoveDatasource(connectConfig);
    }


    public CatRet<List<DataBaseSchema>> getDbsWithConnect(ConnectConfig connectConfig) {
        try {
            DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
            addDefaultDataSource(connectConfig);
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



    public List<ColumnSchema> getColumnSchema(ConnectConfig connectConfig, TableSchema tableSchema){
        DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
        return systemMetaService.selectColumnSchema(dataSourceKey, connectConfig.typeEnum(), tableSchema.getTableName());
    }



    public DataSourceKey addDefaultDataSource(ConnectConfig connectConfig){
       return dynamicDataSource.addDefaultDataSource(connectConfig);
    }



    public DataSourceKey addDataSource(DataBaseTypeEnum dataBaseTypeEnum, String host, String port, String userName, String pwd, String dbName){
        return dynamicDataSource.addDataSource(dataBaseTypeEnum, host, port, userName, pwd, dbName);
    }



    public DataSourceKey addDataSource(ConnectConfig con, DataBaseSchema dataBaseSchema){
        return dynamicDataSource.addDataSource(con.typeEnum(), con.getHost(), con.getPort(), con.getUserName(), con.getPwd(), dataBaseSchema.getSchemaName());
    }


    public List<Map<String,Object>> queryTable(DataSourceKey dataSourceKey, String tableName, int pageNum, int pageSize){
        return tableContentDataService.queryTable(dataSourceKey, tableName, pageNum, pageSize);
    }



    public boolean reNameTable(ConnectConfig con, DataBaseSchema dataBaseSchema, String oldName, String newName) {
        try {
            DataBaseTypeEnum typeEnum = con.typeEnum();
            systemMetaService.reNameTable(con.dataSourceKey(dataBaseSchema.getSchemaName()), typeEnum, oldName, newName);
            return true;
        } catch (Exception e) {
            log.error("reNameTable error,oldName={}, newName={}", oldName, newName, e);
        }
        return false;
    }

}
