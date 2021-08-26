package com.dingyabin.work.common.utils;

import com.dingyabin.work.common.model.ConnectConfig;
import org.apache.commons.io.IOUtils;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dingyabin
 * @date 2021-08-26 18:04
 */
public class CatMybatisGenerator {



    public boolean start(ConnectConfig connectConfig) {
        try (InputStream inputStream = CatMybatisGenerator.class.getResourceAsStream("/template/generator.xml")) {
            List<String> warnings = new ArrayList<>();

            String configFile = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            Configuration config = new ConfigurationParser(warnings).parseConfiguration(new ByteArrayInputStream(configFile.getBytes()));

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