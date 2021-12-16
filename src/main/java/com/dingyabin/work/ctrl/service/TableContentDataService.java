package com.dingyabin.work.ctrl.service;

import com.dingyabin.work.ctrl.config.DataSourceKeyHolder;
import com.dingyabin.work.ctrl.dao.TableContentDataMapper;
import com.dingyabin.work.common.model.DataSourceKey;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/8.
 * Time:0:56
 */
@Service
public class TableContentDataService {


    @Resource
    private TableContentDataMapper tableContentDataMapper;


    public List<Map<String, Object>> queryTable(DataSourceKey dataSourceKey, String tableName, int pageNum, int pageSize) {
        DataSourceKeyHolder.setKey(dataSourceKey);
        //分页
        PageHelper.startPage(pageNum, pageSize);
        return tableContentDataMapper.selectFromTable(tableName);
    }




}
