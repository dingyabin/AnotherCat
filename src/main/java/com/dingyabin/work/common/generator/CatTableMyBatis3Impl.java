package com.dingyabin.work.common.generator;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

/**
 * @author 丁亚宾
 * Date: 2021/9/4.
 * Time:19:04
 */
public class CatTableMyBatis3Impl extends IntrospectedTableMyBatis3Impl {


    protected String calculateMyBatis3XmlMapperFileName() {
        return fullyQualifiedTable.getDomainObjectName() + "Mapper.xml";
    }

}
