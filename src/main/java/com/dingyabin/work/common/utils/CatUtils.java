package com.dingyabin.work.common.utils;

import com.dingyabin.work.common.cons.Const;
import com.dingyabin.work.common.enums.DataBaseTypeEnum;
import com.dingyabin.work.ctrl.meta.SchemaMeta;
import com.dingyabin.work.ctrl.meta.SchemaMetaManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 丁亚宾
 * Date: 2021/8/7.
 * Time:10:28
 */
@Slf4j
public class CatUtils {


    /**
     * 获取指定地址的日志文件
     *
     * @param logPath 地址
     * @return 文件列表集合
     */
    public static Map<String, List<File>> getLogFileMap(String logPath) {
        File[] files = new File(logPath).listFiles();
        if (files == null) {
            return Collections.emptyMap();
        }
        Map<String, List<File>> listMap = new HashMap<>(2);
        List<File> infoLogs = new ArrayList<>();
        List<File> errorLogs = new ArrayList<>();
        for (File logFile : files) {
            if (logFile.isDirectory()) {
                continue;
            }
            String name = FilenameUtils.getName(logFile.getAbsolutePath());
            if (name.contains(".info.log")) {
                infoLogs.add(logFile);
            }
            if (name.contains(".error.log")) {
                errorLogs.add(logFile);
            }
        }
        listMap.put(Const.INFO_LOG_KEY, infoLogs);
        listMap.put(Const.ERROR_LOG_KEY, errorLogs);
        return listMap;
    }


    /**
     * 清空文件
     * @param file 文件
     * @return 是否成功
     */
    public static boolean clearFile(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(StringUtils.EMPTY);
            return true;
        } catch (Exception e) {
            log.error("clearFile error, file={}", file, e);
        }
        return false;
    }



    public static boolean checkNewConnect(DataBaseTypeEnum dataBaseType, String host, String port, String userName, String pwd) {
        SchemaMeta schemaMeta = SchemaMetaManager.getSchemaMeta(dataBaseType);
        if (schemaMeta == null) {
            return false;
        }
        try {
            Class.forName(schemaMeta.driverClassName());
            DriverManager.getConnection(schemaMeta.connectUrl(host, port, null), userName, pwd);
            return true;
        } catch (Exception e) {
            log.error("checkNewConnect error,host={},port={},user={}, error={}", host, port, userName, e.getMessage());
        }
        return false;
    }



}









