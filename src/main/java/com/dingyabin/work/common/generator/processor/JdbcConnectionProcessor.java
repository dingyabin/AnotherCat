package com.dingyabin.work.common.generator.processor;

import com.dingyabin.work.common.model.ConnectConfig;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import org.mybatis.generator.config.JDBCConnectionConfiguration;

/**
 * @author dingyabin
 * @date 2021-08-27 12:42
 */
public class JdbcConnectionProcessor extends JDBCConnectionConfiguration implements ConfigXmlProcessor {



    public JdbcConnectionProcessor(ConnectConfig connectConfig, String dbName) {
        setUserId(connectConfig.getUserName());
        setPassword(connectConfig.getPwd());
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(connectConfig.typeEnum());
        setConnectionURL(schemaMeta.connectUrl(connectConfig.getHost(), connectConfig.getPort(), dbName).replaceAll("&","&amp;"));
        setDriverClass(schemaMeta.driverClassName());
    }


    @Override
    public String process(String xmlString) {
        xmlString = xmlString.replace("${jdbcConnection.driverClass}", getDriverClass());
        xmlString = xmlString.replace("${jdbcConnection.connectionURL}", getConnectionURL());
        xmlString = xmlString.replace("${jdbcConnection.userId}", getUserId());
        xmlString = xmlString.replace("${jdbcConnection.password}", getPassword());
        return xmlString;
    }

}