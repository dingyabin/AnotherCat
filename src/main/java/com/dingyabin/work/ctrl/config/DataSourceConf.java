package com.dingyabin.work.ctrl.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:0:03
 */
@Configuration
@MapperScan(basePackages = "com.dingyabin.work.ctrl.dao", sqlSessionFactoryRef="sqlSessionFactory")
public class DataSourceConf {


    @Bean
    @Scope("prototype")
    @ConfigurationProperties(prefix = "cat.ds.default")
    public DruidDataSource druidDataSource() {
        return new DruidDataSource();
    }


    @Lazy
    @Primary
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        return new DynamicDataSource();
    }



    @Lazy
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
        return factoryBean;
    }


}
