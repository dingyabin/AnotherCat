package com.dingyabin.work.boot;

import com.alibaba.fastjson.JSON;
import com.dingyabin.work.ctrl.config.DataSourceKeyHolder;
import com.dingyabin.work.ctrl.config.DynamicDataSource;
import com.dingyabin.work.ctrl.config.SpringBeanUtil;
import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import com.dingyabin.work.ctrl.model.*;
import com.dingyabin.work.ctrl.service.SystemMetaService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:2:06
 */
@Component
public class BootLauncher implements CommandLineRunner {


    @Resource
    private SystemMetaService systemMetaService;

    @Override
    public void run(String... args) throws Exception {

        DynamicDataSource dataSource = SpringBeanUtil.getBean(DynamicDataSource.class);

        dataSource.addDataSource(DataBaseTypeEnum.MYSQL,"127.0.0.1","3306","root","12345678");


        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(DataBaseTypeEnum.MYSQL);


        DataSourceKeyHolder.setKey(new DataSourceKey("127.0.0.1","3306", schemaMeta.getDefaultDbName()));


        List<DataBaseSchema> dataBaseSchemas = systemMetaService.selectDataBaseSchema(DataBaseTypeEnum.MYSQL);
        System.out.println(JSON.toJSONString(dataBaseSchemas));


        List<TableSchema> tableSchemas = systemMetaService.selectTableSchema(DataBaseTypeEnum.MYSQL, dataBaseSchemas.get(5).getSchemaName());
        System.out.println(JSON.toJSONString(tableSchemas));


        List<ColumnSchema> columnSchemas = systemMetaService.selectColumnSchema(DataBaseTypeEnum.MYSQL, tableSchemas.get(0).getTableName());
        System.out.println(JSON.toJSONString(columnSchemas));


        List<IndexSchema> indexSchemas = systemMetaService.selectIndexSchema(DataBaseTypeEnum.MYSQL, tableSchemas.get(0).getTableName());
        System.out.println(JSON.toJSONString(indexSchemas));
    }


}
