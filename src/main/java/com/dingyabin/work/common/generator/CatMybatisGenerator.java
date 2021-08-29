package com.dingyabin.work.common.generator;

import com.dingyabin.work.common.generator.processor.CommentGeneratorProcessor;
import com.dingyabin.work.common.generator.processor.ConfigXmlProcessor;
import com.dingyabin.work.common.generator.processor.JavaModelGeneratorProcessor;
import com.dingyabin.work.common.generator.processor.JdbcConnectionProcessor;
import com.dingyabin.work.common.model.ConnectConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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


    public String makeCfgXml(List<ConfigXmlProcessor> xmlProcessors) {
        try (InputStream inputStream = CatMybatisGenerator.class.getResourceAsStream("/template/generator.cfg")) {
            //xml模板
            String configXml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            //替换配置
            for (ConfigXmlProcessor xmlProcessor : xmlProcessors) {
                configXml = xmlProcessor.process(configXml);
            }
            return configXml;
        } catch (Exception e) {
            //ignore
        }
        return StringUtils.EMPTY;
    }



    public boolean generate(String makeCfgXml) {
        try {
            if (StringUtils.isBlank(makeCfgXml)) {
                return false;
            }
            List<String> warnings = new ArrayList<>();

            Configuration config = new ConfigurationParser(warnings).parseConfiguration(new ByteArrayInputStream(makeCfgXml.getBytes()));

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


//    public static void main(String[] args) {
//        JdbcConnectionProcessor jdbcConnectionProcessor = new JdbcConnectionProcessor(new ConnectConfig("xxx","MYSQL","127.0.0.1","3306","root","12345678"),"weight_manager");
//        CommentGeneratorProcessor commentGeneratorProcessor = new CommentGeneratorProcessor();
//        JavaModelGeneratorProcessor javaModelGeneratorProcessor = new JavaModelGeneratorProcessor();
//        javaModelGeneratorProcessor.setTargetPackage("com");
//        javaModelGeneratorProcessor.setTargetProject("F:\\demo1");
//        List<ConfigXmlProcessor> xmlProcessors = new ArrayList<>();
//        xmlProcessors.add(jdbcConnectionProcessor);
//        xmlProcessors.add(commentGeneratorProcessor);
//        xmlProcessors.add(javaModelGeneratorProcessor);
//        String s = CatMybatisGenerator.getInstance().makeCfgXml(xmlProcessors);
//        System.out.println(s);
//        CatMybatisGenerator.getInstance().generate(s);
//    }
}