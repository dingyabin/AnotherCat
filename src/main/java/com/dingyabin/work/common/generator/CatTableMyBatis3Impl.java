package com.dingyabin.work.common.generator;

import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author 丁亚宾
 * Date: 2021/9/4.
 * Time:19:04
 */
public class CatTableMyBatis3Impl extends IntrospectedTableMyBatis3Impl {


    protected void calculateJavaClientAttributes() {
        super.calculateJavaClientAttributes();
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(calculateJavaClientInterfacePackage());
        stringBuilder.append('.');
        if (stringHasValue(fullyQualifiedTable.getDomainObjectSubPackage())) {
            stringBuilder.append(fullyQualifiedTable.getDomainObjectSubPackage());
            stringBuilder.append('.');
        }
        stringBuilder.append(fullyQualifiedTable.getDomainObjectName());
        stringBuilder.append("Mapper"); //$NON-NLS-1$

        setMyBatis3JavaMapperType(stringBuilder.toString());
    }
}
