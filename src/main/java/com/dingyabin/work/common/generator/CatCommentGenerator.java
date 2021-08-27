package com.dingyabin.work.common.generator;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @author 丁亚宾
 * Date: 2021/8/27.
 * Time:23:21
 */
public class CatCommentGenerator extends DefaultCommentGenerator {

    private Properties properties;

    private boolean suppressDate;

    private boolean suppressAllComments;

    /**
     * If suppressAllComments is true, this option is ignored.
     */
    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;


    private static final String BR = "<br/>";
    private static final String COLUMN_NAME = "* 字段名：";
    private static final String REMARKS = "* 字段描述：";
    private static final String GETTER_REMARKS = "* 该方法返回值对应数据库里的：";
    private static final String GETTER_RETURN = "*  @return：";
    private static final String SETTER_REMARKS = "* 该方法设置的值对应数据库里的：";


    public CatCommentGenerator() {
        super();
        properties = new Properties();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
    }


    @Override
    public void addConfigurationProperties(Properties properties) {
        this.properties.putAll(properties);
        suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        addRemarkComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        field.addJavaDocLine(COLUMN_NAME + introspectedTable.getFullyQualifiedTable() + "." + introspectedColumn.getActualColumnName() + BR);
        if (addRemarkComments) {
            field.addJavaDocLine(REMARKS + introspectedColumn.getRemarks());
        }
        field.addJavaDocLine("*/");
    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        field.addJavaDocLine("/**");
        field.addJavaDocLine(" *此字段在" + introspectedTable.getFullyQualifiedTable() + "中无对应字段" + BR);
        field.addJavaDocLine("*/");
    }


    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        topLevelClass.addJavaDocLine("/**");
        if (addRemarkComments) {
            topLevelClass.addJavaDocLine(" *  描述:" + introspectedTable.getRemarks());
        }
        topLevelClass.addJavaDocLine("  *");

        topLevelClass.addJavaDocLine("*  本Model对应数据库中的: " + introspectedTable.getFullyQualifiedTable() + "表");

        topLevelClass.addJavaDocLine("  */");
    }


    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine(GETTER_REMARKS + introspectedTable.getFullyQualifiedTable() + "." + introspectedColumn.getActualColumnName() + BR);
        method.addJavaDocLine(GETTER_RETURN + introspectedColumn.getRemarks());
        method.addJavaDocLine(" */");
    }


    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        method.addJavaDocLine(SETTER_REMARKS + introspectedTable.getFullyQualifiedTable() + "." + introspectedColumn.getActualColumnName() + BR);
        method.addJavaDocLine(REMARKS + introspectedColumn.getRemarks());
        method.addJavaDocLine(" */");
    }


    /**
     * Mapper 类注释
     */
    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        method.addJavaDocLine("/**");
        String name = method.getName();
        if () {

        }
        method.addJavaDocLine(" */");
    }


    /**
     * Mapper文件注释
     */
    @Override
    public void addComment(XmlElement xmlElement) {
    }


    @Override
    protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
    }


}
