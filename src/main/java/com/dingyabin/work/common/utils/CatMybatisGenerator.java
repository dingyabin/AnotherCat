package com.dingyabin.work.common.utils;

import com.dingyabin.work.common.model.ConnectConfig;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dingyabin
 * @date 2021-08-26 18:04
 */
public class CatMybatisGenerator {



    public boolean start(ConnectConfig connectConfig) {
        try {
            List<String> warnings = new ArrayList<>();

            File configFile = new File("generatorConfig.xml");


            ConfigurationParser parser = new ConfigurationParser(warnings);

            Configuration config = parser.parseConfiguration(configFile);


            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            return true;
        } catch (Exception e) {
            //ignore
        }
        return false;
    }






    /**
     * 内部类，持有MybatisGenerator的单例, 延迟加载
     */
    private static class CatMybatisGeneratorHolder {
        private static CatMybatisGenerator INSTANCE = new CatMybatisGenerator();
    }



    /**
     * 获取单例
     */
    public static CatMybatisGenerator getInstance() {
        return CatMybatisGeneratorHolder.INSTANCE;
    }


}