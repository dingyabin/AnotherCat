package com.dingyabin.work.adapter;

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


    public List<TableSchema> getTablesOnDbSchemaChange(ConnectConfig connectConfig, DataBaseSchema dataBaseSchema){
        DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
        return systemMetaService.selectTableSchema(dataSourceKey, connectConfig.typeEnum(), dataBaseSchema.getSchemaName());
    }



    public List<ColumnSchema> getColumnOnTableChange(ConnectConfig connectConfig, TableSchema tableSchema){
        DataSourceKey dataSourceKey = connectConfig.defaultDataSourceKey();
        return systemMetaService.selectColumnSchema(dataSourceKey, connectConfig.typeEnum(), tableSchema.getTableName());
    }




}
