package com.dingyabin.work.common.generator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

/**
 * @author Administrator
 * Date: 2023/3/11.
 * Time:0:26
 */
public class LombokPlugin extends PluginAdapter {

    private FullyQualifiedJavaType getter;

    private FullyQualifiedJavaType setter;

    public LombokPlugin() {
        getter = new FullyQualifiedJavaType("lombok.Getter");
        setter = new FullyQualifiedJavaType("lombok.Setter");

    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }


    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        return false;
    }



    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addLomBokAnnotation(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelRecordWithBLOBsClassGenerated(
            TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        addLomBokAnnotation(introspectedTable, topLevelClass);
        return true;
    }

    @Override
    public boolean modelPrimaryKeyClassGenerated(TopLevelClass topLevelClass,
                                                 IntrospectedTable introspectedTable) {
        addLomBokAnnotation(introspectedTable, topLevelClass);
        return true;
    }


    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }


    private void addLomBokAnnotation(IntrospectedTable introspectedTable, TopLevelClass topLevelClass) {
        topLevelClass.addImportedType(getter);
        topLevelClass.addImportedType(setter);

        topLevelClass.addAnnotation("@Getter");
        topLevelClass.addAnnotation("@Setter");

    }

}
