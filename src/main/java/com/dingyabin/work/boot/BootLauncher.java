package com.dingyabin.work.boot;

import com.alibaba.fastjson.JSON;
import com.dingyabin.work.ctrl.config.DynamicDataSource;
import com.dingyabin.work.ctrl.config.SpringBeanUtil;
import com.dingyabin.work.ctrl.model.*;
import com.dingyabin.work.ctrl.service.SystemMetaService;
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

    @Override
    public void run(String... args) throws Exception {

        Set<ConnectConfig> connectConfigs = null;
        if (ConnectConfigManager.loadConnectConfigs()) {
            connectConfigs = ConnectConfigManager.getConnectConfigs();
            System.out.println(String.format("共有%s个数据库:", connectConfigs.size()));
            connectConfigs.forEach(connect -> System.out.println(JSON.toJSONString(connect)));
            System.out.println("请选择一个");
        }


        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();

        ConnectConfig connectConfig = connectConfigs.stream().findFirst().orElse(null);


        DynamicDataSource dataSource = SpringBeanUtil.getBean(DynamicDataSource.class);
        DataSourceKey dataSourceKey = dataSource.addDefaultDataSource(connectConfig);


        List<DataBaseSchema> dataBaseSchemas = systemMetaService.selectDataBaseSchema(dataSourceKey, connectConfig.typeEnum());
        System.out.println(JSON.toJSONString(dataBaseSchemas));


        List<TableSchema> tableSchemas = systemMetaService.selectTableSchema(dataSourceKey, connectConfig.typeEnum(), dataBaseSchemas.get(0).getSchemaName());
        System.out.println(JSON.toJSONString(tableSchemas));


        List<ColumnSchema> columnSchemas = systemMetaService.selectColumnSchema(dataSourceKey, connectConfig.typeEnum(), tableSchemas.get(0).getTableName());
        System.out.println(JSON.toJSONString(columnSchemas));


        List<IndexSchema> indexSchemas = systemMetaService.selectIndexSchema(dataSourceKey, connectConfig.typeEnum(), tableSchemas.get(0).getTableName());
        System.out.println(JSON.toJSONString(indexSchemas));
    }


}
