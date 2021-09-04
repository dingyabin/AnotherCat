package com.dingyabin.work.common.generator.processor;

import org.mybatis.generator.config.JavaClientGeneratorConfiguration;

import java.util.Properties;

/**
 * @author dingyabin
 * @date 2021-08-27 12:38
 */
public class JavaClientGeneratorProcessor extends JavaClientGeneratorConfiguration implements ConfigXmlProcessor {

    public static final String ENABLE_SUB_PACKAGES = "enableSubPackages";


    public JavaClientGeneratorProcessor() {
        this(true);
    }


    public JavaClientGeneratorProcessor(boolean enableSubPackages) {
        addProperty(ENABLE_SUB_PACKAGES, String.valueOf(enableSubPackages));
    }


    @Override
    public String process(String xmlString) {
        String targetProject = getTargetProject();
        String targetPackage = getTargetPackage();
        Properties properties = getProperties();

        xmlString = xmlString.replace("${javaClientGenerator.targetPackage}", targetPackage);
        xmlString = xmlString.replace("${javaClientGenerator.targetProject}", targetProject);

        for (String propertyName : properties.stringPropertyNames()) {
            xmlString = xmlString.replace("${javaClientGenerator." + propertyName + "}", properties.getProperty(propertyName));
        }
        return xmlString;
    }



}