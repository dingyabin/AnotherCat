package com.dingyabin.work.ctrl.service;

import com.dingyabin.work.ctrl.dao.SystemMetaMapper;
import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import com.dingyabin.work.ctrl.model.ColumnSchema;
import com.dingyabin.work.ctrl.model.DataBaseSchema;
import com.dingyabin.work.ctrl.model.IndexSchema;
import com.dingyabin.work.ctrl.model.TableSchema;
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
    public List<DataBaseSchema> selectDataBaseSchema(DataBaseTypeEnum dataBaseTypeEnum) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        return systemMetaMapper.selectTheWholeDataBaseSchema(schemaMeta.getDbNameSql());
    }


    /**
     * 查找所有表信息
     *
     * @param dataBaseTypeEnum dataBaseTypeEnum
     * @param dbName           库名
     * @return 结果
     */
    public List<TableSchema> selectTableSchema(DataBaseTypeEnum dataBaseTypeEnum, String dbName) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        return systemMetaMapper.selectTheWholeTableSchema(schemaMeta.getTableSql(dbName));
    }


    /**
     * 查找所有列信息
     *
     * @param dataBaseTypeEnum dataBaseTypeEnum
     * @param tableName        表名
     * @return 结果
     */
    public List<ColumnSchema> selectColumnSchema(DataBaseTypeEnum dataBaseTypeEnum, String tableName) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        return systemMetaMapper.selectTheWholeColumnSchema(schemaMeta.getColumnSql(tableName));
    }


    /**
     * 查找所有索引信息
     *
     * @param dataBaseTypeEnum dataBaseTypeEnum
     * @param tableName        表名
     * @return 结果
     */
    public List<IndexSchema> selectIndexSchema(DataBaseTypeEnum dataBaseTypeEnum, String tableName) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseTypeEnum);
        if (schemaMeta == null) {
            return Collections.emptyList();
        }
        return systemMetaMapper.selectTheWholeIndexSchema(schemaMeta.getIndexSql(tableName));
    }


}
