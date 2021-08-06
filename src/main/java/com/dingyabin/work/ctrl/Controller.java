package com.dingyabin.nat.ctrl;

import com.alibaba.fastjson.JSON;
import com.dingyabin.nat.config.DataSourceKeyHolder;
import com.dingyabin.nat.config.DynamicDataSource;
import com.dingyabin.nat.config.MyDataSourceConfigration;
import com.dingyabin.nat.dao.CommonDao;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:0:35
 */
@RestController
@RequestMapping("/test")
public class Controller {

    @Resource
    private DynamicDataSource dynamicDataSource;

    @Resource(name = "firstDataSource")
    private DataSource dataSource;


    @Resource(name = "secDataSource")
    private DataSource secDataSource;


    @Resource
    private CommonDao commonDao;




    @RequestMapping("/a")
    public String test(){

        dynamicDataSource.addDataSource("DS_A", dataSource);
        DataSourceKeyHolder.finalKey = "DS_A";

        List<Map<String, Object>> ttt = commonDao.selectAll("demo");
        return JSON.toJSONString(ttt);
    }


    @RequestMapping("/b")
    public String test2(){

        dynamicDataSource.addDataSource("DS_B", secDataSource);
        DataSourceKeyHolder.finalKey = "DS_B";

        List<Map<String, Object>> ttt = commonDao.selectAll("db");
        return JSON.toJSONString(ttt);
    }


}
