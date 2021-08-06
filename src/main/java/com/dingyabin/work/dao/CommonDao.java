package com.dingyabin.nat.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:0:30
 */
public interface CommonDao {


    List<Map<String, Object>> selectAll(@Param("tableName") String tableName);

}
