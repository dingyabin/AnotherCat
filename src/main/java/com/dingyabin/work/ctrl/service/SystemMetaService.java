package com.dingyabin.work.ctrl.service;

import com.dingyabin.work.ctrl.config.DataSourceKeyHolder;
import com.dingyabin.work.ctrl.dao.SystemMetaMapper;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import com.dingyabin.work.common.model.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:0:28
 */
@Service
public class SystemMetaService {

    @Resource
    private SystemMetaMapper systemMetaMapper;


    /**
     * 查找所有库信息
     *
     * @param dataBaseTypeEnum dataBaseTypeEnum
     * @return 结果
     */
    public List<DataBaseSchema> selectDataBaseSchema(DataSourceKey dataSourceKey, DataBaseTypeEnum dataBaseTypeEnum) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        DataSourceKeyHolder.setKey(dataSourceKey);
        return systemMetaMapper.selectTheWholeDataBaseSchema(schemaMeta.getDbNameSql());
    }


    /**
     * 查找所有表信息
     *
     * @param dataBaseTypeEnum dataBaseTypeEnum
     * @param dbName           库名
     * @return 结果
     */
    public List<TableSchema> selectTableSchema(DataSourceKey dataSourceKey, DataBaseTypeEnum dataBaseTypeEnum, String dbName) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        DataSourceKeyHolder.setKey(dataSourceKey);
        return systemMetaMapper.selectTheWholeTableSchema(schemaMeta.getTableSql(dbName));
    }


    /**
     * 查找所有列信息
     *
     * @param dataBaseTypeEnum dataBaseTypeEnum
     * @param tableName        表名
     * @return 结果
     */
    public List<ColumnSchema> selectColumnSchema(DataSourceKey dataSourceKey, DataBaseTypeEnum dataBaseTypeEnum, String tableName) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        DataSourceKeyHolder.setKey(dataSourceKey);
        return systemMetaMapper.selectTheWholeColumnSchema(schemaMeta.getColumnSql(tableName));
    }


    /**
     * 查找所有索引信息
     *
     * @param dataBaseTypeEnum dataBaseTypeEnum
     * @param tableName        表名
     * @return 结果
     */
    public List<IndexSchema> selectIndexSchema(DataSourceKey dataSourceKey, DataBaseTypeEnum dataBaseTypeEnum, String tableName) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        DataSourceKeyHolder.setKey(dataSourceKey);
        return systemMetaMapper.selectTheWholeIndexSchema(schemaMeta.getIndexSql(tableName));
    }


}
