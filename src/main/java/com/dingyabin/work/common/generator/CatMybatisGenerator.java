package com.dingyabin.work.common.generator;

import com.dingyabin.work.common.generator.processor.ConfigXmlProcessor;
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



    public boolean start(ConnectConfig connectConfig, List<ConfigXmlProcessor> xmlProcessors) {
        try (InputStream inputStream = CatMybatisGenerator.class.getResourceAsStream("/template/generator.cfg")) {
            List<String> warnings = new ArrayList<>();

            String configXml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            for (ConfigXmlProcessor xmlProcessor : xmlProcessors) {
                configXml = xmlProcessor.process(configXml);
            }

            Configuration config = new ConfigurationParser(warnings).parseConfiguration(new ByteArrayInputStream(configXml.getBytes()));

            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
            return true;
        } catch (Exception e) {
            //ignore
            e.printStackTrace();
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