package com.dingyabin.work.common.generator.processor;

import org.mybatis.generator.config.CommentGeneratorConfiguration;

import java.util.Properties;

/**
 * @author dingyabin
 * @date 2021-08-27 12:52
 */
public class CommentGeneratorProcessor extends CommentGeneratorConfiguration implements ConfigXmlProcessor {


    public static final String SUPPRESS_DATE = "suppressDate";

    public static final String SUPPRESS_ALL_COMMENTS = "suppressAllComments";

    public static final String ADD_REMARK_COMMENTS = "addRemarkComments";


    public CommentGeneratorProcessor() {
        this(false, false, true);
    }


    public CommentGeneratorProcessor(boolean suppressDate, boolean suppressAllComments, boolean addRemarkComments) {
        addProperty(SUPPRESS_DATE, String.valueOf(suppressDate));
        addProperty(SUPPRESS_ALL_COMMENTS, String.valueOf(suppressAllComments));
        addProperty(ADD_REMARK_COMMENTS, String.valueOf(addRemarkComments));
    }


    @Override
    public String process(String xmlString) {
        Properties properties = getProperties();
        for (String propertyName : properties.stringPropertyNames()) {
            xmlString = xmlString.replace("${commentGenerator." + propertyName + "}", properties.getProperty(propertyName));
        }
        return xmlString;
    }


}