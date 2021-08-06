package com.dingyabin.nat.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Maps;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Map;

/**
 * @author 丁亚宾
 * Date: 2021/8/6.
 * Time:0:03
 */
@Configuration
public class MyDataSourceConfigration {



    @Bean("firstDataSource")
    @ConfigurationProperties("ds.a.datasource")
    public DataSource firstDataSource() {
        return new DruidDataSource();
    }



    @Bean("secDataSource")
    @ConfigurationProperties("ds.b.datasource")
    public DataSource secDataSource() {
        return new DruidDataSource();
    }



    @Lazy
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        return new DynamicDataSource();
    }



    @Lazy
    @Bean("sqlSessionFactory")
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("dynamicDataSource") DynamicDataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
        return factoryBean;
    }



    @Lazy
    @Bean("mapperScannerConfigurer")
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("com.dingyabin.nat.dao");
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        return configurer;
    }



}
