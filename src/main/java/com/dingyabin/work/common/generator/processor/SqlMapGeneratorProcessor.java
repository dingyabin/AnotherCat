package com.dingyabin.work.common.generator.processor;

import org.mybatis.generator.config.SqlMapGeneratorConfiguration;

import java.util.Properties;

/**
 * @author dingyabin
 * @date 2021-08-27 12:34
 */
public class SqlMapGeneratorProcessor extends SqlMapGeneratorConfiguration implements ConfigXmlProcessor {


    public static final String ENABLE_SUB_PACKAGES = "enableSubPackages";


    public SqlMapGeneratorProcessor() {
        this(true);
    }

    public SqlMapGeneratorProcessor(boolean enableSubPackages) {
        addProperty(ENABLE_SUB_PACKAGES, String.valueOf(enableSubPackages));
    }


    @Override
    public String process(String xmlString) {
        String targetProject = getTargetProject();
        String targetPackage = getTargetPackage();
        Properties properties = getProperties();

        xmlString = xmlString.replace("${sqlMapGenerator.targetPackage}", targetPackage);
        xmlString = xmlString.replace("${sqlMapGenerator.targetProject}", targetProject);

        for (String propertyName : properties.stringPropertyNames()) {
            xmlString = xmlString.replace("${sqlMapGenerator." + propertyName + "}", properties.getProperty(propertyName));
        }
        return xmlString;
    }


}