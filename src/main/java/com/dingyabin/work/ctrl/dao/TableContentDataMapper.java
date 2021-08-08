package com.dingyabin.work.ctrl.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/8.
 * Time:0:52
 */
public interface TableContentDataMapper {


    /**
     * 查找所有信息
     * @param tableName tableName
     * @return 结果
     */
    List<Map<String,Object>> selectFromTable(@Param("tableName")String tableName);


}
