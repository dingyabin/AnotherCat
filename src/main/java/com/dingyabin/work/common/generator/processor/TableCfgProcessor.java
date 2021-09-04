package com.dingyabin.work.common.generator.processor;

import com.dingyabin.work.common.generator.bean.ColumnNameCfg;
import com.dingyabin.work.common.generator.bean.TableNameCfg;
import lombok.Getter;
import lombok.Setter;

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

    private static final String EMPTY_STR = "        ";

    private static final String TABLE = "<table tableName=\"${tableName}\" domainObjectName=\"${domainObjectName}\"  mapperName=\"${mapperName}\"    enableCountByExample=\"${enableCountByExample}\"  enableDeleteByExample=\"${enableDeleteByExample}\" enableSelectByExample=\"${enableSelectByExample}\" enableUpdateByExample=\"${enableUpdateByExample}\">${columnMsg}</table>";

    private static final String COLUMN = "                <columnOverride column=\"${column}\" property=\"${property}\"/>";


    public TableCfgProcessor() {
    }


    public TableCfgProcessor(List<TableNameCfg> tableNameCfgList, Map<Object, List<ColumnNameCfg>> columnNameCfgMap, String mapperSuffix) {
        this.tableNameCfgList = tableNameCfgList;
        this.columnNameCfgMap = columnNameCfgMap;
        this.mapperSuffix = mapperSuffix;
    }



    @Override
    public String process(String xmlString) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TableNameCfg tableNameCfg : tableNameCfgList) {
            String tableCfg = TABLE;
            tableCfg = tableCfg.replace("${tableName}", tableNameCfg.getTableName());
            tableCfg = tableCfg.replace("${domainObjectName}", tableNameCfg.getModelName());
            tableCfg = tableCfg.replace("${mapperName}", tableNameCfg.getModelName() + mapperSuffix);

            tableCfg = tableCfg.replace("${enableCountByExample}", String.valueOf(enableCountByExample));
            tableCfg = tableCfg.replace("${enableDeleteByExample}", String.valueOf(enableDeleteByExample));
            tableCfg = tableCfg.replace("${enableSelectByExample}", String.valueOf(enableSelectByExample));
            tableCfg = tableCfg.replace("${enableUpdateByExample}", String.valueOf(enableUpdateByExample));
            stringBuilder.append(tableCfg);
            stringBuilder.append("\n" + EMPTY_STR);
        }
        return xmlString.replace("${table}", stringBuilder.toString());
    }



}
