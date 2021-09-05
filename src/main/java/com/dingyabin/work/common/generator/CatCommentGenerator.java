package com.dingyabin.work.common.generator;

import org.apache.commons.collections4.CollectionUtils;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

/**
 * @author 丁亚宾
 * Date: 2021/8/27.
 * Time:23:21
 */
public class CatCommentGenerator extends DefaultCommentGenerator {

    private boolean suppressAllComments;

    private boolean addRemarkComments;

    private SimpleDateFormat dateFormat;


    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    private static final String BR = "  <br/>";
    private static final String COLUMN_NAME = "* 字段名：";
    private static final String REMARKS = "* 字段描述：";
    private static final String GETTER_REMARKS = "* 该方法返回值对应数据库里的：";
    private static final String GETTER_RETURN = "*  @return：";
    private static final String SETTER_REMARKS = "* 该方法设置的值对应数据库里的：";


    public CatCommentGenerator() {
        super();
        suppressAllComments = false;
        addRemarkComments = true;
    }


    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
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
        if (SERIAL_VERSION_UID.equals(field.getName())) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine("* 序列化Id");
            field.addJavaDocLine("*/");
        }
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

        topLevelClass.addJavaDocLine("  *  本Model对应数据库中的: " + introspectedTable.getFullyQualifiedTable() + "表");

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
        generalMethodComment(method, introspectedTable);
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


    private void generalMethodComment(Method method, IntrospectedTable introspectedTable) {
        String name = method.getName();
        if ("deleteByPrimaryKey".equalsIgnoreCase(name)) {
            method.addJavaDocLine("* 根据主键删除记录");
            List<Parameter> parameters = method.getParameters();
            if (CollectionUtils.isNotEmpty(parameters)) {
                method.addJavaDocLine(String.format("* @param %s 主键", parameters.get(0).getName()));
            }
            method.addJavaDocLine("* @return 删除的条数");
        }

        if ("insert".equalsIgnoreCase(name)) {
            method.addJavaDocLine("* 插入一条记录(所有字段都会插入)");
            List<Parameter> parameters = method.getParameters();
            if (CollectionUtils.isNotEmpty(parameters)) {
                method.addJavaDocLine(String.format("* @param %s 待插入的记录", parameters.get(0).getName()));
            }
            method.addJavaDocLine("* @return 插入的条数");
        }

        if ("insertSelective".equalsIgnoreCase(name)) {
            method.addJavaDocLine("* 选择性的插入一条记录(只会插入非空的字段)");
            List<Parameter> parameters = method.getParameters();
            if (CollectionUtils.isNotEmpty(parameters)) {
                method.addJavaDocLine(String.format("* @param %s 待插入的记录", parameters.get(0).getName()));
            }
            method.addJavaDocLine("* @return 插入的条数");
        }

        if ("selectByPrimaryKey".equalsIgnoreCase(name)) {
            method.addJavaDocLine("* 根据主键查找记录");
            List<Parameter> parameters = method.getParameters();
            if (CollectionUtils.isNotEmpty(parameters)) {
                method.addJavaDocLine(String.format("* @param %s 主键", parameters.get(0).getName()));
            }
            method.addJavaDocLine("* @return 结果");
        }

        if ("updateByPrimaryKeySelective".equalsIgnoreCase(name)) {
            method.addJavaDocLine("* 根据主键选择性的修改记录(只会修改非空字段)");
            List<Parameter> parameters = method.getParameters();
            if (CollectionUtils.isNotEmpty(parameters)) {
                method.addJavaDocLine(String.format("* @param %s 待修改的记录", parameters.get(0).getName()));
            }
            method.addJavaDocLine("* @return 修改的条数");
        }

        if ("updateByPrimaryKey".equalsIgnoreCase(name)) {
            method.addJavaDocLine("* 根据主键删除记录(所有字段都会修改)");
            List<Parameter> parameters = method.getParameters();
            if (CollectionUtils.isNotEmpty(parameters)) {
                method.addJavaDocLine(String.format("* @param %s 待修改的记录", parameters.get(0).getName()));
            }
            method.addJavaDocLine("* @return 修改的条数");
        }
    }


}
