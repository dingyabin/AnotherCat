package com.dingyabin.work.ctrl.meta;

import com.dingyabin.work.ctrl.enums.DataBaseTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:23:10
 */
@Component
public class MySqlSchemaMeta implements SchemaMeta {


    private static final String DB_NAME_SQL = "SELECT SCHEMA_NAME schemaName  FROM  information_schema.SCHEMATA";

    private static final String TABLE_SQL =  "SELECT TABLE_NAME  tableName  FROM  information_schema.TABLES WHERE TABLE_SCHEMA = '%s'";

    private static final String COLUMN_SQL = "SELECT COLUMN_NAME columnName, COLUMN_TYPE columnType, COLUMN_COMMENT columnComment FROM  information_schema.COLUMNS WHERE TABLE_NAME= '%s'";

    private static final String INDEX_SQL = "SELECT INDEX_NAME indexName , INDEX_TYPE indexType, GROUP_CONCAT(DISTINCT COLUMN_NAME  SEPARATOR ',') indexColumns, INDEX_COMMENT  indexComment  FROM information_schema.statistics WHERE table_name = '%s'  group by INDEX_NAME,INDEX_TYPE,INDEX_COMMENT ORDER BY SEQ_IN_INDEX";


    @Override
    public String getDbNameSql() {
        return DB_NAME_SQL;
    }


    @Override
    public String getTableSql(String dbName) {
        return String.format(TABLE_SQL, dbName);
    }


    @Override
    public String getColumnSql(String tableName) {
        return String.format(COLUMN_SQL, tableName);
    }

    
    @Override
    public String getIndexSql(String tableName) {
        return String.format(INDEX_SQL, tableName);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        SchemaManager.registerSchema(DataBaseTypeEnum.MYSQL,this);
    }
}
