package com.dingyabin.work.ctrl.meta;

import com.dingyabin.work.common.enums.DataBaseTypeEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:23:59
 */
public class SchemaMetaManager {


    private static final Map<DataBaseTypeEnum, SchemaMeta> META_MAP = new HashMap<>();


    public static void registerSchema(DataBaseTypeEnum dataBaseTypeEnum, SchemaMeta schemaMeta){
        META_MAP.put(dataBaseTypeEnum, schemaMeta);
    }

    public static SchemaMeta getSchemaMeta(DataBaseTypeEnum dataBaseTypeEnum){
        return META_MAP.get(dataBaseTypeEnum);
    }




}
