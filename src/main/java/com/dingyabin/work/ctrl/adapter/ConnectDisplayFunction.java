package com.dingyabin.work.ctrl.adapter;

import com.dingyabin.work.common.model.CatRet;
import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.common.model.TableSchema;
import com.dingyabin.work.ctrl.config.SpringBeanHolder;

import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2022/2/18.
 * Time:21:29
 */
public interface ConnectDisplayFunction {




    /**
     * 查询connectConfig里面的所有数据库
     * @param connectConfig 连接信息
     * @return 数据库
     */
    default CatRet<List<DataBaseSchema>> getDataBaseSchemasInConnectConfig(ConnectConfig connectConfig) {
        //查询对应的数据库
        return SpringBeanHolder.getCatAdapter().getDbsWithConnect(connectConfig);
    }



    /**
     * 关闭配置的连接
     * @param connectConfig 配置的连接
     */
    default void closeRemoveConnect(ConnectConfig connectConfig){
        SpringBeanHolder.getCatAdapter().closeRemoveConnect(connectConfig);
    }




    /**
     * 查询对应的数据库下面的表
     * @param connectConfig 连接信息
     * @param dataBaseSchema 库信息
     * @return 数据库下面的表
     */
    default List<TableSchema> getTablesInDataBaseSchema(ConnectConfig connectConfig, DataBaseSchema dataBaseSchema) {
        //查询对应的数据库下面的表
        return SpringBeanHolder.getCatAdapter().getTablesWithDb(connectConfig, dataBaseSchema);
    }




    /**
     * 添加动态数据源
     * @param connectConfig 连接信息
     * @param dataBaseSchema 库信息
     */
    default void addDataSource(ConnectConfig connectConfig, DataBaseSchema dataBaseSchema) {
        SpringBeanHolder.getCatAdapter().addDataSource(connectConfig, dataBaseSchema);
    }



}
