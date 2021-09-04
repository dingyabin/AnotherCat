package com.dingyabin.work.common.generator.processor;

import com.dingyabin.work.common.generator.bean.ColumnNameCfg;
import com.dingyabin.work.common.generator.bean.TableNameCfg;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/9/4.
 * Time:22:56
 */
@Setter
@Getter
public class TableCfgProcessor implements ConfigXmlProcessor{

    private List<TableNameCfg> tableNameCfgList;

    private Map<Object, List<ColumnNameCfg>> columnNameCfgMap;

    private String mapperSuffix;

    private boolean enableCountByExample;

    private boolean enableDeleteByExample;

    private boolean enableSelectByExample;

    private boolean enableUpdateByExample;

    private static final String ENTER = "\n";

    private static final String EMPTY_STR = "        ";

    private static final String TABLE = "<table tableName=\"${tableName}\" domainObjectName=\"${domainObjectName}\"  mapperName=\"${mapperName}\"    enableCountByExample=\"${enableCountByExample}\"  enableDeleteByExample=\"${enableDeleteByExample}\" enableSelectByExample=\"${enableSelectByExample}\" enableUpdateByExample=\"${enableUpdateByExample}\">${columnMsg}</table>";

    private static final String COLUMN = "<columnOverride column=\"${column}\" property=\"${property}\"/>";


    public TableCfgProcessor() {
    }


    public TableCfgProcessor(List<TableNameCfg> tableNameCfgList, Map<Object, List<ColumnNameCfg>> columnNameCfgMap, String mapperSuffix) {
        this.tableNameCfgList = tableNameCfgList;
        this.columnNameCfgMap = columnNameCfgMap;
        this.mapperSuffix = mapperSuffix;
    }



    @Override
    public String process(String xmlString) {
        StringBuilder stringBuilder = new StringBuilder(StringUtils.EMPTY);
        for (TableNameCfg tableNameCfg : tableNameCfgList) {
            //处理表
            String tableCfg = processTable(tableNameCfg);
            //处理列
            String columnCfg = processColumn(tableNameCfg);
            if (StringUtils.isNotBlank(columnCfg)) {
                columnCfg = ENTER + columnCfg + EMPTY_STR;
            }
            //列添加到表里
            tableCfg = tableCfg.replace("${columnMsg}",  columnCfg);

            stringBuilder.append(tableCfg);
            stringBuilder.append("\n\n");
            stringBuilder.append(EMPTY_STR);
        }
        return xmlString.replace("${table}", stringBuilder.toString());
    }





    private String processTable(TableNameCfg tableNameCfg) {
        String tableCfg = TABLE;

        tableCfg = tableCfg.replace("${tableName}", tableNameCfg.getTableName());
        tableCfg = tableCfg.replace("${domainObjectName}", tableNameCfg.getModelName());
        tableCfg = tableCfg.replace("${mapperName}", tableNameCfg.getModelName() + mapperSuffix);

        tableCfg = tableCfg.replace("${enableCountByExample}", String.valueOf(enableCountByExample));
        tableCfg = tableCfg.replace("${enableDeleteByExample}", String.valueOf(enableDeleteByExample));
        tableCfg = tableCfg.replace("${enableSelectByExample}", String.valueOf(enableSelectByExample));
        tableCfg = tableCfg.replace("${enableUpdateByExample}", String.valueOf(enableUpdateByExample));

        return tableCfg;
    }


    private String processColumn(TableNameCfg tableNameCfg) {
        List<ColumnNameCfg> columnCfg = getColumnCfg(tableNameCfg.getTableName());
        if (CollectionUtils.isEmpty(columnCfg)) {
            return StringUtils.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder(StringUtils.EMPTY);
        for (ColumnNameCfg columnNameCfg : columnCfg) {
            //是否需要重写
            if (!shouldColumnOverride(columnNameCfg)) {
                continue;
            }
            String column = COLUMN;
            column = column.replace("${column}", columnNameCfg.getColumnName());
            column = column.replace("${property}", columnNameCfg.getFieldName());

            stringBuilder.append(EMPTY_STR);
            stringBuilder.append(EMPTY_STR);

            stringBuilder.append(column);
            stringBuilder.append(ENTER);
        }
        return stringBuilder.toString();
    }



    private List<ColumnNameCfg> getColumnCfg(String tableName) {
        return (columnNameCfgMap != null) ? columnNameCfgMap.get(tableName) : null;
    }



    private boolean shouldColumnOverride(ColumnNameCfg columnNameCfg) {
        String autoFieldName = JavaBeansUtil.getCamelCaseString(columnNameCfg.getColumnName(), false);
        return !autoFieldName.equals(columnNameCfg.getFieldName());
    }


}
