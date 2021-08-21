package com.dingyabin.work.ctrl.dao;

import com.dingyabin.work.common.model.ColumnSchema;
import com.dingyabin.work.common.model.DataBaseSchema;
import com.dingyabin.work.common.model.IndexSchema;
import com.dingyabin.work.common.model.TableSchema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:0:30
 */
public interface SystemMetaMapper {


    /**
     * 查找所有数据库信息
     * @param sql sql
     * @return 结果
     */
    List<DataBaseSchema> selectTheWholeDataBaseSchema(@Param("sql")String sql);


     /**
      *  查找所有表信息
     * @param sql sql
     * @return 结果
     */
    List<TableSchema> selectTheWholeTableSchema(@Param("sql")String sql);


    /**
     * 查找所有列信息
     * @param sql sql
     * @return 结果
     */
    List<ColumnSchema> selectTheWholeColumnSchema(@Param("sql")String sql);


    /**
     * 查找所有索引信息
     * @param sql sql
     * @return 结果
     */
    List<IndexSchema> selectTheWholeIndexSchema(@Param("sql")String sql);


    /**
     * 修改表名
     * @param sql sql
     * @return 结果
     */
    int reNameTable(@Param("sql")String sql);

}
