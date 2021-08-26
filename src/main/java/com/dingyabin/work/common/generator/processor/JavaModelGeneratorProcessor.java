package com.dingyabin.work.common.generator.processor;

import org.mybatis.generator.config.JavaModelGeneratorConfiguration;

import java.util.Properties;

/**
 * @author 丁亚宾
 * Date: 2021/8/27.
 * Time:0:17
 */
public class JavaModelGeneratorProcessor extends JavaModelGeneratorConfiguration implements ConfigXmlProcessor {


    public static final String enableSubPackages = "enableSubPackages";

    public static final String trimStrings = "trimStrings";


    public JavaModelGeneratorProcessor() {
        addProperty(enableSubPackages, "true");
        addProperty(trimStrings, "false");
    }


    @Override
    public String process(String xmlString) {
        String targetProject = getTargetProject();
        String targetPackage = getTargetPackage();
        Properties properties = getProperties();

        xmlString = xmlString.replace("${javaModelGenerator.targetPackage}", targetPackage);
        xmlString = xmlString.replace("${javaModelGenerator.targetProject}", targetProject);

        for (String propertyName : properties.stringPropertyNames()) {
            xmlString = xmlString.replace("${javaModelGenerator." + propertyName + "}", properties.getProperty(propertyName));
        }
        return xmlString;
    }
}
