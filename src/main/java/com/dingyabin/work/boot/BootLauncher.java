package com.dingyabin.work.boot;

import com.alibaba.fastjson.JSON;
import com.dingyabin.work.ctrl.config.DynamicDataSource;
import com.dingyabin.work.ctrl.config.SpringBeanUtil;
import com.dingyabin.work.ctrl.model.*;
import com.dingyabin.work.ctrl.service.SystemMetaService;
import com.dingyabin.work.gui.SwingCompentDemo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:2:06
 */
@Component
public class BootLauncher implements CommandLineRunner {


    @Resource
    private SystemMetaService systemMetaService;


    @Resource
    private SwingCompentDemo swingCompentDemo;

    @Override
    public void run(String... args) throws Exception {

        swingCompentDemo.init();

        Set<ConnectConfig> connectConfigs = null;
        if (ConnectConfigManager.loadConnectConfigs()) {
            connectConfigs = ConnectConfigManager.getConnectConfigs();
        }


        ConnectConfig connectConfig = connectConfigs.stream().findFirst().orElse(null);


        DynamicDataSource dataSource = SpringBeanUtil.getBean(DynamicDataSource.class);
        DataSourceKey dataSourceKey = dataSource.addDefaultDataSource(connectConfig);


        swingCompentDemo.getColorSelect().setConnectConfig(connectConfig);


        List<DataBaseSchema> dataBaseSchemas = systemMetaService.selectDataBaseSchema(dataSourceKey, connectConfig.typeEnum());

        dataBaseSchemas.forEach(connect -> swingCompentDemo.addDbSelectItem(connect));



//        List<TableSchema> tableSchemas = systemMetaService.selectTableSchema(dataSourceKey, connectConfig.typeEnum(), "");
//        System.out.println(JSON.toJSONString(tableSchemas));
//
//
//        List<ColumnSchema> columnSchemas = systemMetaService.selectColumnSchema(dataSourceKey, connectConfig.typeEnum(), tableSchemas.get(0).getTableName());
//        System.out.println(JSON.toJSONString(columnSchemas));
//
//
//        List<IndexSchema> indexSchemas = systemMetaService.selectIndexSchema(dataSourceKey, connectConfig.typeEnum(), tableSchemas.get(0).getTableName());
//        System.out.println(JSON.toJSONString(indexSchemas));
    }


}
