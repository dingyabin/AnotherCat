package com.dingyabin.nat.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Discard on 2016/11/7.
 */

public class DynamicDataSource extends AbstractRoutingDataSource {



    private Map<Object, Object> dynamicDataSources = new HashMap<>();


    public DynamicDataSource() {
        setTargetDataSources(dynamicDataSources);
    }


    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceKeyHolder.getKey();
    }


    public void addDataSource(String key, DataSource dataSource) {
        dynamicDataSources.put(key, dataSource);
        afterPropertiesSet();
    }



}
