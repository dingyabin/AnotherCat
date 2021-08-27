package com.dingyabin.work.common.generator.processor;

import org.mybatis.generator.config.CommentGeneratorConfiguration;

import java.util.Properties;

/**
 * @author dingyabin
 * @date 2021-08-27 12:52
 */
public class CommentGeneratorProcessor extends CommentGeneratorConfiguration implements ConfigXmlProcessor {


    @Override
    public String process(String xmlString) {
        Properties properties = getProperties();
        for (String propertyName : properties.stringPropertyNames()) {
            xmlString = xmlString.replace("${commentGenerator." + propertyName + "}", properties.getProperty(propertyName));
        }
        return xmlString;
    }


}