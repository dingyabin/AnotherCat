package com.dingyabin.work.common.generator.processor;

import org.mybatis.generator.config.JavaTypeResolverConfiguration;

import java.util.Properties;

/**
 * @author 丁亚宾
 * Date: 2021/8/27.
 * Time:23:01
 */
public class JavaTypeResolverProcessor extends JavaTypeResolverConfiguration implements ConfigXmlProcessor {

    public static final String FORCE_BIG_DECIMALS ="forceBigDecimals";

    public static final String USE_JSR_310_TYPES ="useJSR310Types";


    public JavaTypeResolverProcessor() {
        this(true, false);
    }


    public JavaTypeResolverProcessor(boolean forceBigDecimals, boolean useJSR310Types) {
        addProperty(FORCE_BIG_DECIMALS, String.valueOf(forceBigDecimals));
        addProperty(USE_JSR_310_TYPES, String.valueOf(useJSR310Types));
    }


    @Override
    public String process(String xmlString) {
        Properties properties = getProperties();
        for (String propertyName : properties.stringPropertyNames()) {
            xmlString = xmlString.replace("${javaTypeResolver." + propertyName + "}", properties.getProperty(propertyName));
        }
        return xmlString;
    }


}
